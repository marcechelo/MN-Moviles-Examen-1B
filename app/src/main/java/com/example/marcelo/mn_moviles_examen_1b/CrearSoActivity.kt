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

/*                    val jsonPayload = """
                    {"nombreSo": ${nombre}, "versionApi": ${version},"fechaLanzamiento": ${fecha}, "pesoGigasSo": ${peso}, "instalado": ${instaldo} }
                    """
                    Fuel.post("http://localhost:1337/SistemaOperativo").body(jsonPayload, Charsets.UTF_8).
                            header("Content-Type" to "application/json").
                            response { request, response, result ->
                                when (result) {
                                    is com.github.kittinunf.result.Result.Failure -> {
                                        val ex = result.getException()
                                    }
                                    is com.github.kittinunf.result.Result.Success -> {
                                        Log.i("mensaje", "Correcto")
                                    }
                                }
                                "{\"nombreSo\" : $nombre, \"versionApi\" : $version, \"fechaLanzamiento\" :$fecha, \"pesoGigasSo\" :$peso, \"instalado\": $instaldo}"
                            }*/

                    Fuel.post("http://localhost:1337/SistemaOperativo").body("{\"nombreSo\":\"$nombre\", \"versionApi\" : $version, \"fechaLanzamiento\" :\"$fecha\", \"pesoGigasSo\" :$peso, \"instalado\": $instaldo}").response{request, response, result ->
                        when(result){
                            is com.github.kittinunf.result.Result.Failure -> {
                                val ex = result.getException()
                            }
                            is com.github.kittinunf.result.Result.Success ->{
                                Log.i("SO","correcto");
                            }
                        }
                    }

                    //dbHandler.insertarSo(so)
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
