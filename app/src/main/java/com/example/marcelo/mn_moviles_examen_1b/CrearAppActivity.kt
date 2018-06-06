package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_crear_app.*

class CrearAppActivity : AppCompatActivity() {

    lateinit var dbHnadler: DbHandlerApp
    var soId = 0
    var aplicacion: App? = null
    var tipo = false
    var soIntent: SO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_app)

        val type = intent.getStringExtra("tipo")
        soIntent = intent.getParcelableExtra("sistema")

        if (type.equals("Edit")) {
            txt_descripcion_app.text = "Editar Aplicacion"
            aplicacion = intent.getParcelableExtra("app")
            soId = aplicacion?.sistemaOperativoId!!
            llenar()
            tipo = true
        }else {soId = intent.getIntExtra("sistemaId",0)}


        dbHnadler = DbHandlerApp(this)


        boton_guardar_app.setOnClickListener { view: View ->

            if (!tipo){

                if (edit_nombre_app.text.toString().isEmpty() ||
                        edit_peso_app.text.toString().isEmpty() ||
                        edit_version.text.toString().isEmpty() ||
                        edit_url.text.toString().isEmpty() ||
                        edit_fecha_app.text.toString().isEmpty() ||
                        edit_costo.text.toString().isEmpty()){

                    val toast = Toast.makeText(this,"Todos los campos deben estra lleno", Toast.LENGTH_SHORT)
                    toast.show()

                } else {
                    var nombre = edit_nombre_app.text.toString()
                    var peso = edit_peso_app.text.toString().toDouble()
                    var version =  edit_version.text.toString().toInt()
                    var url = edit_url.text.toString()
                    var fecha = edit_fecha_app.text.toString()
                    var costo = edit_costo.text.toString().toDouble()
                    var app = App(0,nombre,peso,version,url,fecha,costo,soId)
                    dbHnadler.insertarApp(app)
                    irAActividadDetalleSo()
                }

            }else{

                if (edit_nombre_app.text.toString().isEmpty() ||
                        edit_peso_app.text.toString().isEmpty() ||
                        edit_version.text.toString().isEmpty() ||
                        edit_url.text.toString().isEmpty() ||
                        edit_fecha_app.text.toString().isEmpty() ||
                        edit_costo.text.toString().isEmpty()){

                    val toast = Toast.makeText(this,"Todos los campos deben estra lleno", Toast.LENGTH_SHORT)
                    toast.show()

                } else {
                    var nombre = edit_nombre_app.text.toString()
                    var peso = edit_peso_app.text.toString().toDouble()
                    var version =  edit_version.text.toString().toInt()
                    var url = edit_url.text.toString()
                    var fecha = edit_fecha_app.text.toString()
                    var costo = edit_costo.text.toString().toDouble()
                    var app = App(aplicacion?.appid!!,nombre,peso,version,url,fecha,costo,soId)
                    dbHnadler.updateApp(app)
                    irAActividadDetalleSo()
                }

            }
        }
    }

    fun llenar(){
        edit_nombre_app.setText(aplicacion?.nombre)
        edit_peso_app.setText(aplicacion?.pesoEnGigas.toString())
        edit_version.setText(aplicacion?.version.toString())
        edit_url.setText(aplicacion?.urlDescarga)
        edit_fecha_app.setText(aplicacion?.fechaLanzamiento)
        edit_costo.setText(aplicacion?.costo.toString())
    }

    fun irAActividadDetalleSo(){
        var intent = Intent(this,DetalleSOActivity::class.java)
        intent.putExtra("sistema",soIntent)
        startActivity(intent)
    }
}
