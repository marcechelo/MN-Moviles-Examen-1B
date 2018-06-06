package com.example.marcelo.mn_moviles_examen_1b

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detalle_app.*

class DetalleAppActivity : AppCompatActivity() {


    var aplicacion: App ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_app)


        aplicacion = intent.getParcelableExtra("app")
        edit_nombre_app.text = aplicacion?.nombre
        edit_peso_app.text = aplicacion?.pesoEnGigas.toString()
        edit_version.text = aplicacion?.version.toString()
        edit_url.text = aplicacion?.urlDescarga
        edit_fecha_app.text = aplicacion?.fechaLanzamiento
        edit_costo.text = aplicacion?.costo.toString()
        edit_so_id.text = aplicacion?.sistemaOperativoId.toString()
    }
}
