package com.example.incaldesviaciones

import SegundoPlano.ProcessMainClass
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.mediciones_calidad_layout.*
import java.lang.Double.parseDouble
import java.text.SimpleDateFormat
import java.util.*


class MedicionesCalidad : AppCompatActivity() {
     var jprocesos = arrayOf("Trituracion", "MNC", "Calcinacion", "Hidratacion")
     val db = Firebase.firestore
     var desviado = 0
     var naturaleza = ""
     var DisplayUser = User.nombre

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = Color.TRANSPARENT

        //para quitar la barra de navegacion
        if(getSupportActionBar() != null)
            getSupportActionBar()?.hide();
        window.decorView.systemUiVisibility = View.AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS

        setContentView(R.layout.mediciones_calidad_layout)

        txtdisplayuser.text = DisplayUser

        //createParametros2()
        setView()

        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, jprocesos)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spProceso.adapter = aa
        //spProceso.setSelection(0, false)

        spProceso.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //showToast(message = "Position:${position} and Proceso: ${spProceso.selectedItem}")
                loadParametros(spProceso.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showToast(message = "Nothing selected")
            }
        }

        spParametro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                loadObjetivo(spParametro.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showToast(message = "Nothing selected")
            }
        }


        btnSave.setOnClickListener() {

            // VALIDA SI HAY INTERNET

            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            // CONFIRMA EL GUARDAR

            if (networkInfo != null && networkInfo.isConnected()) {
                var repetido = false
                val dialogo1: android.app.AlertDialog.Builder =
                    android.app.AlertDialog.Builder(this)
                dialogo1.setTitle("Importante")
                dialogo1.setMessage("Desea guardar la desviacion?")
                dialogo1.setCancelable(false)
                dialogo1.setPositiveButton("Confirmar",

                    // READER A LA BASE DE DATOS PARA NO REPETIDO

                    DialogInterface.OnClickListener { dialogo1, id ->
                        db.collection("Desviaciones")
                            .whereEqualTo("desviado", 1)
                            .whereEqualTo("proceso", spProceso.selectedItem.toString())
                            .whereEqualTo("parametro", spParametro.selectedItem.toString())
                            .get()
                            .addOnSuccessListener { documents ->
                                for (x in documents) {
                                    if (x.data.get("parametro") == spParametro.selectedItem.toString()) {
                                        showToast(message = "Ya existe una desviacion para este proceso")
                                        repetido = true
                                    }
                                }
                                if (repetido == false) {
                                    jInsert()
                                }
                            }
                    })
                dialogo1.setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialogo1, id -> })
                dialogo1.show()
            } else {
                showToast(message = "Se requiere conexion a Internet!")
            }
        }


        btnDesviaciones.setOnClickListener() {
            val cambiarVentana: Intent = Intent(this, VistaDesviaciones::class.java)
            startActivity(cambiarVentana)
        }
    }
        /*Monitoreo*/
        /*
        db.collection("Desviaciones")
            .whereEqualTo("desviado", 1)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("TAG", "listen:error", e)
                    return@addSnapshotListener
                }

                //txtMonitor.text.clear()
                val recyclerView = findViewById(R.id.list_view) as RecyclerView
                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

                for (dc in snapshots!!.documentChanges) {
                    //txtMonitor.append(dc.document.data.get("fecha").toString()+" - "+dc.document.data?.get("parametro").toString()+" - "+ dc.document.data?.get("resultado").toString()+" - "+ dc.document.data?.get("correccion").toString()+"\n")
                    jdesviaciones.add(Desviacion(dc.document.data?.get("parametro").toString(), dc.document.data.get("fecha").toString(),dc.document.data.get("objetivo").toString(),dc.document.data.get("resultado").toString()))

                    when (dc.type) {
                        DocumentChange.Type.ADDED -> Log.d("TAGxxxxxxxxxx", "New city: ${dc.document.data}")
                        DocumentChange.Type.MODIFIED -> Log.d("TAGxxxxxxxxxxxxxx", "Modified city: ${dc.document.data}")
                        DocumentChange.Type.REMOVED -> Log.d("TAGxxxxxxxxxxxx", "Removed city: ${dc.document.data}")
                    }
                }
                val adapter = CustomAdapter(jdesviaciones)
                recyclerView.adapter = adapter
            }*/

     fun loadParametros(jpro: String)
     {
         val db = Firebase.firestore

         db.collection("Parametros")
             .whereEqualTo("proceso", "${jpro}")
             .get()
             .addOnSuccessListener { documents ->
                 val jarr = arrayListOf<String>()

                 for (document in documents) {
                     jarr.add(document.data.get("nombre").toString())
                 }
                 var aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, jarr)
                 aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                 spParametro.adapter = aa
             }
     }

     fun loadObjetivo(jparam: String)
     {
         val db = Firebase.firestore

         db.collection("Parametros")
             .whereEqualTo("nombre", "${jparam}")
             .get()
             .addOnSuccessListener { documents ->
                 for (document in documents) {
                     txtObjetivo.setText(document.data.get("objetivo").toString())
                     naturaleza = document.data.get("naturaleza").toString()
                 }
             }
     }

    fun jInsert()
    {
        val calendar = Calendar.getInstance()
        val jdate = calendar.getTime()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.setTimeZone(TimeZone.getTimeZone("America/Tegucigalpa"))
        val sdf2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS")
        sdf2.setTimeZone(TimeZone.getTimeZone("America/Tegucigalpa"))

        val jfecha = sdf.format(jdate)
        val jId = sdf2.format(jdate)

        //val proceso = findViewById<Spinner>(R.id.spProceso)
        var objetivo = parseDouble(txtObjetivo.text.toString())
        var resultado = parseDouble(txtResultado.text.toString())

        if ((naturaleza=="asc" && resultado>objetivo) || (naturaleza=="desc" && resultado<objetivo))
            desviado=1

        // Establecer la nueva desviacion
        val desviacion = hashMapOf(
            "id" to jId,
            "oldId" to "",
            "fecha" to jfecha,
            "proceso" to spProceso.selectedItem.toString(),
            "parametro" to spParametro.selectedItem.toString(),
            "objetivo" to parseDouble(txtObjetivo.text.toString()),
            "naturaleza" to naturaleza,
            "resultado" to parseDouble(txtResultado.text.toString()),
            "responsable" to "",
            "correccion" to "",
            "correccionEsperaDate" to "",
            "correccionEsperaTime" to "",
            "nuevoResultado" to "",
            "fechaNuevoResultado" to "",
            "desviado" to desviado,
            "creadaPor" to User.nombre,
            "cnt" to "1"
        )

        // Add a new document with a generated ID
        /*db.collection("Desviaciones").document("Desviaciones")
                .set(desviacion)
                .addOnSuccessListener { Log.d("ADD_USER", "Desviacion Agregada") }
                .addOnFailureListener { e -> Log.w("ERROR_ADD", "Error writing document", e) }
                */

        db.collection("Desviaciones").document(jId)
            .set(desviacion)
            .addOnSuccessListener { showToast(message = "Medicion agregada correctamente...!")}
            .addOnFailureListener { showToast(message = "Se ha generado un error, intente nuevamente...!") }

        resetVars()
    }


     fun resetVars()
     {
         desviado = 0
         //naturaleza = ""
     }

    fun setView()
    {

    }

     fun createParametros()
     {
         val db = Firebase.firestore

         var params = hashMapOf(
             "param1" to hashMapOf("id" to 1, "nombre" to "% CaCO3 en Piedra", "objetivo" to 90)
         )
         db.collection("Parametros").document("Trituración").set(params)

         params = hashMapOf(
             "param2" to hashMapOf(
                 "id" to 2,
                 "nombre" to "% Finos Carbonato 2-4 mm",
                 "objetivo" to 5
             ),
             "param3" to hashMapOf(
                 "id" to 3,
                 "nombre" to "% Finos Carbonato 0.85-2 mm",
                 "objetivo" to 10
             ),
             "param4" to hashMapOf(
                 "id" to 4,
                 "nombre" to "% Humedad Salida del Secador",
                 "objetivo" to 1
             ),
             "param5" to hashMapOf(
                 "id" to 5,
                 "nombre" to "% Humedad CaCO3 Pulverizado",
                 "objetivo" to 1
             ),
             "param6" to hashMapOf(
                 "id" to 6,
                 "nombre" to "% Retenido Malla 325 CaCO3 Pulverizado",
                 "objetivo" to 20
             ),
             "param7" to hashMapOf(
                 "id" to 7,
                 "nombre" to "% Retenido Malla 200 CaCO3 Pulverizado",
                 "objetivo" to 10
             )
         )
         db.collection("Parametros").document("MNC").set(params)

         params = hashMapOf(
             "param8" to hashMapOf(
                 "id" to 8,
                 "nombre" to "% Humedad Salida Secador Biomasa",
                 "objetivo" to 12
             ),
             "param9" to hashMapOf(
                 "id" to 9,
                 "nombre" to "% Retenido Malla 7 Biomasa",
                 "objetivo" to 3
             )
         )
         db.collection("Parametros").document("Biomasa").set(params)

         params = hashMapOf(
             "param10" to hashMapOf("id" to 10, "nombre" to "% CaO", "objetivo" to 80),
             "param11" to hashMapOf(
                 "id" to 11,
                 "nombre" to "% Pérdidas por Calcinación",
                 "objetivo" to 3
             )
         )
         db.collection("Parametros").document("Calcinación").set(params)

         params = hashMapOf(
             "param12" to hashMapOf(
                 "id" to 12,
                 "nombre" to "% Ca(OH)2 Cal Química",
                 "objetivo" to 90
             ),
             "param13" to hashMapOf(
                 "id" to 13,
                 "nombre" to "% Ca(OH)2 Cal Construcción",
                 "objetivo" to 80
             )
         )
         db.collection("Parametros").document("Hidratación").set(params)
     }


     fun createParametros2()
     {
         val db = Firebase.firestore

         db.collection("Parametros").document("Trituracion1").set(
             hashMapOf(
                 "id" to 1,
                 "proceso" to jprocesos[0],
                 "nombre" to "% CaCO3 en Piedra",
                 "objetivo" to 90,
                 "naturaleza" to "desc"
             )
         )

         db.collection("Parametros").document("MNC1").set(
             hashMapOf(
                 "id" to 2,
                 "proceso" to jprocesos[1],
                 "nombre" to "% Finos Carbonato 2-4 mm",
                 "objetivo" to 5,
                 "naturaleza" to "asc"
             )
         )
         db.collection("Parametros").document("MNC2").set(
             hashMapOf(
                 "id" to 3,
                 "proceso" to jprocesos[1],
                 "nombre" to "% Finos Carbonato 0.85-2 mm",
                 "objetivo" to 10,
                 "naturaleza" to "asc"
             )
         )
         db.collection("Parametros").document("MNC3").set(
             hashMapOf(
                 "id" to 4,
                 "proceso" to jprocesos[1],
                 "nombre" to "% Humedad Salida del Secador",
                 "objetivo" to 1,
                 "naturaleza" to "asc"
             )
         )
         db.collection("Parametros").document("MNC4").set(
             hashMapOf(
                 "id" to 5,
                 "proceso" to jprocesos[1],
                 "nombre" to "% Humedad CaCO3 Pulverizado",
                 "objetivo" to 1,
                 "naturaleza" to "asc"
             )
         )
         db.collection("Parametros").document("MNC5").set(
             hashMapOf(
                 "id" to 6,
                 "proceso" to jprocesos[1],
                 "nombre" to "% Retenido Malla 325 CaCO3 Pulverizado",
                 "objetivo" to 20,
                 "naturaleza" to "asc"
             )
         )
         db.collection("Parametros").document("MNC6").set(
             hashMapOf(
                 "id" to 7,
                 "proceso" to jprocesos[1],
                 "nombre" to "% Retenido Malla 200 CaCO3 Pulverizado",
                 "objetivo" to 10,
                 "naturaleza" to "asc"
             )
         )

         db.collection("Parametros").document("Biomasa1").set(
             hashMapOf(
                 "id" to 8,
                 "proceso" to jprocesos[2],
                 "nombre" to "% Humedad Salida Secador Biomasa",
                 "objetivo" to 12,
                 "naturaleza" to "asc"
             )
         )
         db.collection("Parametros").document("Biomasa2").set(
             hashMapOf(
                 "id" to 9,
                 "proceso" to jprocesos[2],
                 "nombre" to "% Retenido Malla 7 Biomasa",
                 "objetivo" to 3,
                 "naturaleza" to "asc"
             )
         )

         db.collection("Parametros").document("Calcinacion1").set(
             hashMapOf(
                 "id" to 10,
                 "proceso" to jprocesos[3],
                 "nombre" to "% CaO",
                 "objetivo" to 80,
                 "naturaleza" to "desc"
             )
         )
         db.collection("Parametros").document("Calcinacion2").set(
             hashMapOf(
                 "id" to 11,
                 "proceso" to jprocesos[3],
                 "nombre" to "% Pérdidas por Calcinación",
                 "objetivo" to 3,
                 "naturaleza" to "asc"
             )
         )

         db.collection("Parametros").document("Hidratacion1").set(
             hashMapOf(
                 "id" to 12,
                 "proceso" to jprocesos[4],
                 "nombre" to "% Ca(OH)2 Cal Química",
                 "objetivo" to 90,
                 "naturaleza" to "desc"
             )
         )
         db.collection("Parametros").document("Hidratacion2").set(
             hashMapOf(
                 "id" to 13,
                 "proceso" to jprocesos[4],
                 "nombre" to "% Ca(OH)2 Cal Construcción",
                 "objetivo" to 80,
                 "naturaleza" to "desc"
             )
         )
     }

     private fun showToast(
         context: Context = applicationContext,
         message: String,
         duration: Int = Toast.LENGTH_LONG
     ) {
         Toast.makeText(context, message, duration).show()
     }

}