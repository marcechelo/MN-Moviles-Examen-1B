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
import android.widget.*
import kotlinx.android.synthetic.main.activity_crear_app.*
import kotlinx.android.synthetic.main.activity_detalle_so.*
import kotlinx.android.synthetic.main.activity_detalle_so.view.*
import kotlinx.android.synthetic.main.activity_listar_so.*
import java.util.*
import kotlin.collections.ArrayList

class DetalleSOActivity : AppCompatActivity() {

    //var aplicaciones = ArrayList<Aplicacion>()
    lateinit var aplicaciones: java.util.ArrayList<App>
    lateinit var dbHandler: DbHandlerApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_so)

        dbHandler = DbHandlerApp(this)
        aplicaciones = dbHandler.leerApp()

        val sistemaIntent: SO = intent.getParcelableExtra("sistema")
        //val sistemaGuardado: SO? = savedInstanceState?.get("sistemaIntent") as SO?
        val id = sistemaIntent.id
        txtv_nombre_so.text = sistemaIntent.nombre
        txtv_version_so.text = sistemaIntent.versionApi.toString()
        txtv_fecha_so.text = sistemaIntent.fechaLanzamiento
        txtv_peso_so.text = sistemaIntent.pesoEnGigas.toString()
        if (sistemaIntent.instalado === 1) {
            txtv_instaldo_so.text = "trues"
        } else txtv_instaldo_so.text = "false"

        val toast = Toast.makeText(this, id.toString(), Toast.LENGTH_SHORT)
        toast.show()

        val layoutManager = LinearLayoutManager(this)
        //aplicaciones = CrearAplicacion.aplicacion
        val adaptador1 = AplicacionAdaptador(aplicaciones)
        recycler_view_app.layoutManager = layoutManager
        recycler_view_app.itemAnimator = DefaultItemAnimator()
        recycler_view_app.adapter = adaptador1
        adaptador1.notifyDataSetChanged()
        registerForContextMenu(recycler_view)

        boton_so_crear.setOnClickListener { view: View ->
            val intent = Intent(this, CrearAppActivity::class.java)
            intent.putExtra("so", sistemaIntent)
            startActivity(intent)
            //irAAtividadCrearApp()}
        }

        fun irAAtividadCrearApp() {
            var intent = Intent(this, CrearAppActivity::class.java)
            //intent.putExtra("sistemaid",this.sistemaIntent)
            startActivity(this, intent, null)
        }
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

class AplicacionAdaptador(private val listaAplicaciones: List<App>): RecyclerView.Adapter<AplicacionAdaptador.MyViewHolder>(),
        PopupMenu.OnMenuItemClickListener{

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.item_menu_editar -> {
                Log.i("menu", "Editar")
                return true
            }
            R.id.item_menu_eliminar -> {
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
        holder.nombre.text = aplicacion.nombre
        holder.costo.text = aplicacion.costo.toString()
        holder.urlDescarga.text = aplicacion.urlDescarga
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


