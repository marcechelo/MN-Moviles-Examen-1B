package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_listar_so.*
import java.util.*

class ListarSOActivity : AppCompatActivity() {

    lateinit var sistemOp: ArrayList<SO>
    lateinit var dbHandler: DbHandlerSo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_so)

        dbHandler = DbHandlerSo(this)
        sistemOp = dbHandler.leerSo()
        val layoutManager = LinearLayoutManager(this)
        //sistemOp = CrearSistemOperativo.sistemaOp
        val adaptador = SistemaOperativoAdaptador(sistemOp)
        recycler_view.layoutManager = layoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recycler_view)

    }
}


class SistemaOperativo(var nombre:String,
                       var versionApi:Int,
                       var fechaLanzamaineto: String,
                       var pesoEnGigas:Double,
                       var instalado:Boolean){}

/*class CrearSistemOperativo(){
    companion object {

        var sistemaOp: ArrayList<SO> = ArrayList()

        init {
            sistemaOp.add(SistemaOperativo("algo1",1, "",1.1,true))
            sistemaOp.add(SistemaOperativo("algo2",2, "",1.2,false))
        }
    }
}*/

class SistemaOperativoAdaptador(private val listaSistema: List<SO>): RecyclerView.Adapter<SistemaOperativoAdaptador.MyViewHolder>(),
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

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var nombre: TextView
        var versionApi: TextView
        var pesoEnGigas: TextView
        lateinit var sistema: SO
        var botonDetalle: Button
        var layout: RelativeLayout

        init {
            nombre = view.findViewById(R.id.txtv_nombre) as TextView
            versionApi = view.findViewById(R.id.txtv_detalle1) as TextView
            pesoEnGigas = view.findViewById(R.id.txtv_detalle2) as TextView
            botonDetalle = view.findViewById(R.id.boton_detalle) as Button
            layout = view.findViewById(R.id.relative_layout) as RelativeLayout

            /*layout.setOnClickListener({v ->
                val nombreActual = v.findViewById(R.id.txtv_nombre) as TextView
                val toast = Toast.makeText(v.context, "algo", Toast.LENGTH_LONG)
                toast.show()
            })*/
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

        val sistemaIntent = SO(sistema.id,sistema.nombre, sistema.versionApi, sistema.fechaLanzamiento, sistema.pesoEnGigas,sistema.instalado)

        holder.botonDetalle.setOnClickListener{v ->
            val intent = Intent(v.context,DetalleSOActivity::class.java)
            intent.putExtra("sistema",sistemaIntent)
            startActivity(v.context, intent, null)
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
        return listaSistema.size
    }

}


