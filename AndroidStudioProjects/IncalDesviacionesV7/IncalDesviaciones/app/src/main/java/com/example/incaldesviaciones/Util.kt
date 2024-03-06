package com.example.incaldesviaciones

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

object Util {

    val db = Firebase.firestore
    var state= "xxx"

    fun jInsert (objetivo: Double, resultado: Double, naturaleza: String?, proceso: String?, parametro: String?, old_id: String, cnt: String)
    {
        var desviado= 0
        val calendar = Calendar.getInstance()
        val jdate = calendar.getTime()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.setTimeZone(TimeZone.getTimeZone("America/Tegucigalpa"))
        val sdf2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS")
        sdf2.setTimeZone(TimeZone.getTimeZone("America/Tegucigalpa"))

        val jfecha = sdf.format(jdate)
        val jId = sdf2.format(jdate)

        if ((naturaleza=="asc" && resultado>objetivo) || (naturaleza=="desc" && resultado<objetivo))
            desviado=1

        // Establecer la nueva desviacion
        val desviacion = hashMapOf(
            "id" to jId,
            "oldId" to old_id,
            "fecha" to jfecha,
            "proceso" to proceso,
            "parametro" to parametro,
            "objetivo" to objetivo,
            "naturaleza" to naturaleza,
            "resultado" to resultado,
            "responsable" to "",
            "correccion" to "",
            "correccionEsperaDate" to "",
            "correccionEsperaTime" to "",
            "nuevoResultado" to "",
            "fechaNuevoResultado" to "",
            "desviado" to desviado,
            "cnt" to cnt
        )
        db.collection("Desviaciones").document(jId)
            .set(desviacion)
            .addOnSuccessListener {state= "NUEVA DESVIACION AGREGADA"}
            .addOnFailureListener { state= "ERR" }
    }

}

fun jNow(): String{
    val calendar = Calendar.getInstance()
    val jdate = calendar.getTime()
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    sdf.setTimeZone(TimeZone.getTimeZone("America/Tegucigalpa"))

    return sdf.format(jdate)
}