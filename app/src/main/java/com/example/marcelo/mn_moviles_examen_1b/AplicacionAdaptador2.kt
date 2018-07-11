package com.example.marcelo.mn_moviles_examen_1b

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class AplicacionAdaptador2(private val listaAplicaciones: List<App>) : RecyclerView.Adapter<AplicacionAdaptador2.MyViewHolder>() {

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
        var imagen: ImageView

        init {
            nombre = view.findViewById(R.id.txtv_nombre) as TextView
            costo = view.findViewById(R.id.txtv_detalle1) as TextView
            urlDescarga = view.findViewById(R.id.txtv_detalle2) as TextView
            botonDetalle = view.findViewById(R.id.boton_detalle) as Button
            layout = view.findViewById(R.id.relative_layout) as RelativeLayout
            imagen = view.findViewById(R.id.imagen_view) as ImageView
            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

            menu?.add(Menu.NONE, R.id.item_menu_editar, Menu.NONE, "Seleccionar")
            //menu?.add(Menu.NONE, R.id.item_menu_borrar, Menu.NONE, "Quitar")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista_aplicaciones, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aplicacion = listaAplicaciones[position]
        holder.nombre.text = aplicacion.nombre
        holder.costo.text = aplicacion.costo.toString()

        when(aplicacion.estado){
            1 ->{
                holder.urlDescarga.text = "Disponible"
            }
            2 ->{
                holder.urlDescarga.text = "Seleccionado"
            }
            3 ->{
                holder.urlDescarga.text = "No Disponible"
            }
            else ->{
                Log.i("Error","Error")}
        }

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