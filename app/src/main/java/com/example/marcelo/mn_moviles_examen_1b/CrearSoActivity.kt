package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_crear_so.*
import java.util.*

class CrearSoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_so)

        //val dbHandler = DbHandlerAplicacion(this)

        MainActivity.BaseDeDatos.crearSo("algo1",1, "09/04/2010",1.1,true)
        MainActivity.BaseDeDatos.leer()

        boton_guardar.setOnClickListener{view: View ->
            val nombre = edit_nombre.text.toString()
            val version = edit_version_api.text.toString()

            /*val fecha = edit_fecha.text.toString()
            val api = edit_version_api.text.toString().toDouble()
            val instaldo:Boolean
            if (cb_instalado.isChecked()){instaldo = true} else instaldo = false*/
            //MainActivity.BaseDeDatos.crearSo(nombre,version1,fecha,api,instaldo)
            //MainActivity.BaseDeDatos.crearSo("algo",1, "09/04/2010",1.1,true)
            //MainActivity.BaseDeDatos.leer()
            MainActivity.BaseDeDatos.crearSo("algo1",1, "09/04/2010",1.1,true)

            irAActividadListarOs()
        }
    }

    fun irAActividadListarOs(){
        val intent = Intent(this,ListarSOActivity::class.java)
        startActivity(intent)
    }
}
