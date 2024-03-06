package com.example.incaldesviaciones

final data class Desviacion(
    val id: String,
    val proceso: String,
    val parametro: String,
    val fecha: String,
    val objetivo: Double,
    val resultado: Double,
    val naturaleza: String,
    val correccion: String,
    val correccionEsperaDate: String,
    val correccionEsperaTime: String,
    val detalle: String,
    val puntuacion: String,
    val cnt: String
)