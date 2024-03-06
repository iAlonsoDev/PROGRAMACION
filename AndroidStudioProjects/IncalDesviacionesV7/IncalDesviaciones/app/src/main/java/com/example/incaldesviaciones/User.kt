package com.example.incaldesviaciones

class User {
    companion object {
        var nombre: String = ""
        var proceso: String = ""
        var tipo: String = ""
        var iddesv: String = ""
        var detalle: String = ""

        fun showUser() {
            println("Usuario Online: "+ nombre)
        }
    }
}