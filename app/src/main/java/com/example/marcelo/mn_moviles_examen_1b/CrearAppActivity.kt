package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_crear_app.*

class CrearAppActivity : AppCompatActivity() {

    lateinit var dbHnadler: DbHandlerApp
    lateinit var soIntent:SO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_app)

        dbHnadler = DbHandlerApp(this)
        soIntent = intent.getParcelableExtra("so")
        val soId: Int = soIntent.id
        val toast1 = Toast.makeText(this,soId.toString(), Toast.LENGTH_SHORT)
        toast1.show()

        boton_guardar_app.setOnClickListener { view: View ->

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
                irAActividadDetalleSo()
            }
        }
    }

    fun irAActividadDetalleSo(){
        var intent = Intent(this,DetalleSOActivity::class.java)
        intent.putExtra("sistema",this.soIntent)
        startActivity(intent)
    }
}
