package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
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

            if(arreglo.size === 0){
                val toast = Toast.makeText(this,"No existen Items seleccionado",Toast.LENGTH_SHORT)
                toast.show()
            }else {
                aplicaciones.forEach {
                    BaseDeDatosApp.putAplicacionEstado(it, 3)
                }

                irAActividadOrdenes()
            }
        }

        boton_cancelar.setOnClickListener{view: View ->
            aplicaciones.forEach{
                BaseDeDatosApp.putAplicacionEstado(it,1)
            }

            irAActividadBuscar()
        }

    }

    fun irAActividadOrdenes(){
        val intent = Intent(this,OrdenesComprador::class.java)
        intent.putExtra("aplicaciones", aplicaciones)
        startActivity(intent)
    }

    fun irAActividadBuscar(){
        val intent = Intent(this,BuscarComprador::class.java)
        startActivity(intent)
    }

}
