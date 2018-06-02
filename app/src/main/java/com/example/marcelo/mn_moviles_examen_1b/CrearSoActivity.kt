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

        MainActivity.BaseDeDatos.crearSo("algo1",1, "09/04/2010",1.1,true)
        MainActivity.BaseDeDatos.leer()

        boton_guardar.setOnClickListener{view: View ->
            irAActividadListarOs()
        }
    }

    fun irAActividadListarOs(){
        val intent = Intent(this,ListarSOActivity::class.java)
        startActivity(intent)
    }
}
