package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_crear_app.*

class CrearAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_app)

        boton_guardar_app.setOnClickListener{view: View -> irAActividadDetalleSo()}
    }

    fun irAActividadDetalleSo(){
        var intent = Intent(this,DetalleSOActivity::class.java)
        startActivity(intent)
    }
}
