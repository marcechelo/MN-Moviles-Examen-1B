package com.example.marcelo.mn_moviles_examen_1b

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home_principal_comprador.*

class HomePrincipalComprador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_principal_comprador)

         var usuario: Usuario = intent.getParcelableExtra("usuario")

        text_saludo_comprador.text = "Bienvenido ${usuario.username}"

    }
}
