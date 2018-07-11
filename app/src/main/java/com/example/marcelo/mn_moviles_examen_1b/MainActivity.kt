package com.example.marcelo.mn_moviles_examen_1b

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var usuarios: ArrayList<Usuario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //boton_listar.setOnClickListener { view: View -> irAActividadListarSo() }
        //boton_refrescar.setOnClickListener { view: View -> irAActividadCrearSo() }
        boton_ingresar.setOnClickListener{view: View -> logIn()}

    }

    /*fun irAActividadListarSo(){
        val intent = Intent(this,ListarSOActivity::class.java)
        startActivity(intent)
    }

    fun irAActividadCrearSo(){
        val intent = Intent(this,CrearSoActivity::class.java)
        intent.putExtra("tipo", "Create")
        startActivity(intent)
    }*/

    fun logIn(){

        usuarios = BaseDeDatosUsuario.getUsuario(text_username.text.toString())
        var longitud = usuarios.size
        //val toast = Toast.makeText(this,longitud.toString(),Toast.LENGTH_SHORT)
        //toast.show()
        if (longitud === 0){
            val toast = Toast.makeText(this,"Usuario No Existe",Toast.LENGTH_SHORT)
            toast.show()
        }else{
            val contraseña = text_password.text.toString()
            if (usuarios[0].password.equals(contraseña)){
                when(usuarios[0].tipo){
                    1 -> {
                        val intent = Intent(this,HomeVendedor::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this,HomeComprador::class.java)
                        startActivity(intent)
                    }
                    3 -> {
                        val intent = Intent(this,HomeDelivery::class.java)
                        startActivity(intent)
                    }
                    else -> {
                        val toast = Toast.makeText(this,"Usuario No Tiene Tipo",Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
                //val intent = Intent(this,ListarSOActivity::class.java)
                //startActivity(intent)
            }else{
                val toast = Toast.makeText(this,"Contraseña Incorrecta",Toast.LENGTH_SHORT)
                toast.show()
            }
        }

    }


}
