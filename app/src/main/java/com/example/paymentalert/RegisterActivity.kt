package com.example.paymentalert

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.ktx.Firebase
import kotlin.math.abs

class RegisterActivity : AppCompatActivity() {
    private lateinit var detector: GestureDetectorCompat
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.fondo1)
        }


        val editTextCorreo = findViewById<EditText>(R.id.et_email)
        val editTextPassword = findViewById<EditText>(R.id.et_password)
        val editTextRePassword = findViewById<EditText>(R.id.et_repassword)
        var buttonInicioSesion = findViewById<Button>(R.id.btn_register)

        detector = GestureDetectorCompat(this, DiaryGestureListener())

        buttonInicioSesion.setOnClickListener {
            val mEmail = editTextCorreo.text.toString()
            val mPassword = editTextPassword.text.toString()
            val mRPassword = editTextRePassword.text.toString()

            if (mPassword.isEmpty() || mEmail.isEmpty() || mRPassword.isEmpty()) {
                Toast.makeText(
                    this, "Email o contraseña o incorrectos.", Toast.LENGTH_SHORT
                ).show()
            } else {
                if (Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                    if (editTextPassword.text.toString() != editTextRePassword.text.toString()) {
                        Toast.makeText(
                            this, "Contraseñas no coisiden", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (editTextPassword.length() < 6) {//check if the value in password editText filed is less than 6 characters
                            Toast.makeText(
                                this,
                                " La contraseña debe tener al menos 6 caracteres",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@setOnClickListener //this line simply states that no other action should happen unless the value in password editText field is more than 6 characters
                        }

                        logIn(mEmail, mPassword)
                    }
                }else{
                    editTextCorreo.requestFocus()
                    Toast.makeText(this, "Ingrese Correo Valido", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener //this line simply states that no other action should happen unless the value in email editText matches an email address pattern
                }
            }

        }

    }


    private fun logIn(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
//                    val user = auth.currentUser
                    reload()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)
                    try {
                        throw it.getException()!!
                    } catch (e: FirebaseAuthUserCollisionException) {
                        // email already in use
                        Toast.makeText(
                            applicationContext,
                            "Correo electrónico ya Registrado!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Toast.makeText(this, "El Registro Fallo!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload() {
        val intent = Intent(this, LogActivity::class.java)
        this.startActivity(intent)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (detector.onTouchEvent(event!!)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    inner class DiaryGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
        override fun onFling(
            downEvent: MotionEvent, moveEvent: MotionEvent, velocityX: Float, velocityY: Float,
        ): Boolean {
            var diffX = moveEvent?.x?.minus(downEvent!!.x) ?: 0.0F
            var diffY = moveEvent?.y?.minus(downEvent!!.y) ?: 0.0F

            return if (abs(diffX) > abs(diffY)) {
                //este es un deslizamiento hacia la izquierda o hacia la derecha
                if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        //deslizar hacia la derecha
                        this@RegisterActivity.onSwipeRight()
                    } else {
                        //deslizar hacia la izquierda
                        this@RegisterActivity.onSwipeLeft()
                    }
                    true
                } else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            } else {
                //esto es un deslizamiento hacia arriba o hacia abajo
                if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    true
                } else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }
            }
        }
    }

    private fun onSwipeLeft() {
        Toast.makeText(this, "Del otro lado", Toast.LENGTH_SHORT).show()

    }

    private fun onSwipeRight() {
        //Toast.makeText(this, "Deslizo Derecha", Toast.LENGTH_SHORT).show()
        reload()
    }


}
