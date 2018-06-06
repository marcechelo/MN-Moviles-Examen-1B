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
    lateinit var dbHandler: DbHandlerApp

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

        dbHandler = DbHandlerApp(this)
        idSo = intent.getIntExtra("idso",0)
        aplicaciones = dbHandler.leerApp(idSo)

        val layoutManager = LinearLayoutManager(this)
        adaptador = AplicacionAdaptador(aplicaciones)
        recycler_view_app.layoutManager = layoutManager
        recycler_view_app.itemAnimator = DefaultItemAnimator()
        recycler_view_app.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(recycler_view)

        boton_so_crear.setOnClickListener { view: View ->
            irAAtividadCrearApp()
        }
    }

    fun irAAtividadCrearApp() {
        val intent = Intent(this, CrearAppActivity::class.java)
        intent.putExtra("tipo", "Create")
        intent.putExtra("sistemaId", SisteOp?.id)
        intent.putExtra("sistema",SisteOp)
        startActivity(this, intent, null)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var posicion = adaptador.getPosition()
        var aplicacion = aplicaciones[posicion]

        when(item.itemId){
            R.id.item_menu_edit -> {
                val intent = Intent(this,CrearAppActivity::class.java)
                intent.putExtra("tipo","Edit")
                intent.putExtra("app",aplicacion)
                intent.putExtra("sistema",SisteOp)
                startActivity(intent)
                return true
            }
            R.id.item_menu_borrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Desea eliminar la Aplicacion").setPositiveButton("Si",{
                    dialog, which -> dbHandler.borrarApp(aplicacion.appid)
                    finish()
                    startActivity(intent)
                }).setNegativeButton("No",null)
                val dialogo = builder.create()
                dialogo.show()
                return true
            }
            else -> {
                Log.i("menu", "Todos los demas")
                return super.onOptionsItemSelected(item)
            }
        }
    }


}


class AplicacionAdaptador(private val listaAplicaciones: List<App>) : RecyclerView.Adapter<AplicacionAdaptador.MyViewHolder>() {

    private var position: Int = 0

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

        var nombre: TextView
        var costo: TextView
        var urlDescarga: TextView
        lateinit var aplicacion: App
        var botonDetalle: Button
        var layout: RelativeLayout

        init {
            nombre = view.findViewById(R.id.txtv_nombre) as TextView
            costo = view.findViewById(R.id.txtv_detalle1) as TextView
            urlDescarga = view.findViewById(R.id.txtv_detalle2) as TextView
            botonDetalle = view.findViewById(R.id.boton_detalle) as Button
            layout = view.findViewById(R.id.relative_layout) as RelativeLayout
            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(Menu.NONE, R.id.item_menu_editar, Menu.NONE, "Editar")
            menu?.add(Menu.NONE, R.id.item_menu_borrar, Menu.NONE, "Borrar")
            menu?.add(Menu.NONE, R.id.item_menu_compartir, Menu.NONE, "Compartir")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aplicacion = listaAplicaciones[position]
        holder.nombre.text = aplicacion.nombre
        holder.costo.text = aplicacion.costo.toString()
        holder.urlDescarga.text = aplicacion.urlDescarga
        holder.botonDetalle.setBackgroundColor(Color.GRAY)
        holder.aplicacion = aplicacion
        holder.botonDetalle.setOnClickListener { view: View ->
            var intent = Intent(view.context, DetalleAppActivity::class.java)
            intent.putExtra("app",aplicacion)
            startActivity(view.context, intent, null)

        }

        holder.itemView.setOnLongClickListener{
            setPosition(holder.adapterPosition)
            false
        }
        /*holder.layout.setOnClickListener { view ->
            val popup = PopupMenu(view.context, view)
            popup.setOnMenuItemClickListener(this)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.pop_up_menu, popup.menu)
            popup.show()
        }*/
    }

    override fun getItemCount(): Int {
        return listaAplicaciones.size
    }
}


