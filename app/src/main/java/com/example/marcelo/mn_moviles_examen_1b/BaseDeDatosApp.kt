package com.example.marcelo.mn_moviles_examen_1b

import android.os.StrictMode
import android.util.Log
import com.beust.klaxon.*
import com.github.kittinunf.fuel.*

class BaseDeDatosApp {


    companion object {
        val ip = "192.168.1.4:1337"

        fun postAplicacion(aplicacion: App) {
            "http://${this.ip}/Aplicaciones".httpPost(listOf("nombreApp" to aplicacion.nombre, "pesoGigasApp" to aplicacion.pesoEnGigas, "version" to aplicacion.version, "urlApp" to aplicacion.urlDescarga, "fechaLanzamientoApp" to aplicacion.fechaLanzamiento, "costo" to aplicacion.costo, "estado" to aplicacion.estado, "sistemaOperativoId" to aplicacion.sistemaOperativoId, "foto" to aplicacion.foto))
                    .responseString { request, response, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun putAplicacion(aplicacion: App) {
            "http://${this.ip}/Aplicaciones/${aplicacion.appid}".httpPut(listOf("nombreApp" to aplicacion.nombre, "pesoGigasApp" to aplicacion.pesoEnGigas, "version" to aplicacion.version, "urlApp" to aplicacion.urlDescarga, "fechaLanzamientoApp" to aplicacion.fechaLanzamiento, "costo" to aplicacion.costo, "sistemaOperativoId" to aplicacion.sistemaOperativoId, "foto" to aplicacion.foto))
                    .responseString { request, responde, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun putAplicacionEstado(aplicacion: App, estado: Int) {
            "http://${this.ip}/Aplicaciones/${aplicacion.appid}".httpPut(listOf("estado" to estado))
                    .responseString { request, responde, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun deleteAplicacion(id: Int) {
            "http://${this.ip}/Aplicaciones/$id".httpDelete()
                    .responseString { request, response, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun getAplicaciones(idSistema: Int): ArrayList<App> {
            val aplicaciones: ArrayList<App> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://${this.ip}/Aplicaciones?sistemaOperativoId=$idSistema".httpGet().responseString()
            val jsonStringLibro = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringLibro)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val nombre = it["nombreApp"] as String
                val peso = it["pesoGigasApp"] as Double
                val version = it["version"] as Int
                val url = it["urlApp"] as String
                val fecha = it["fechaLanzamientoApp"] as String
                val costo = it["costo"] as Double
                val estado = it["estado"] as Int
                val foto = it["foto"] as String
                //val latitud = it["latitud"] as Double
                //val longitud = it["longitud"] as Double

                //val app = App(id, nombre, peso, version, url, fecha, costo, latitud, longitud, idSistema, 0, 0)
                val app = App(id, nombre, peso, version, url, fecha, costo, estado, idSistema, foto, 0, 0)
                aplicaciones.add(app)
            }
            return aplicaciones
        }

        fun getTodasAplicaciones(): ArrayList<App> {
            val aplicaciones: ArrayList<App> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://${this.ip}/Aplicaciones".httpGet().responseString()
            val jsonStringLibro = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringLibro)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val nombre = it["nombreApp"] as String
                val peso = it["pesoGigasApp"] as Double
                val version = it["version"] as Int
                val url = it["urlApp"] as String
                val fecha = it["fechaLanzamientoApp"] as String
                val foto = it["foto"] as String
                val costo = it["costo"] as Double
                val idSistema = it["id"] as Int
                val estado = it["estado"] as Int
                //val latitud = it["latitud"] as Double
                //val longitud = it["longitud"] as Double

                //val app = App(id, nombre, peso, version, url, fecha, costo, latitud, longitud, idSistema, 0, 0)
                val app = App(id, nombre, peso, version, url, fecha, costo, estado, idSistema, foto, 0, 0)
                aplicaciones.add(app)
            }
            return aplicaciones
        }

        fun getAplicacionesBusqueda(nombre:String): ArrayList<App> {
            val aplicaciones: ArrayList<App> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://${this.ip}/Aplicaciones?nombreApp=$nombre".httpGet().responseString()
            val jsonStringLibro = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringLibro)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val nombre = it["nombreApp"] as String
                val peso = it["pesoGigasApp"] as Double
                val version = it["version"] as Int
                val url = it["urlApp"] as String
                val fecha = it["fechaLanzamientoApp"] as String
                val foto = it["foto"] as String
                val costo = it["costo"] as Double
                val idSistema = it["id"] as Int
                val estado = it["estado"] as Int
                //val latitud = it["latitud"] as Double
                //val longitud = it["longitud"] as Double

                //val app = App(id, nombre, peso, version, url, fecha, costo, latitud, longitud, idSistema, 0, 0)
                val app = App(id, nombre, peso, version, url, fecha, costo, estado, idSistema, foto, 0, 0)
                aplicaciones.add(app)
            }
            return aplicaciones
        }
        fun getAplicacioneUnica(id: Int): App {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://${this.ip}/Aplicaciones/$id".httpGet().responseString()
            val jsonStringLibro = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringLibro)
            val app = parser.parse(stringBuilder) as JsonObject
            val id = app["id"] as Int
            val nombre = app["nombreApp"] as String
            val peso = app["pesoGigasApp"] as Double
            val version = app["version"] as Int
            val url = app["urlApp"] as String
            val fecha = app["fechaLanzamientoApp"] as String
            val foto = app["foto"] as String
            val costo = app["costo"] as Double
            val idSistema = app["id"] as Int
            val estado = app["estado"] as Int
            val aplicacion = App(id, nombre, peso, version, url, fecha, costo, estado, idSistema, foto, 0, 0)

            return aplicacion
        }

    }
}

