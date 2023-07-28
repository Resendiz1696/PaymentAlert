package com.example.paymentalert

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
class MyAdapter(private val cardList: ArrayList<Card>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_item,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

        val card : Card =cardList[position]
        holder.Banco.text = card.banco
        holder.Nombre.text = card.nombre
        holder.Numero.text = card.numero

    }

    override fun getItemCount(): Int {
       return cardList.size
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Banco: TextView = itemView.findViewById<TextView>(R.id.cardBanco)
        val Nombre: TextView = itemView.findViewById<TextView>(R.id.cardNombre)
        val Numero: TextView = itemView.findViewById<TextView>(R.id.cardNumero)

    }
}