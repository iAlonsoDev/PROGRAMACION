package com.example.incaldesviaciones

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class PopupCoordinador : DialogFragment() {
    // Use this instance of the interface to deliver action events
    internal lateinit var listener: NoticeDialogListener
    val db = Firebase.firestore


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

    fun newInstance(id: String, parametro: String, puntuacion: String, correccion: String, correccionEsperaDate: String, correccionEsperaTime: String): PopupCoordinador {
        val frag = PopupCoordinador()
        val args = Bundle()
        args.putString("id", id)
        args.putString("parametro", parametro)
        args.putString("puntuacion", puntuacion)
        args.putString("correccion", correccion)
        args.putString("correccionEsperaDate", correccionEsperaDate)
        args.putString("correccionEsperaTime", correccionEsperaTime)
        frag.setArguments(args)
        return frag
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {

            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val dialogView: View = inflater.inflate(R.layout.coordinador_layout, null)
            builder.setView(dialogView)

            val xid: String? = requireArguments().getString("id")
            val xparametro: String? = requireArguments().getString("parametro")
            val xpuntuacion: String? = requireArguments().getString("puntuacion")
            val xcorreccion: String? = requireArguments().getString("correccion")
            val xdate: String? = requireArguments().getString("correccionEsperaDate")
            val xtime: String? = requireArguments().getString("correccionEsperaTime")

            val txtId = dialogView.findViewById<View>(R.id.txtId) as EditText
            val xTitle = dialogView.findViewById<View>(R.id.txtTitle) as EditText
            val txtParametro = dialogView.findViewById<View>(R.id.txtParametro) as EditText
            val txtCorreccion = dialogView.findViewById<View>(R.id.txtNewResult) as EditText
            val txtDate = dialogView.findViewById<View>(R.id.txtDate) as EditText
            val txtTime = dialogView.findViewById<View>(R.id.txtTime) as EditText

            val btnPickTime = dialogView.findViewById<Button>(R.id.btnPickTime)
            val btnPickDate = dialogView.findViewById<Button>(R.id.btnPickDate)

            txtId.setText(xid)
            xTitle.setText("Corregir Desviación")
            txtParametro.setText(xparametro+" ["+xpuntuacion+"]")
            txtCorreccion.setText(xcorreccion)
            txtDate.setText(xdate)
            txtTime.setText(xtime)

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            btnPickDate.setOnClickListener {
                val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    var jmonth=monthOfYear+1
                    var txtmonth=""

                    if (jmonth<10) {
                        txtmonth = "0" + jmonth
                    }
                    else {
                        txtmonth = ""+ jmonth
                    }
                    txtDate.setText("" + year + "-" + txtmonth + "-" + dayOfMonth)
                }, year, month, day)
                dpd.show()
            }

            btnPickTime.setOnClickListener {
                val cal = Calendar.getInstance()
                val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)
                    txtTime.setText ( SimpleDateFormat("HH:mm").format(cal.time))
                }
                TimePickerDialog(activity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }

            builder.setPositiveButton(R.string.guardar,
                    DialogInterface.OnClickListener { dialog, id ->
                        //listener.onDialogPositiveClick(this)
                        //Toast.makeText(activity, "Corrección Propuesta: "+ xCorreccion.text.toString()+"\n"+"Tiempo Estimado: "+xTime.text.toString(), Toast.LENGTH_SHORT).show()

                        //valido la fecha y hora
                        if (txtDate.text.toString() == "" && txtTime.text.toString() == "")
                        {
                            Toast.makeText(activity, "Por favor, selecciones una fecha y una hora para la siguiente medicion", Toast.LENGTH_SHORT).show()
                        } else {

                            val jupdate = db.collection("Desviaciones").document(txtId.text.toString())
                            jupdate.update("correccion", txtCorreccion.text.toString())
                            jupdate.update("correccionEsperaDate", txtDate.text.toString())
                            jupdate.update("correccionEsperaTime", txtTime.text.toString())
                            jupdate.update("responsable", User.nombre)

                            //notifico la actualizacion
                            if (User.tipo != "Analista") {
                                Toast.makeText(activity, "Desviacion Actualizada Exitosamente", Toast.LENGTH_SHORT).show()
                            }
                            //.addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                        }

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
