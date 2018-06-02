package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boton_listar.setOnClickListener{view: View -> irAActividadListarSo()}
    }

    fun irAActividadListarSo(){
        val intent = Intent(this,ListarSOActivity::class.java)
        startActivity(intent)
    }
}
