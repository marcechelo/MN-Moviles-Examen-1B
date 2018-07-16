package com.example.marcelo.mn_moviles_examen_1b

import android.os.StrictMode
import android.util.Log
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut

class BaseDeDatosDetalle {

    companion object {

        val ip = "192.168.1.5:1337"
        fun postDetalle(detalle: DetalleOrden) {
            "http://${this.ip}/DetalleOrden".httpPost(listOf("orden" to detalle.idOrden, "precio" to detalle.precio))
                    .responseString { request, response, result ->
                        Log.d("request", request.toString())
                    }
        }

        fun putDetalle(detalle: DetalleOrden) {
            "http://${this.ip}/DetalleOrden/${detalle.id}".httpPut(listOf("precio" to detalle.precio))
                    .responseString { request, response, result ->
                        Log.d("request", request.toString())
                    }
        }

        fun deleteOrden(id: Int) {
            "http://${this.ip}/DetalleOrden/$id".httpDelete()
                    .responseString { request, response, result ->
                        Log.d("request", request.toString())
                    }
        }

            fun getDetalle(orden: Int): ArrayList<DetalleOrden>{
            val ordenes: ArrayList<DetalleOrden> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://${this.ip}/DetalleOrden?orden=$orden".httpGet().responseString()
            val jsonStringAutor = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringAutor)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val precio = it["precio"] as Double
                //val orden = it["orden"] as Int

                val sistema = DetalleOrden(id, precio, 0,0,0)
                ordenes.add(sistema)
            }
            return ordenes
        }

    }

}