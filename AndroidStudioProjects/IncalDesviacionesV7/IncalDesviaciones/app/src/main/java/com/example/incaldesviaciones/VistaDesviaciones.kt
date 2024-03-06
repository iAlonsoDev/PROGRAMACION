package com.example.incaldesviaciones
import SegundoPlano.ProcessMainClass
import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.desviacion_layout.*
import kotlinx.android.synthetic.main.desviacion_layout.view.*
import kotlinx.android.synthetic.main.desviaciones_layout.*
import kotlinx.android.synthetic.main.layout_login.*
import kotlinx.android.synthetic.main.mediciones_calidad_layout.*
import java.lang.Double.parseDouble
import java.util.*

class VistaDesviaciones : FragmentActivity(),

    PopupCoordinador.NoticeDialogListener, PopupAnalista.NoticeDialogListener {
    val db = Firebase.firestore
    var procDesviado= ""
    var paramDesviado= ""

    //notificacion
    val channelID = "Departamento TI"
    val channelName = "INCAL S.A DE C.V."
    val notificationID = 0

    companion object{
        const val INTENT_REQUEST = 0
    }

    private fun showToast(
        context: Context = applicationContext,
        message: String,
        duration: Int = Toast.LENGTH_LONG
    ) {
        Toast.makeText(context, message, duration).show()
    }

    private val adapter by lazy {
        Desviacion_Adapter()
    }

    fun showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog = PopupCoordinador()
        dialog.show(supportFragmentManager, "NoticeDialogFragment")
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    override fun onDialogPositiveClick(dialog: DialogFragment) {
        // User touched the dialog's positive button
        //Toast.makeText(this, "Bottom OK", Toast.LENGTH_SHORT).show()
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        // User touched the dialog's negative button
        //Toast.makeText(this, "Bottom NO", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = Color.parseColor("#94E4E4E4")
        window.decorView.systemUiVisibility = View.AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS

        setContentView(R.layout.desviaciones_layout)

        createNotificationChannel()

        //para abrir la notificacion
        val intent = Intent(this, VistaDesviaciones::class.java)
        val pendingIntent : PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(INTENT_REQUEST, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        //creacion de la notificacion
        /*
        val notificationADD = NotificationCompat.Builder(this, channelID).also {
            it.setContentTitle("Nueva Desviación Creada")
            it.setContentText("Parámetro: "+paramDesviado)
            it.setSmallIcon(R.mipmap.incal)
            it.setPriority(NotificationCompat.PRIORITY_MAX)
            it.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

            //para cuando clickea la desviacion
            it.setContentIntent(pendingIntent)
            it.setAutoCancel(true)
        }.build()

        //creacion de la notificacion

        val notificationREP = NotificationCompat.Builder(this, channelID).also {
            it.setContentTitle("Repetición de Desviación")
            it.setContentText("Parámetro: "+paramDesviado)
            it.setSmallIcon(R.mipmap.incal)
            it.setPriority(NotificationCompat.PRIORITY_MAX)
            it.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

            //para cuando clickea la desviacion
            it.setContentIntent(pendingIntent)
            it.setAutoCancel(true)
        }.build()

        val notificationMODIFIED = NotificationCompat.Builder(this, channelID).also {
            it.setContentTitle("Corrección de Desviación Ingresada")
            it.setContentText("Proceso: "+paramDesviado)
            it.setSmallIcon(R.mipmap.incal)
            it.setPriority(NotificationCompat.PRIORITY_MAX)
            it.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

            //para cuando clickea la desviacion
            it.setContentIntent(pendingIntent)
            it.setAutoCancel(true)
        }.build()

        val notificationCLOSE = NotificationCompat.Builder(this, channelID).also {
            it.setContentTitle("Cierre de Desviación")
            it.setContentText("Parámetro: "+paramDesviado)
            it.setSmallIcon(R.mipmap.incal)
            it.setPriority(NotificationCompat.PRIORITY_MAX)
            it.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

            //para cuando clickea la desviacion
            it.setContentIntent(pendingIntent)
            it.setAutoCancel(true)
        }.build()
        */


        /*-------------------- DEFINICION DE NOTIFICACIONES ------------------*/
        val notificationADD = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Nueva Desviación Creada")
            .setSmallIcon(R.mipmap.incal)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationREP = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Repetición de Desviación")
            .setSmallIcon(R.mipmap.incal)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationMODIFIED = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Corrección de Desviación Ingresada")
            .setSmallIcon(R.mipmap.incal)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationCLOSE = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Cierre de Desviación")
            .setSmallIcon(R.mipmap.incal)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        /*--------------------------------------------------------------*/

        val notificationManager = NotificationManagerCompat.from(this)

        db.collection("Desviaciones")
            .addSnapshotListener { snapshots, e ->
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.ADDED -> if (adapter.getItemCount() != 0) {
                            if ((User.tipo == "Coordinador" && dc.document.data.get("correccion") == "" && dc.document.data.get("proceso") == "${User.proceso}")
                                || ((User.tipo == "Master" || User.tipo == "Supervisor") && dc.document.data.get("correccion") == "")
                            ) {
                                //PARA NOTIFICAR
                                if (dc.document.data.get("cnt")=="1"){
                                    notificationADD.setContentText("Parámetro: "+dc.document.data.get("parametro").toString())
                                    if (ActivityCompat.checkSelfPermission(
                                            this,
                                            Manifest.permission.POST_NOTIFICATIONS
                                        ) != PackageManager.PERMISSION_GRANTED
                                    ) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return@addSnapshotListener
                                    }
                                    notificationManager.notify(notificationID, notificationADD.build())
                                }
                                else{
                                    notificationREP.setContentText("Parámetro: "+dc.document.data.get("parametro").toString())
                                    notificationManager.notify(notificationID, notificationREP.build())
                                }

                                //PARA RESALTAR LA DESVIACION
                                User.iddesv = dc.document.data.get("id").toString()
                            }
                        }

                        DocumentChange.Type.MODIFIED -> TODO()
                        DocumentChange.Type.REMOVED -> TODO()
                    }
                }
            }

        /*Evento cuando el coordinador responde la desviacion y propone una correción
        * Notificacion enviada a Todos los usuarios excepto al coordiandor*/
        db.collection("Desviaciones")
            .addSnapshotListener { snapshots, e ->
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.MODIFIED -> if (adapter.getItemCount() != 0) {
                            if (dc.document.data.get("correccion") != "" && User.tipo != "Coordinador" && dc.document.data.get("nuevoResultado") == "")
                            {
                                notificationMODIFIED.setContentText("Parámetro: "+dc.document.data.get("parametro").toString())
                                notificationManager.notify(notificationID, notificationMODIFIED.build())

                                //PARA RESALTAR LA DESVIACION
                                User.iddesv = dc.document.data.get("id").toString()
                            }
                        }

                        DocumentChange.Type.ADDED -> TODO()
                        DocumentChange.Type.REMOVED -> TODO()
                    }
                }
            }

        /*Alerta de cierre de desviación*/
        db.collection("Desviaciones")
            .addSnapshotListener { snapshots, e ->
                for (dc in snapshots!!.documentChanges) {
                    when (dc.type) {
                        DocumentChange.Type.MODIFIED ->  {
                            if (dc.document.data.get("desviado").toString()=="0")
                            {
                                procDesviado= dc.document.data.get("proceso").toString()
                                paramDesviado= dc.document.data.get("parametro").toString()

                                //Log.w("TEST", "Event Modified: "+dc.document.data.get("id")+" ; "+dc.document.data.get("desviado"), e)
                                if ((User.tipo == "Coordinador" && dc.document.data.get("proceso") == "${User.proceso}") || (User.tipo == "Supervisor" || User.tipo == "Master")) {
                                    notificationCLOSE.setContentText("Parámetro: "+dc.document.data.get("parametro").toString())
                                    notificationManager.notify(notificationID, notificationCLOSE.build())
                                }
                            }
                        }

                        DocumentChange.Type.ADDED -> TODO()
                        DocumentChange.Type.REMOVED -> TODO()
                    }
                }
            }

        /*Monitoreo de desviaciones activas*/
        /*Validacion de mediciones por area, si es master cargue todas*/
        //condicion where para mostrar las desviaciones por area
        if (User.tipo == "Analista" || User.tipo == "Master" || User.tipo == "Supervisor") {
            db.collection("Desviaciones")
                .whereEqualTo("desviado", 1)
        } else {
            db.collection("Desviaciones")
                .whereEqualTo("desviado", 1)
                .whereEqualTo("proceso", "${User.proceso}")
            //.orderBy("desviado")
            //.orderBy("fecha", Query.Direction.ASCENDING)
        }

        .addSnapshotListener { value, e ->
            if (e != null) {
                Log.w("TAG", "Listen failed.", e)
                return@addSnapshotListener
            }

            val jdesv = ArrayList<Desviacion>()

            for (doc in value!!) {
                var resultado = parseDouble(doc.get("resultado").toString())
                var newResult = doc.get("nuevoResultado").toString()
                if (newResult != "")
                    resultado = parseDouble(newResult)

                jdesv.add(
                    Desviacion(
                        doc.get("id").toString(),
                        doc.get("proceso").toString(),
                        doc.get("parametro").toString(),
                        doc.get("fecha").toString(),
                        parseDouble(doc.get("objetivo").toString()),
                        resultado,
                        doc.get("naturaleza").toString(),
                        doc.get("correccion").toString(),
                        doc.get("correccionEsperaDate").toString(),
                        doc.get("correccionEsperaTime").toString(),
                        "Correccion: " +
                                doc.get("correccion")
                                    .toString() + "\nNueva Medición: " + doc.get("correccionEsperaDate")
                            .toString() + " " + doc.get("correccionEsperaTime")
                            .toString() + "\n" +
                                doc.get("fecha").toString(),
                        resultado.toString() + "/" + doc.get("objetivo").toString(),
                        doc.get("cnt").toString()
                    )
                )
            }


            //se adapta el recycler para que reciba las desviaciones
            recycler_view.adapter = adapter



            // valido el boton de agregar desviaciones
            if (User.tipo == "Coordinador" || User.tipo == "Supervisor") {
                btnAgregar.setVisibility(View.GONE);
            } else
            {
                btnAgregar.setOnClickListener() {
                val cambiarVentana: Intent = Intent(this, MedicionesCalidad::class.java)
                startActivity(cambiarVentana)
            }}

            //se cargan las desviaciones
            adapter.addItems(jdesv)

            // se valida el tipo de usuario para contestar las desviaciones
            adapter.listener = { view, item, position ->
                if (User.tipo == "Master" ) {
                    // setup the alert builder
                    val builder = AlertDialog.Builder(this).setTitle("Elija Una Vista")

                    val views = arrayOf("Vista Coordinador", "Vista Analista")
                    builder.setItems(views) { dialog, which ->
                        when (which) {
                            0 -> {
                                viewCoordinador(item)
                            }
                            1 -> {
                                viewAnalista(item)
                            }
                        }
                    }
                    val dialog = builder.create().show()
                }

                if (User.tipo == "Coordinador") {
                    viewCoordinador(item)
                }

                if (User.tipo == "Analista") {
                    viewAnalista(item)
                }
            }

        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelID, channelName, importance).apply {
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun viewCoordinador(item: Desviacion){
        //val fragmentManager: FragmentManager = supportFragmentManager
        val dialog = PopupCoordinador().newInstance(
            item.id,
            item.parametro,
            item.puntuacion,
            item.correccion,
            item.correccionEsperaDate,
            item.correccionEsperaTime
        ).show(supportFragmentManager, "tagAlerta")
    }

    fun viewAnalista(item: Desviacion){
        //val fragmentManager: FragmentManager = supportFragmentManager
        val dialog = PopupAnalista().newInstance(
            item.id,
            item.proceso,
            item.parametro,
            item.puntuacion,
            item.resultado,
            item.objetivo,
            item.naturaleza,
            item.cnt
        ).show(supportFragmentManager, "tagAlerta")
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RestartServiceBroadcastReceiver.scheduleJob(applicationContext)
        } else {
            val bck = ProcessMainClass()
            bck.launchService(applicationContext)
        }
    }


}



