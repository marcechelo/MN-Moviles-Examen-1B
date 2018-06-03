package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_crear_app.*
import kotlinx.android.synthetic.main.activity_detalle_so.*
import java.util.*
import kotlin.collections.ArrayList

class DetalleSOActivity : AppCompatActivity() {

    var aplicaciones = ArrayList<Aplicacion>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_so)

        val layoutManager = LinearLayoutManager(this)
        aplicaciones = CrearAplicacion.aplicacion
        val adaptador1 = AplicacionAdaptador(aplicaciones)
        recycler_view_app.layoutManager = layoutManager
        recycler_view_app.itemAnimator = DefaultItemAnimator()
        recycler_view_app.adapter = adaptador1
        adaptador1.notifyDataSetChanged()

        boton_so_crear.setOnClickListener{view: View -> irAAtividadCrearApp()}
    }

    fun irAAtividadCrearApp(){
        var intent = Intent(this,CrearAppActivity::class.java)
        startActivity(intent)
    }
}

class Aplicacion(var pesoEnGigas: String,
                 var version:String,
                 var nombre:String,
                 var urlDescarga:String,
                 var fechaLanzamiento:Date,
                 var costo:String,
                 var sistemaOperativoId:String){}

class CrearAplicacion(){

    companion object {

        var aplicacion: ArrayList<Aplicacion> = ArrayList()

        init {
            aplicacion.add(Aplicacion("1.1","1","algo1","usrl1", Date(),"1.1","1"))
            aplicacion.add(Aplicacion("1.2","2","algo2","usrl2",Date(),"2.1","2"))
        }
    }
}

class AplicacionAdaptador(private val listaAplicaciones: List<Aplicacion>): RecyclerView.Adapter<AplicacionAdaptador.MyViewHolder>(),
        PopupMenu.OnMenuItemClickListener{

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.item_menu_aceptar -> {
                Log.i("menu", "Editar")
                return true
            }
            R.id.item_menu_cancelar -> {
                Log.i("menu", "Eliminar")
                return true
            }
            R.id.item_menu_correo -> {
                Log.i("menu", "Correo")
                return true
            }
            else -> {
                Log.i("menu", "Todos los demas")
                return false
            }
        }
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        var nombre: TextView
        var costo: TextView
        var urlDescarga: TextView
        lateinit var aplicacion: Aplicacion
        var botonDetalle: Button
        var layout: RelativeLayout

        init {
            nombre = view.findViewById(R.id.txtv_nombre) as TextView
            costo = view.findViewById(R.id.txtv_detalle1) as TextView
            urlDescarga = view.findViewById(R.id.txtv_detalle2) as TextView
            botonDetalle =view.findViewById(R.id.boton_detalle) as Button
            layout = view.findViewById(R.id.relative_layout) as RelativeLayout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aplicacion = listaAplicaciones[position]
        holder.nombre.setText(aplicacion.nombre)
        holder.costo.setText(aplicacion.costo)
        holder.urlDescarga.setText(aplicacion.urlDescarga)
        holder.botonDetalle.setBackgroundColor(Color.GRAY)
        holder.botonDetalle.setOnClickListener{view:View ->
            var intent = Intent(view.context,DetalleAppActivity::class.java)
            startActivity(view.context, intent, null)

        }

        holder.layout.setOnClickListener{view ->
            val popup = PopupMenu(view.context,view)
            popup.setOnMenuItemClickListener(this)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.pop_up_menu, popup.menu)
            popup.show()
        }
    }

    override fun getItemCount(): Int {
        return listaAplicaciones.size
    }
}


