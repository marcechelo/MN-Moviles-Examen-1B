package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_crear_so.*
import java.util.*

class CrearSoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_so)

        //val dbHandler = DbHandlerAplicacion(this)

        //MainActivity.BaseDeDatos.crearSo("algo1",1, "09/04/2010",1.1,true)
        //MainActivity.BaseDeDatos.leer()

        boton_guardar.setOnClickListener{view: View ->

            if(edit_nombre.text.toString().isEmpty() ||
                    edit_version_api.text.toString().isEmpty() ||
                    edit_fecha.text.toString().isEmpty() ||
                    edit_version_api.text.toString().isEmpty()){
                val toast = Toast.makeText(this,"Todos los campos deben estra lleno",Toast.LENGTH_SHORT)
                toast.show()
            }else{
                val nombre = edit_nombre.text.toString()
                val version = edit_version_api.text.toString().toDouble()
                val fecha = edit_fecha.text.toString()
                val api = edit_version_api.text.toString().toDouble()
                val instaldo:Boolean = cb_instalado.isChecked()
                irAActividadListarOs()
            }
        }
    }

    fun irAActividadListarOs(){
        val intent = Intent(this,ListarSOActivity::class.java)
        startActivity(intent)
    }
}
