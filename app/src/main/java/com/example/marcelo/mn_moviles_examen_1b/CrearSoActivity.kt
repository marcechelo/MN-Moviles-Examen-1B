package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.beust.klaxon.JsonObject
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.response
import kotlinx.android.synthetic.main.activity_crear_app.*
import kotlinx.android.synthetic.main.activity_crear_so.*
import org.json.JSONObject
import java.util.*
import javax.xml.transform.Result

class CrearSoActivity : AppCompatActivity() {

    lateinit var dbHandler: DbHandlerSo
    var sistemaOp: SO? = null
    var tipo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_so)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")){
            txt_descripcion.text = "Editar SO"
            sistemaOp = intent.getParcelableExtra("so")
            llenar()
            tipo = true
        }

        dbHandler = DbHandlerSo(this)

        boton_guardar.setOnClickListener{view: View? ->

            if (!tipo){

                if(edit_nombre.text.toString().isEmpty() ||
                        edit_version_api.text.toString().isEmpty() ||
                        edit_fecha.text.toString().isEmpty() ||
                        edit_peso.text.toString().isEmpty())
                {
                    val toast = Toast.makeText(this,"Todos los campos deben estra lleno",Toast.LENGTH_SHORT)
                    toast.show()
                }else{

                    var nombre = edit_nombre.text.toString()
                    var version = edit_version_api.text.toString().toInt()
                    var fecha = edit_fecha.text.toString()
                    var peso = edit_peso.text.toString().toDouble()
                    var instaldo = if (cb_instalado.isChecked) 1 else 0
                    var so = SO(0, nombre, version, fecha, peso, instaldo)
                    //dbHandler.insertarSo(so)
                    postSo(nombre,version,fecha,peso,instaldo)

                    irAActividadListarOs()
                }

            }else{

                if(edit_nombre.text.toString().isEmpty() ||
                        edit_version_api.text.toString().isEmpty() ||
                        edit_fecha.text.toString().isEmpty() ||
                        edit_peso.text.toString().isEmpty())
                {
                    val toast = Toast.makeText(this,"Todos los campos deben estra lleno",Toast.LENGTH_SHORT)
                    toast.show()
                }else{
                    var nombre = edit_nombre.text.toString()
                    var version = edit_version_api.text.toString().toInt()
                    var fecha = edit_fecha.text.toString()
                    var peso = edit_peso.text.toString().toDouble()
                    var instaldo = if (cb_instalado.isChecked) 1 else 0
                    var so = SO(sistemaOp?.id!!, nombre, version, fecha, peso, instaldo)

                    dbHandler.updateSo(so)
                    irAActividadListarOs()
                }

            }
        }
    }

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

    fun llenar(){
        edit_nombre.setText(sistemaOp?.nombre)
        edit_version_api.setText(sistemaOp?.versionApi.toString())
        edit_fecha.setText(sistemaOp?.fechaLanzamiento)
        edit_peso.setText(sistemaOp?.pesoEnGigas.toString())
        if(sistemaOp?.instalado == 1){
            cb_instalado.toggle()
        }
    }

    fun irAActividadListarOs(){
        val intent = Intent(this,ListarSOActivity::class.java)
        startActivity(intent)
    }
}
