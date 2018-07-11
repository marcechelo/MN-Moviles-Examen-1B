package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home_vendedor.*

class HomeVendedor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_vendedor)

        boton_listar.setOnClickListener { view: View -> irAActividadListarSo() }
        boton_crear.setOnClickListener { view: View -> irAActividadCrearSo() }
        boton_foto.setOnClickListener { view: View -> irAActividadCamara() }

    }

    fun irAActividadListarSo(){
        val intent = Intent(this,ListarSOActivity::class.java)
        startActivity(intent)
    }

    fun irAActividadCamara(){
        val intent = Intent(this,TomarFoto::class.java)
        startActivity(intent)
    }

    fun irAActividadCrearSo(){
        val intent = Intent(this,CrearSoActivity::class.java)
        intent.putExtra("tipo", "Create")
        startActivity(intent)
    }
}
