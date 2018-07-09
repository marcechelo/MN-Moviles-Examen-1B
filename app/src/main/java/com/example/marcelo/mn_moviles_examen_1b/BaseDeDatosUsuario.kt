package com.example.marcelo.mn_moviles_examen_1b

import android.os.StrictMode
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpGet

class BaseDeDatosUsuario {


    companion object {

        fun getUsuario(usuario: String): ArrayList<Usuario> {
            val usuarios: ArrayList<Usuario> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://172.31.105.205:1337/Usuario?username=$usuario".httpGet().responseString()
            val jsonStringLibro = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringLibro)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val username = it["username"] as String
                val password = it["password"] as String
                val tipo = it["tipo"] as Int

                val usuario = Usuario(id, tipo, username, password)
                usuarios.add(usuario)
            }
            return usuarios
        }

    }



}