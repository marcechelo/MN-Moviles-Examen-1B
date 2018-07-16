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
import javax.xml.transform.Result

class BaseDeDatosOrdenes {

    companion object {

        val ip = "172.31.104.20:1337"
        var identificador = 0
        fun postOrden(orden: Ordenes): Int {
            identificador += 1
             var resultado: Ordenes
            "http://${this.ip}/Ordenes".httpPost(listOf("fecha" to orden.fecha, "total" to orden.total, "estado" to orden.estado, "usuario" to orden.idUsuario, "lugar" to orden.lugar))
                    .responseString { request, response, result ->
                        Log.d("request", request.toString())
                    }
            return identificador
        }

        fun putOrden(orden: Ordenes) {
            "http://${this.ip}/Ordenes/${orden.id}".httpPut(listOf("costoDelivery" to orden.costoDelivery, "fechaEntrega" to orden.fechaEntrega, "estado" to orden.estado))
                    .responseString { request, response, result ->
                        Log.d("request", request.toString())
                    }
        }

        fun deleteOrden(id: Int) {
            "http://${this.ip}/Ordenes/$id".httpDelete()
                    .responseString { request, response, result ->
                        Log.d("request", request.toString())
                    }
        }

        fun getList(usuario: Int): ArrayList<Ordenes> {
            val ordenes: ArrayList<Ordenes> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://${this.ip}/Ordenes?usuario=$usuario".httpGet().responseString()
            val jsonStringAutor = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringAutor)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val fecha = it["fecha"] as String
                val total = it["total"] as Double
                val estado = it["estado"] as Int
                val lugar = it["lugar"] as String
                val costoDelivery = it["costoDelivery"] as Double
                val fechaEntrega = it["fechaEntrega"] as String
                val idUsuario = it["idUsuario"] as Int
                val idDetalle = it["idDetalle"] as Int

                val sistema = Ordenes(id, fecha, total, estado, lugar, costoDelivery, fechaEntrega, idUsuario, idDetalle, 0, 0)
                ordenes.add(sistema)
            }
            return ordenes
        }

    }

}