package com.example.marcelo.mn_moviles_examen_1b

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home_principal_comprador.*
import java.io.FileInputStream
import java.nio.charset.Charset

class HomePrincipalComprador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_principal_comprador)
        leerUsuario()

         var usuario: Usuario = intent.getParcelableExtra("usuario")

        text_saludo_comprador.text = "Bienvenido ${usuario.username}"

        boton_buscar.setOnClickListener { view: View ->
            irAActividadBuscar()
        }

        boton_ordenes.setOnClickListener { view: View ->
            irAActividadOrdenes()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu):Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar, menu)
        return true
    }

    fun irAActividadBuscar(){
        val intent = Intent(this,BuscarComprador::class.java)
        intent.putExtra("tipo", "Create")
        startActivity(intent)
    }

    fun irAActividadOrdenes(){
        val intent = Intent(this,OrdenesComprador::class.java)
        intent.putExtra("tipo", "Create")
        startActivity(intent)
    }

    fun leerUsuario(){

        val FILENAME ="usuario"
        var usuario = ""
        this.openFileInput(FILENAME).use {
            val usuarioAux = it.readBytes()
            usuario = usuarioAux.toString(Charset.defaultCharset())
        }

        val FILENAMEUNO ="contraseña"
        var contraseña = ""
        this.openFileInput(FILENAMEUNO).use {
            val contraseñaAux = it.readBytes()
            contraseña = contraseñaAux.toString(Charset.defaultCharset())
        }

        val toast = Toast.makeText(this,"suaurio: $usuario, contraseña: $contraseña",Toast.LENGTH_SHORT)
        toast.show()


    }
}
