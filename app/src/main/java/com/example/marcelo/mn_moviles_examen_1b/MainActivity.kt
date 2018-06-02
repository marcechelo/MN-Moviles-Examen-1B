package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    //lateinit var dbHandler: DbHandlerAplicacion
    companion object BaseDeDatos{
        lateinit var dbHandler: DbHandlerAplicacion

        fun crearSo(nombre: String,
                 versionApi: Int,
                 fechaLanzamiento: String,
                 pesoEnGigas:Double,
                 instaldo:Boolean)= dbHandler.insertarSo(nombre,versionApi,fechaLanzamiento,pesoEnGigas,instaldo)

        fun crearApp(pesoEnGigas: Double,
                     version: Int,
                     nombre: String,
                     urlDescarga: String,
                     fechaLanzamiento: Date,
                     costo: Double,
                     sistemaOperativoId: Int) = dbHandler.insertrApp(pesoEnGigas,version,nombre,urlDescarga,fechaLanzamiento,costo,sistemaOperativoId)

        fun leer()= dbHandler.leerDatosSo()

    }

    //lateinit var dbHandler: DbHandlerAplicacion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DbHandlerAplicacion(this)


        boton_listar.setOnClickListener { view: View -> irAActividadListarSo() }
        boton_crear.setOnClickListener { view: View -> irAActividadCrearSo() }

    }

    fun irAActividadListarSo(){
        val intent = Intent(this,ListarSOActivity::class.java)
        startActivity(intent)
    }

    fun irAActividadCrearSo(){
        val intent = Intent(this,CrearSoActivity::class.java)
        startActivity(intent)
    }
}
