package com.example.paymentalert

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)


       /* if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.fondo1)


        }*/
        val editTextCorreo = findViewById<EditText>(R.id.editTextCorreo)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        var buttonInicioSesion = findViewById<Button>(R.id.buttonIniciarSesion)
        var textViewRegistro = findViewById<TextView>(R.id.texViewRegistrar)

        buttonInicioSesion.setOnClickListener {
            val mEmail = editTextCorreo.text.toString()
            val mPassword = editTextPassword.text.toString()

            if (mPassword.isEmpty() || mEmail.isEmpty()) {
                Toast.makeText(
                    this, "Email o contraseña o incorrectos.", Toast.LENGTH_SHORT
                ).show()
            } else {
                signIn(mEmail, mPassword)
            }

        }

        textViewRegistro.setOnClickListener {
            goRegistro()
        }


    }

   private fun signIn(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    reload(it.result?.user?.email?:"")
                } else {
                    Log.w("TAG", "signInWithEmail:failure", it.exception)
                    Toast.makeText(
                        baseContext, "Email o contraseña o incorrectos.", Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    private fun reload(email: String) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
        }
        this.startActivity(intent)
    }

    private fun goRegistro() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }




}
