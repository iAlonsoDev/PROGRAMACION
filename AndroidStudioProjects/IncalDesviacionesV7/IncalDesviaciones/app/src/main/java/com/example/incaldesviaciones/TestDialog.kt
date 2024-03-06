package com.example.incaldesviaciones

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.coordinador_layout.*


class TestDialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.desviaciones_layout)

        val jintent: Intent= intent
        txtTitle.setText(jintent.getStringExtra("title"))
    }

}