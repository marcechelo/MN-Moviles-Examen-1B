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
import android.util.JsonReader
import android.util.Log
import android.view.*
import android.widget.*
import com.beust.klaxon.Klaxon
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_listar_so.*
import java.io.StringReader
import java.util.*
import kotlin.collections.ArrayList

class ListarSOActivity : AppCompatActivity() {

    lateinit var sistemOp: ArrayList<SO>
    lateinit var dbHandler: DbHandlerSo
    lateinit var adaptador: SistemaOperativoAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_so)

        dbHandler = DbHandlerSo(this)
        //sistemOp = dbHandler.leerSo()
        sistemOp = streamingArray(listarOs())
        val layoutManager = LinearLayoutManager(this)
        //sistemOp = CrearSistemOperativo.sistemaOp
        adaptador = SistemaOperativoAdaptador(sistemOp)
        recycler_view.layoutManager = layoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(recycler_view)

    }

    fun streamingArray(jsonStringEntrenador: String):ArrayList<SO> {
        val klaxon = Klaxon()
        val result = arrayListOf<SO>()
        JsonReader(StringReader(jsonStringEntrenador)).use { reader ->
            reader.beginArray {
                while (reader.hasNext()) {
                    val sistemaOperativo: SistemaOperativo? = Klaxon().parse<SistemaOperativo>(jsonStringEntrenador)
                    val id = sistemaOperativo!!.id
                    val nombre = sistemaOperativo.nombreSo
                    val version = sistemaOperativo.versionApi
                    val fecha = sistemaOperativo.fechaLanzamiento
                    val peso = sistemaOperativo.pesoGigasSo
                    val instaldo = sistemaOperativo.instalado
                    result.add(SO(id,nombre,version,fecha,peso,instaldo))
                }
            }
        }
        return result
    }

    fun listarOs ():String{

        var resulatdo = ""
        "http://192.168.1.3:1337/SistemaOperativo/1".httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    Log.i("http-ejemplo", "Error ${ex.response}")
                }
                is Result.Success -> {
                    val jsonStringEntrenador = result.get()
                    Log.i("http-ejemplo", "Exito ${jsonStringEntrenador}")
                    resulatdo = jsonStringEntrenador
                    val sistemaOperativo: SistemaOperativo? = Klaxon().parse<SistemaOperativo>(jsonStringEntrenador)

                        if (sistemaOperativo != null) {
                        Log.i("http-ejemplo", "Nombre: ${sistemaOperativo.nombreSo}")
                        Log.i("http-ejemplo", "API: ${sistemaOperativo.versionApi}")
                        Log.i("http-ejemplo", "fecha: ${sistemaOperativo.fechaLanzamiento}")
                        Log.i("http-ejemplo", "peso: ${sistemaOperativo.pesoGigasSo}")
                        Log.i("http-ejemplo", "instalado: ${sistemaOperativo.instalado}")
                        Log.i("http-ejemplo", "Id: ${sistemaOperativo.id}")
                        Log.i("http-ejemplo", "Creado: ${sistemaOperativo.createdAtDate}")
                        Log.i("http-ejemplo", "Actualizado: ${sistemaOperativo.updatedAtDate}")

                        /*sistemaOperativo.pokemons.forEach { pokemon: Pokemon ->
                            Log.i("http-ejemplo", "Nombre ${pokemon.nombre}")
                            Log.i("http-ejemplo", "Tipo ${pokemon.tipo}")
                            Log.i("http-ejemplo", "Numero ${pokemon.numero}")
                        }*/

                    } else {
                        Log.i("http-ejemplo", "Entrenador nulo")
                    }


                }
            }
        }

        return resulatdo
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
            R.id.item_menu_compartir -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/html"
                intent.putExtra(Intent.EXTRA_SUBJECT, "Examen Moviles - SO y Aplicaciones")
                intent.putExtra(Intent.EXTRA_TEXT,"Datos del Sistema Operativo - ${sistema}" )
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}

private fun <T> Klaxon.parse(reader: JsonReader) {

}

private fun JsonReader.beginArray(function: () -> Unit) {

}




