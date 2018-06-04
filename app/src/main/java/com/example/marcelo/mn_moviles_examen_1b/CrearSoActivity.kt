package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_crear_app.*
import kotlinx.android.synthetic.main.activity_crear_so.*
import java.util.*

class CrearSoActivity : AppCompatActivity() {

    lateinit var dbHandler: DbHandlerSo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_so)

        dbHandler = DbHandlerSo(this)

        boton_guardar.setOnClickListener{view: View ->

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

                dbHandler.insertarSo(so)
                irAActividadListarOs()
            }
        }
    }

    fun irAActividadListarOs(){
        val intent = Intent(this,ListarSOActivity::class.java)
        startActivity(intent)
    }
}
