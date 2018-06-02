package com.example.marcelo.mn_moviles_examen_1b

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_listar_so.*
import java.util.*

class ListarSOActivity : AppCompatActivity() {

    var sistemOp = ArrayList<SistemaOperativo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_so)
        val layoutManager = LinearLayoutManager(this)
        sistemOp = CrearSistemOperativo.sistemaOp
        val adaptador = SistemaOperativoAdaptador(sistemOp)
        recycler_view.layoutManager = layoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adaptador
        adaptador.notifyDataSetChanged()

    }
}

class SistemaOperativoParcelable (var nombre:String,
                                  var versionApi:Int,
                                  var fechaLanzamaineto: Date?,
                                  var pesoEnGigas:Double,
                                  var instalado:Boolean): Parcelable {

    constructor(parcel: Parcel):this(
            parcel.readString(),
            parcel.readInt(),
            parcel.leerDate(),
            parcel.readDouble(),
            parcel.readByte() != 0.toByte()){

    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(nombre)
        dest?.writeInt(versionApi)
        dest?.escribirDate(fechaLanzamaineto)
        dest?.writeByte((if(instalado)1 else 0).toByte())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SistemaOperativoParcelable>{
        override fun createFromParcel(source: Parcel): SistemaOperativoParcelable {
            return SistemaOperativoParcelable(source)
        }

        override fun newArray(size: Int): Array<SistemaOperativoParcelable?> {
            return arrayOfNulls(size)
        }
    }


}

fun Parcel.escribirDate(date:Date?){
    writeLong(date?.time?: -1)
}

fun Parcel.leerDate(): Date? {
    val long = readLong()
    return if (long != -1L) Date(long) else null
}

class SistemaOperativo(var nombre:String,
                       var versionApi:Int,
                       var fechaLanzamaineto: Date,
                       var pesoEnGigas:Double,
                       var instalado:Boolean){}

class CrearSistemOperativo(){
    companion object {

        var sistemaOp: ArrayList<SistemaOperativo> = ArrayList()

        init {
            sistemaOp.add(SistemaOperativo("algo1",1, Date(),1.1,true))
            sistemaOp.add(SistemaOperativo("algo2",2, Date(),1.2,false))
        }
    }
}

class SistemaOperativoAdaptador(private val listaSistema: List<SistemaOperativo>): RecyclerView.Adapter<SistemaOperativoAdaptador.MyViewHolder>(){

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var nombre: TextView
        var versionApi: TextView
        var pesoEnGigas: TextView
        lateinit var sistema: SistemaOperativo
        var botonDetalle: Button

        init {
            nombre = view.findViewById(R.id.txtv_nombre) as TextView
            versionApi = view.findViewById(R.id.txtv_detalle1) as TextView
            pesoEnGigas = view.findViewById(R.id.txtv_detalle2) as TextView
            botonDetalle = view.findViewById(R.id.boton_detalle) as Button

            val layout = view.findViewById(R.id.relative_layout) as RelativeLayout
            layout.setOnClickListener({v ->
                val nombreActual = v.findViewById(R.id.txtv_nombre) as TextView
            })
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val sistema = listaSistema[position]
        holder.nombre.setText(sistema.nombre)
        val versApi = sistema.versionApi.toString()
        holder.versionApi.setText(versApi)
        val peso = sistema.pesoEnGigas.toString()
        holder.pesoEnGigas.setText(peso)
        holder.botonDetalle.setBackgroundColor(Color.GRAY)

        holder.botonDetalle.setOnClickListener{v ->
            val intent = Intent(v.context,DetalleSOActivity::class.java)
            startActivity(v.context, intent, null)
        }


    }

    override fun getItemCount(): Int {
        return listaSistema.size
    }

}
