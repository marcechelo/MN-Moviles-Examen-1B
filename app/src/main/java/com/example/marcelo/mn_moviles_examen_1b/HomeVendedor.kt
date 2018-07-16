package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home_vendedor.*
import java.nio.charset.Charset
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle


class HomeVendedor : AppCompatActivity() {

    private val mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_vendedor)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        leerUsuario()


        boton_listar.setOnClickListener { view: View -> irAActividadListarSo() }
        boton_crear.setOnClickListener { view: View -> irAActividadCrearSo() }
        boton_foto.setOnClickListener { view: View -> irAActividadCamara() }

    }

    fun irAActividadListarSo(){
        val intent = Intent(this,ListarSOActivity::class.java)
        startActivity(intent)
    }

    fun irAActividadCamara(){
        //val intent = Intent(this,Camara2::class.java)
        //startActivity(intent)
        val toast = Toast.makeText(this,BaseDeDatosDetalle.getDetalle(1)[0].id.toString(),Toast.LENGTH_LONG)
        toast.show()
    }

    fun irAActividadCrearSo(){
        val intent = Intent(this,CrearSoActivity::class.java)
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

        val toast = Toast.makeText(this,"suaurio: $usuario, contraseña: $contraseña", Toast.LENGTH_SHORT)
        toast.show()


    }
}
