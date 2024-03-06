package com.example.incaldesviaciones

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Double.parseDouble
import java.text.SimpleDateFormat
import java.util.*
import java.lang.Integer.parseInt


class PopupAnalista : DialogFragment() {
    // Use this instance of the interface to deliver action events
    internal lateinit var listener: NoticeDialogListener
    val db = Firebase.firestore

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    fun newInstance(id: String, proceso: String, parametro: String, puntuacion: String, resultado: Double, objetivo: Double , naturaleza: String, cnt: String): PopupAnalista {
        val frag = PopupAnalista()
        val args = Bundle()
        args.putString("id", id)
        args.putString("proceso", proceso)
        args.putString("parametro", parametro)
        args.putString("puntuacion", puntuacion)
        args.putDouble("resultado", resultado)
        args.putDouble("objetivo", objetivo)
        args.putString("naturaleza", naturaleza)
        args.putString("cnt", cnt)
        frag.setArguments(args)
        return frag
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val dialogView: View = inflater.inflate(R.layout.analista_popup_layout, null)
            builder.setView(dialogView)

            val xid: String? = requireArguments().getString("id")
            val xproceso: String? = requireArguments().getString("proceso")
            val xparametro: String? = requireArguments().getString("parametro")
            val xpuntuacion: String? = requireArguments().getString("puntuacion")
            val xresult: Double = requireArguments().getDouble("resultado")
            val xobject: Double = requireArguments().getDouble("objetivo")
            val xnaturaleza: String? = requireArguments().getString("naturaleza")
            var xcnt: String? = requireArguments().getString("cnt")

            val txtId = dialogView.findViewById<View>(R.id.txtId) as EditText
            val xTitle = dialogView.findViewById<View>(R.id.txtTitle) as EditText
            val txtParametro = dialogView.findViewById<View>(R.id.txtParametro) as EditText
            val txtNewResult = dialogView.findViewById<View>(R.id.txtNewResult) as EditText
            //val txtDate = dialogView.findViewById<View>(R.id.txtDate) as EditText
            //val txtTime = dialogView.findViewById<View>(R.id.txtTime) as EditText

            txtId.setText(xid)
            xTitle.setText("Ingresar nuevo resultado")
            txtParametro.setText(xparametro+" ["+xpuntuacion+"]")
            txtNewResult.setText(xresult.toString())

            builder.setPositiveButton(R.string.guardar,
                DialogInterface.OnClickListener { dialog, id ->
                    //listener.onDialogPositiveClick(this)
                    //Toast.makeText(activity, "Corrección Propuesta: "+ xCorreccion.text.toString()+"\n"+"Tiempo Estimado: "+xTime.text.toString(), Toast.LENGTH_SHORT).show()

                    val calendar = Calendar.getInstance()
                    val jdate = calendar.getTime()
                    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    sdf.setTimeZone(TimeZone.getTimeZone("America/Tegucigalpa"))

                    val jfecha = sdf.format(jdate)
                    var desviado = 0
                    var resultado  = parseDouble(txtNewResult.text.toString())

                    if ((xnaturaleza=="asc" && resultado>xobject) || (xnaturaleza=="desc" && resultado<xobject))
                        desviado=2

                    val jupdate = db.collection("Desviaciones").document(txtId.text.toString())
                    jupdate.update("desviado", desviado)
                    jupdate.update("nuevoResultado", resultado)
                    jupdate.update("fechaNuevoResultado", jNow())


                    var x = Util

                    if (desviado==2){
                        var cnt= parseInt(xcnt)
                        cnt+=1
                        var txtcnt=cnt.toString()
                       x.jInsert(xobject, resultado, xnaturaleza, xproceso, xparametro, txtId.text.toString(), txtcnt)
                        Toast.makeText(activity, "Nueva desviacion agregada", Toast.LENGTH_SHORT).show()
                    }

                    if (User.tipo == "Analista") {
                        Toast.makeText(activity, "Desviacion Actualizada Exitosamente", Toast.LENGTH_SHORT).show()
                    }
                    //.addOnSuccessListener { Toast.makeText(activity, "Actualización Exitosa..! ", Toast.LENGTH_SHORT).show() }
                    //.addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        // Send the negative button event back to the host activity
                        listener.onDialogNegativeClick(this)
                    })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}