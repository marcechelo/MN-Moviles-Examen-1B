package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView

class OperadorAdaptador(private val listaSistema: List<SO>): RecyclerView.Adapter<OperadorAdaptador.MyViewHolder>(){

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

        holder.botonDetalle.setOnClickListener{v: View ->
            val intent = Intent(v.context,DetalleSOActivity::class.java)
            intent.putExtra("sistema",sistema)
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