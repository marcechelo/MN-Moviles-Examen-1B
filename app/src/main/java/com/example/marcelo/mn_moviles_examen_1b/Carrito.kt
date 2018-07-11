package com.example.marcelo.mn_moviles_examen_1b

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_carrito.*
import kotlinx.android.synthetic.main.activity_home_comprador.*

class Carrito : AppCompatActivity() {

    lateinit var adaptador: AplicacionAdaptador2
    lateinit var arreglo:ArrayList<Int>
    lateinit var aplicaciones: ArrayList<App>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        aplicaciones = ArrayList()
        arreglo = intent.getIntegerArrayListExtra("ids")

        if(arreglo.size != 0){

            arreglo.forEach {
                val aplicacion = BaseDeDatosApp.getAplicacioneUnica(it)
                aplicaciones.add(aplicacion)
            }
            val layoutManager = LinearLayoutManager(this)
            adaptador = AplicacionAdaptador2(aplicaciones)
            adaptador.notifyDataSetChanged()
            recyclerview_carrito.layoutManager = layoutManager
            recyclerview_carrito.itemAnimator = DefaultItemAnimator()
            recyclerview_carrito.adapter = adaptador
            recyclerview_carrito.invalidate()
            registerForContextMenu(recyclerview_carrito)


        }else{
            val toast = Toast.makeText(this,"El Carro esta Vacio",Toast.LENGTH_SHORT)
            toast.show()
        }

        boton_guardar.setOnClickListener{view: View ->

            aplicaciones.forEach{
                BaseDeDatosApp.putAplicacionEstado(it,3)
            }
        }

    }

}
