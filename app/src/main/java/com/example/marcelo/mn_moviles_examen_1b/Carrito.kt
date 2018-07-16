package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_carrito.*
import kotlinx.android.synthetic.main.activity_home_comprador.*

class Carrito : AppCompatActivity() {

    lateinit var adaptador: AplicacionAdaptador2
    lateinit var arreglo: ArrayList<Int>
    lateinit var aplicaciones: ArrayList<App>
    lateinit var aplicacionesAux: ArrayList<App>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        //aplicaciones = intent.getParcelableArrayListExtra("aplicaciones")

        /*arreglo = intent.getIntegerArrayListExtra("ids")
        aplicaciones = ArrayList()
        arreglo.forEach {
            aplicaciones.add(BaseDeDatosApp.getAplicacioneUnica(it))
        }*/

        //aplicaciones = intent.getParcelableArrayListExtra("aplicaciones")
        aplicaciones = BaseDeDatosApp.getTodasAplicaciones()
        aplicacionesAux= ArrayList()

        aplicaciones.forEach {

            if (it.estado == 2){
                aplicacionesAux.add(it)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        adaptador = AplicacionAdaptador2(aplicacionesAux,2)
        adaptador.notifyDataSetChanged()
        recyclerview_carrito.layoutManager = layoutManager
        recyclerview_carrito.itemAnimator = DefaultItemAnimator()
        recyclerview_carrito.adapter = adaptador
        recyclerview_carrito.invalidate()
        registerForContextMenu(recyclerview_carrito)

        boton_guardar.setOnClickListener { view: View ->

            if (aplicacionesAux.size === 0) {
                val toast = Toast.makeText(this, "No existen Items seleccionados", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                irAActividadOrdenes()
            }

            finish()
        }

        boton_cancelar.setOnClickListener { view: View ->
            aplicaciones.forEach {

                if (it.estado === 2){
                BaseDeDatosApp.putAplicacionEstado(it, 1)
                }
            }

            irAActividadBuscar()
        }

    }

    fun irAActividadOrdenes() {
        val intent = Intent(this, CrearOrden::class.java)
        intent.putParcelableArrayListExtra("aplicaciones", aplicacionesAux)
        startActivity(intent)
    }

    fun irAActividadBuscar() {
        val intent = Intent(this, BuscarComprador::class.java)
        startActivity(intent)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {

        var posicion = adaptador.getPosition()
        var aplicacion = aplicacionesAux[posicion]

        when (item.itemId) {
            R.id.item_menu_editar -> {
                BaseDeDatosApp.putAplicacionEstado(aplicacion, 1)
                aplicacionesAux.removeAt(posicion)
                val layoutManager = LinearLayoutManager(this)
                adaptador = AplicacionAdaptador2(aplicacionesAux,2)
                adaptador.notifyDataSetChanged()
                recyclerview_carrito.layoutManager = layoutManager
                recyclerview_carrito.itemAnimator = DefaultItemAnimator()
                recyclerview_carrito.adapter = adaptador
                recyclerview_carrito.invalidate()
                registerForContextMenu(recyclerview_carrito)
                return true
            }
            else -> {
                Log.i("menu", "Todos los demas")
                return super.onOptionsItemSelected(item)
            }
        }
    }

}
