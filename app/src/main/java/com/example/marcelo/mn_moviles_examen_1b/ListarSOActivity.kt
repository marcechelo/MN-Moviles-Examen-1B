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



