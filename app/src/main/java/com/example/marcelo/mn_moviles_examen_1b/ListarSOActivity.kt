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
import kotlinx.android.synthetic.main.activity_listar_so.*
import java.util.*

class ListarSOActivity : AppCompatActivity() {

    lateinit var sistemOp: ArrayList<SO>
    lateinit var dbHandler: DbHandlerSo
    lateinit var adaptador: SistemaOperativoAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_so)

        dbHandler = DbHandlerSo(this)
        sistemOp = dbHandler.leerSo()
        val layoutManager = LinearLayoutManager(this)
        //sistemOp = CrearSistemOperativo.sistemaOp
        adaptador = SistemaOperativoAdaptador(sistemOp)
        recycler_view.layoutManager = layoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(recycler_view)

    }

    override fun onContextItemSelected(item: MenuItem):Boolean {

        var posicion = adaptador.getPosition()
        var sistema = sistemOp[posicion]

        when (item.itemId){
            R.id.item_menu_editar -> {
                val intent = Intent(this, CrearSoActivity::class.java)
                intent.putExtra("tipo","Edit")
                intent.putExtra("so",sistema)
                startActivity(intent)
                return true
            }
            R.id.item_menu_borrar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Desea eliminar el SO").setPositiveButton("Si",{
                    dialog, which -> dbHandler.borrarSo(sistema.id)
                    finish()
                    startActivity(intent)
                }).setNegativeButton("No",null)
                val dialogo = builder.create()
                dialogo.show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}

class SistemaOperativoAdaptador(private val listaSistema: List<SO>): RecyclerView.Adapter<SistemaOperativoAdaptador.MyViewHolder>(){

    private var position: Int = 0

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

        var nombre: TextView
        var versionApi: TextView
        var pesoEnGigas: TextView
        lateinit var sistema: SO
        lateinit var app:App
        var botonDetalle: Button
        var layout: RelativeLayout

        init {
            nombre = view.findViewById(R.id.txtv_nombre) as TextView
            versionApi = view.findViewById(R.id.txtv_detalle1) as TextView
            pesoEnGigas = view.findViewById(R.id.txtv_detalle2) as TextView
            botonDetalle = view.findViewById(R.id.boton_detalle) as Button
            layout = view.findViewById(R.id.relative_layout) as RelativeLayout
            view.setOnCreateContextMenuListener(this)

            /*layout.setOnClickListener({v ->
                val nombreActual = v.findViewById(R.id.txtv_nombre) as TextView
                val toast = Toast.makeText(v.context, "algo", Toast.LENGTH_LONG)
                toast.show()
            })*/
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(Menu.NONE, R.id.item_menu_editar, Menu.NONE, "Editar")
            menu?.add(Menu.NONE, R.id.item_menu_borrar, Menu.NONE, "Borrar")
            menu?.add(Menu.NONE, R.id.item_menu_compartir, Menu.NONE, "Compartir")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sistema = listaSistema[position]
        holder.nombre.text = sistema.nombre
        holder.versionApi.text = "Version Api " + sistema.versionApi.toString()
        holder.pesoEnGigas.text = "Peso " + sistema.pesoEnGigas.toString() + " Gigas"
        holder.botonDetalle.setBackgroundColor(Color.GRAY)
        holder.sistema = sistema

        val sistemaIntent = SO(sistema.id,sistema.nombre, sistema.versionApi, sistema.fechaLanzamiento, sistema.pesoEnGigas,sistema.instalado)

        holder.botonDetalle.setOnClickListener{v: View ->
            val intent = Intent(v.context,DetalleSOActivity::class.java)
            intent.putExtra("sistema",sistemaIntent)
            intent.putExtra("idso",sistema.id)
            v.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener{
            setPosition(holder.adapterPosition)
            false}



        /*holder.layout.setOnClickListener{view ->
            val popup = PopupMenu(view.context,view)
            popup.setOnMenuItemClickListener(this)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.pop_up_menu, popup.menu)
            popup.show()
        }*/


    }

    override fun getItemCount(): Int {
        return listaSistema.size
    }

}


