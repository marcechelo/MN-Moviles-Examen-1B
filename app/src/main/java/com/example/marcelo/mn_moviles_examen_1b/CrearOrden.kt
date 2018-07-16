package com.example.marcelo.mn_moviles_examen_1b

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_crear_orden.*
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList

class CrearOrden : AppCompatActivity() {

    lateinit var aplicaciones: ArrayList<App>
    lateinit var aplicacionesAux: ArrayList<App>
    var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_orden)

        text_fecha.text = Calendar.getInstance().time.toString()
        aplicacionesAux = ArrayList()
        //aplicaciones = intent.getParcelableArrayListExtra("aplicaciones")
        aplicaciones = BaseDeDatosApp.getTodasAplicaciones()
        aplicaciones.forEach {
            if (it.estado == 2){
                total += it.costo
                aplicacionesAux.add(it)
            }
        }

        text_total.text = total.toString()

        boton_guardar.setOnClickListener{view: View ->
            crearOrden()
        }



    }

    fun crearOrden(){

        val usuario = BaseDeDatosUsuario.getUsuario(leerUsuario())[0]

        val orden = Ordenes(0,Calendar.getInstance().time.toString(),this.total,1, edit_lugar.text.toString(), 0.0, "hola", usuario.id,1, 0,0 )
        val identificador = BaseDeDatosOrdenes.postOrden(orden)

        BaseDeDatosDetalle.postDetalle(DetalleOrden(0,this.total,identificador,0 ,0))
        val idDetalle = BaseDeDatosDetalle.getDetalle(identificador)

        aplicacionesAux.forEach {
            BaseDeDatosApp.putAplicacionDetalle(it,identificador)
        }

    }

    fun leerUsuario():String{

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

        return usuario

    }
}
