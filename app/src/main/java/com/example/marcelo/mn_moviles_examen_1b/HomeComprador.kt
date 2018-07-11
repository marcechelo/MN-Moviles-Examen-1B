package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detalle_so.*
import kotlinx.android.synthetic.main.activity_home_comprador.*
import kotlinx.android.synthetic.main.activity_main.*

class HomeComprador : AppCompatActivity() {

    lateinit var adaptador: AplicacionAdaptador2
    lateinit var aplicaciones: ArrayList<App>
    lateinit var arregloIds: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_comprador)

        arregloIds = ArrayList()
        boton_buscar.setOnClickListener { view: View ->
            val busqueda = text_buscar.text.toString()
            aplicaciones = BaseDeDatosApp.getAplicacionesBusqueda(busqueda)
            val layoutManager = LinearLayoutManager(this)
            adaptador = AplicacionAdaptador2(aplicaciones)
            adaptador.notifyDataSetChanged()
            recyclerview_lista_apps.layoutManager = layoutManager
            recyclerview_lista_apps.itemAnimator = DefaultItemAnimator()
            recyclerview_lista_apps.adapter = adaptador
            recyclerview_lista_apps.invalidate()
            registerForContextMenu(recyclerview_lista_apps)

        }

        boton_mostrar_todos.setOnClickListener {view: View ->
            aplicaciones = BaseDeDatosApp.getTodasAplicaciones()
            val layoutManager = LinearLayoutManager(this)
            adaptador = AplicacionAdaptador2(aplicaciones)
            adaptador.notifyDataSetChanged()
            recyclerview_lista_apps.layoutManager = layoutManager
            recyclerview_lista_apps.itemAnimator = DefaultItemAnimator()
            recyclerview_lista_apps.adapter = adaptador
            recyclerview_lista_apps.invalidate()
            registerForContextMenu(recyclerview_lista_apps)
        }

        boton_carrito.setOnClickListener{view: View -> irAActividadCarrito()}

    }

    fun irAActividadCarrito(){
        val intent = Intent(this,Carrito::class.java)
        intent.putIntegerArrayListExtra("ids",arregloIds)
        startActivity(intent)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var posicion = adaptador.getPosition()
        var aplicacion = aplicaciones[posicion]

        when(item.itemId){
            R.id.item_menu_editar-> {

                when (aplicacion.estado){
                    1 ->{
                        val toast = Toast.makeText(this,"Seleccioado ${aplicacion.nombre}", Toast.LENGTH_SHORT)
                        toast.show()
                        arregloIds.add(aplicacion.appid)
                        BaseDeDatosApp.putAplicacionEstado(aplicacion,2)
                    }
                    2 ->{
                        val toast = Toast.makeText(this,"Item ya Seleccionado", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    3 ->{
                        val toast = Toast.makeText(this,"Item no disponible", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    else ->{
                        Log.i("Error","Error")}
                }

                /*val layoutManager = LinearLayoutManager(this)
                adaptador = AplicacionAdaptador2(aplicaciones)
                adaptador.notifyDataSetChanged()
                recyclerview_lista_apps.layoutManager = layoutManager
                recyclerview_lista_apps.itemAnimator = DefaultItemAnimator()
                recyclerview_lista_apps.adapter = adaptador
                recyclerview_lista_apps.invalidate()
                registerForContextMenu(recyclerview_lista_apps)*/
                return true
            }
            else -> {
                Log.i("menu", "Todos los demas")
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
