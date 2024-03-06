package com.example.incaldesviaciones

import android.graphics.Color
import androidx.cardview.widget.CardView
import com.example.incaldesviaciones.databinding.DesviacionLayoutBinding
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase




class Desviacion_Adapter : BaseRecyclerViewAdapter<Desviacion, DesviacionLayoutBinding>() {

    val db = Firebase.firestore

    override fun getLayout() = R.layout.desviacion_layout

    override fun onBindViewHolder(holder: Companion.BaseViewHolder<DesviacionLayoutBinding>, position: Int )
    {
        holder.binding
            .desviacion = items[position]


        //** PARA PINTAR LA DESVIACION CREADA O MODIFICADA
        if (items[position].id == User.iddesv) {
            holder.itemView.findViewById<CardView>(R.id.cardview_item)
                .setCardBackgroundColor(Color.parseColor("#C5DCCE"))
        }

        db.collection("Desviaciones")
            .addSnapshotListener { snapshots, e ->
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.MODIFIED -> {
                            if (User.tipo == "Analista" && items[position].id == User.iddesv) {
                                holder.itemView.findViewById<CardView>(R.id.cardview_item)
                                    .setCardBackgroundColor(Color.parseColor("#C5DCCE"))
                            }
                        }

                        DocumentChange.Type.ADDED -> {
                            TODO()
                        }
                        DocumentChange.Type.REMOVED -> {
                            TODO()
                        }
                    }
                }
            }

        //****

        holder.binding.root.setOnClickListener {
            listener?.invoke(it, items[position], position)
        }
    }
}

