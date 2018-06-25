package com.example.marcelo.mn_moviles_examen_1b

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import org.json.JSONObject

class ServidorWeb(){

    fun postSo(nombre:String,version: Int, fecha:String, peso:Double, instalado:Int){

        val json = JSONObject()
        json.put("nombreSo", nombre)
        json.put("versionApi", version)
        json.put("fechaLanzamiento", fecha)
        json.put("pesoGigasSo", peso)
        json.put("instalado", instalado)

        val httpRequest = Fuel.post("http://192.168.1.3:1337/SistemaOperativo").body(json.toString())

        httpRequest.headers["Content-Type"] = "application/json"

        httpRequest.response { request, response, result ->
            Log.i("mensaje",request.toString())
            Log.i("mensaje",response.toString())
            Log.i("mensaje",result.toString())
        }
    }
}