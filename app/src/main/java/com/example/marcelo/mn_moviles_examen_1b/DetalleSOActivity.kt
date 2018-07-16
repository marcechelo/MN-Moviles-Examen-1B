package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.activity_detalle_so.*
import kotlinx.android.synthetic.main.activity_listar_so.*
import kotlin.collections.ArrayList

class DetalleSOActivity : AppCompatActivity() {

    //var aplicaciones = ArrayList<Aplicacion>()
    var SisteOp: SO? = null
    lateinit var adaptador: AplicacionAdaptador
    lateinit var aplicaciones: ArrayList<App>

    var idSo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_so)

        SisteOp = intent.getParcelableExtra("sistema")

        txtv_nombre_so.text = SisteOp?.nombre
        txtv_version_so.text = SisteOp?.versionApi.toString()
        txtv_fecha_so.text = SisteOp?.fechaLanzamiento
        txtv_peso_so.text = SisteOp?.pesoEnGigas.toString()
        txtv_instaldo_so.text = if (SisteOp?.instalado == 1) "Si" else "No"

        idSo = intent.getIntExtra("idso",0)
        aplicaciones = BaseDeDatosApp.getAplicaciones(idSo)
        //aplicaciones = dbHandler.leerApp(idSo)

        val layoutManager = LinearLayoutManager(this)
        adaptador = AplicacionAdaptador(aplicaciones)
        adaptador.notifyDataSetChanged()
        recycler_view_app.layoutManager = layoutManager
        recycler_view_app.itemAnimator = DefaultItemAnimator()
        recycler_view_app.adapter = adaptador
        recycler_view_app.invalidate()
        registerForContextMenu(recycler_view_app)

        boton_so_crear.setOnClickListener { view: View ->
            irAAtividadCrearApp()
            finish()
        }

        boton_mapa.setOnClickListener { view: View ->
            irAAtividadLogin()
            finish()
        }

    }

    fun irAAtividadLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(this, intent, null)
    }

    fun irAAtividadCrearApp() {
        val intent = Intent(this, CrearAppActivity::class.java)
        intent.putExtra("tipo", "Create")
        intent.putExtra("sistemaId", SisteOp?.id)
        intent.putExtra("sistema",SisteOp)
        startActivity(this, intent, null)
    }

    /*fun irAAtividadMapa() {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("sistemaId", SisteOp?.id)
        intent.putExtra("sistema",SisteOp)
        startActivity(this, intent, null)
    }*/

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var posicion = adaptador.getPosition()
        var aplicacion = aplicaciones[posicion]

        when(item.itemId){
            R.id.item_menu_editar-> {
                val intent = Intent(this, CrearAppActivity::class.java)
                intent.putExtra("tipo","Edit")
                intent.putExtra("app",aplicacion)
                intent.putExtra("sistema",SisteOp)
                startActivity(intent)
                finish()
                return true
                Log.i("error","intent ${intent}")
            }
            R.id.item_menu_borrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Desea eliminar la Aplicacion").setPositiveButton("Si",{
                    dialog, which -> BaseDeDatosApp.deleteAplicacion(aplicacion.appid)
                    finish()
                    startActivity(intent)
                }).setNegativeButton("No",null)
                val dialogo = builder.create()
                dialogo.show()
                return true
            }
            R.id.item_menu_compartir -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_SUBJECT, "Examen Moviles - SO y Aplicaciones")
                intent.putExtra(Intent.EXTRA_TEXT,"Datos del Sistema Operativo - ${aplicacion}" )
                startActivity(intent)
                return true
            }
            else -> {
                Log.i("menu", "Todos los demas")
                return super.onOptionsItemSelected(item)
            }
        }
    }


}




