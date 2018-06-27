package com.example.marcelo.mn_moviles_examen_1b

import android.util.Log
import android.os.StrictMode
import com.beust.klaxon.*
import com.github.kittinunf.fuel.*

class BaseDeDatosSo {

    companion object {

        fun postSistemaOperativo(sistema: SO) {
            "http://172.31.104.13:1337/SistemaOperativo".httpPost(listOf("nombreSo" to sistema.nombre, "versionApi" to sistema.versionApi, "fechaLanzamiento" to sistema.fechaLanzamiento, "pesoGigasSo" to sistema.pesoEnGigas, "instalado" to sistema.instalado))
                    .responseString { request, response, result ->
                        Log.d("request", request.toString())
                    }
        }

        fun putSistemaOperativo(sistema: SO) {
            "http://172.31.104.13:1337/SistemaOperativo/${sistema.id}".httpPut(listOf("nombreSo" to sistema.nombre, "versionApi" to sistema.versionApi, "fechaLanzamiento" to sistema.fechaLanzamiento, "pesoGigasSo" to sistema.pesoEnGigas, "instalado" to sistema.instalado))
                    .responseString { request, response, result ->
                        Log.d("request", request.toString())
                    }
        }

        fun deleteSistemaOperativo(id: Int) {
            "http://172.31.104.13:1337/SistemaOperativo/$id".httpDelete()
                    .responseString { request, response, result ->
                        Log.d("request", request.toString())
                    }
        }

        fun getList(): ArrayList<SO> {
            val sistemas: ArrayList<SO> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://172.31.104.13:1337/SistemaOperativo".httpGet().responseString()
            val jsonStringAutor = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringAutor)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val nombre = it["nombreSo"] as String
                val version = it["versionApi"] as Int
                val fecha = it["fechaLanzamiento"] as String
                val peso = it["pesoGigasSo"] as Double
                val instalado = it["instalado"] as Int
                val sistema = SO(id, nombre, version, fecha, peso, instalado, 0, 0)
                sistemas.add(sistema)
            }
            return sistemas
        }

    }


}

