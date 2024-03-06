package com.example.incaldesviaciones

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.layout_login.*


@Suppress("DEPRECATION")
class Login : AppCompatActivity() {

    val db = Firebase.firestore

    private fun showToast(
        context: Context = applicationContext,
        message: String,
        duration: Int = Toast.LENGTH_LONG
    ) {
        Toast.makeText(context, message, duration).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Para revisar el login en cache e cargar variables de cache
        val prefs = getApplicationContext().getSharedPreferences("name", MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)

        if(isLoggedIn){
            User.tipo = prefs.getString("tipo", "").toString()
            User.proceso = prefs.getString("proceso", "").toString()
            User.nombre = prefs.getString("nombre", "").toString()

            val cambiarVentana: Intent
            //if (User.tipo=="Coordinador")
            //    cambiarVentana = Intent(this, VistaDesviaciones::class.java)
            // else
                cambiarVentana = Intent(this, VistaDesviaciones::class.java)
            startActivity(cambiarVentana)
            finish()
            return
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = Color.TRANSPARENT

        //para quitar la barra de navegacion
        if(getSupportActionBar() != null)
            getSupportActionBar()?.hide()
        window.decorView.systemUiVisibility = View.AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS

        setContentView(R.layout.layout_login)

        btninicar.setOnClickListener(){

            //para verificar si hay internet
            val connectivityManager =
                getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected()) {

                //para validar el usuario e cargar variables login local permamente
                db.collection("Usuarios")
                    .whereEqualTo("username", txtuser.text.toString())
                    .get()
                    .addOnSuccessListener { documents ->

                        var flag =false

                        for (x in documents) {
                            if (x.data.get("password").toString()==txtpassword.text.toString()) {
                                flag = true
                                User.proceso = x.data.get("proceso").toString()
                                User.tipo = x.data.get("tipo").toString()
                                User.nombre = x.data.get("nombre").toString()

                                //variables login permanentes
                                val editor = getSharedPreferences("name", MODE_PRIVATE).edit()
                                editor.putString("user", txtuser.text.toString())
                                editor.putString("password", txtpassword.text.toString())
                                editor.putString("proceso", x.data.get("proceso").toString())
                                editor.putString("tipo", x.data.get("tipo").toString())
                                editor.putString("nombre", x.data.get("nombre").toString())
                                editor.putBoolean("isLoggedIn", true)
                                editor.apply()
                            }
                        }

                        if (flag){
                            val cambiarVentana: Intent

                            // if (User.tipo=="Coordinador")
                            //    cambiarVentana = Intent(this, VistaDesviaciones::class.java)
                            // else
                             cambiarVentana = Intent(this, VistaDesviaciones::class.java)
                            startActivity(cambiarVentana)
                            //finish()
                        }
                        else
                            showToast(message = "Datos Incorrectos..!")
                    }
            } else {
                showToast(message = "Se requiere conexion a Internet!")
            }
        }
    }



}