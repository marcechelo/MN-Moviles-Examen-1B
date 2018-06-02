package com.example.marcelo.mn_moviles_examen_1b

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.drawable.AdaptiveIconDrawable
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class BaseDeDatos {

    companion object BaseDeDatos{

        val BDD_NOMBRE = "aplicacion"

        val BDD_TABLA_SO_NOMBRE =   "sistemaOperativo"
        //val BDD_TABLA_SO_CAMPO_ID = "idSo"
        val BDD_TABLA_SO_CAMPO_NOMBRE = "nombreSo"
        val BDD_TABLA_SO_CAMPO_VERSIONAPI = "versionApi"
        val BDD_TABLA_SO_CAMPO_FECHALANZAMIENTO = "fechaLanzamientoSo"
        val BDD_TABLA_SO_CAMPO_PESOENGIGAS = "pesoEnGigasSo"
        val BDD_TABLA_SO_CAMPO_INSTALADO = "instalado"

        val BDD_TABLA_APLICACION_NOMBRE =   "aplicacion"
        //val BDD_TABLA_APLICACIO_CAMPO_ID = "idApp"
        val BDD_TABLA_APLICACIO_CAMPO_PESOENGIGAS = "pesoEnGigasApp"
        val BDD_TABLA_APLICACIO_CAMPO_VERSION = "version"
        val BDD_TABLA_APLICACIO_CAMPO_NOMBRE = "nombreApp"
        val BDD_TABLA_APLICACIO_CAMPO_URLDESCARGA = "urlDescarga"
        val BDD_TABLA_APLICACIO_CAMPO_FECHALANZAMIENTO = "fechaLanzamientoApp"
        val BDD_TABLA_APLICACIO_CAMPO_COSTO = "costo"
        val BDD_TABLA_APLICACIO_CAMPO_SISTEMAOPERATIVOID = "sistemaOperativoId"


    }

}

class DbHandlerAplicacion(context:Context): SQLiteOpenHelper(context,BaseDeDatos.BDD_NOMBRE,null,1){

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSo = "CREATE TABLE ${BaseDeDatos.BDD_TABLA_SO_NOMBRE} " +
                "(${BaseDeDatos.BDD_TABLA_SO_CAMPO_NOMBRE} VARCHAR(60)," +
                "${BaseDeDatos.BDD_TABLA_SO_CAMPO_VERSIONAPI} INTEGER," +
                "${BaseDeDatos.BDD_TABLA_SO_CAMPO_FECHALANZAMIENTO} VARCHAR(60)," +
                "${BaseDeDatos.BDD_TABLA_SO_CAMPO_PESOENGIGAS} DOUBLE," +
                "${BaseDeDatos.BDD_TABLA_SO_CAMPO_INSTALADO} BOOLEAN)"

        val createTableApp = "CREATE TABLE ${BaseDeDatos.BDD_TABLA_APLICACION_NOMBRE} " +
                "(${BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_PESOENGIGAS} DOUBLE," +
                "${BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_VERSION} INTEGER," +
                "${BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_NOMBRE} VARCHAR(60)," +
                "${BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_URLDESCARGA} VARCHAR(100)," +
                "${BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_FECHALANZAMIENTO} DATE," +
                "${BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_COSTO} DOUBLE," +
                "${BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_SISTEMAOPERATIVOID} INTEGER)"

        db?.execSQL(createTableSo)
        db?.execSQL(createTableApp)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarSo(nombre: String,
                  versionApi: Int,
                  fechaLanzamiento: String,
                  pesoEnGigas:Double,
                  instaldo:Boolean){

        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(BaseDeDatos.BDD_TABLA_SO_CAMPO_NOMBRE,nombre)
        cv.put(BaseDeDatos.BDD_TABLA_SO_CAMPO_VERSIONAPI,versionApi)
        cv.put(BaseDeDatos.BDD_TABLA_SO_CAMPO_FECHALANZAMIENTO,fechaLanzamiento)
        cv.put(BaseDeDatos.BDD_TABLA_SO_CAMPO_PESOENGIGAS,pesoEnGigas)
        cv.put(BaseDeDatos.BDD_TABLA_SO_CAMPO_INSTALADO,instaldo)

        val resultado = dbWriteable.insert(BaseDeDatos.BDD_TABLA_SO_NOMBRE,null,cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()

    }

    fun insertrApp(pesoEnGigas: Double,
                   version: Int,
                   nombre: String,
                   urlDescarga: String,
                   fechaLanzamiento: Date,
                   costo: Double,
                   sistemaOperativoId: Int){

        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_PESOENGIGAS,pesoEnGigas)
        cv.put(BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_VERSION,version)
        cv.put(BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_NOMBRE,nombre)
        cv.put(BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_URLDESCARGA,urlDescarga)
        cv.put(BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_FECHALANZAMIENTO,toSimpleString(fechaLanzamiento))
        cv.put(BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_COSTO,costo)
        cv.put(BaseDeDatos.BDD_TABLA_APLICACIO_CAMPO_SISTEMAOPERATIVOID,sistemaOperativoId)

        val resultado = dbWriteable.insert(BaseDeDatos.BDD_TABLA_APLICACION_NOMBRE,null,cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()

    }


    fun leerDatosSo(){

        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${BaseDeDatos.BDD_TABLA_SO_NOMBRE}"
        val resultado = dbReadable.rawQuery(query,null)

        if (resultado.moveToFirst()) {
            do {
                val nombreActual = resultado.getString(0)
                val versionApi = resultado.getString(1).toInt()
                Log.i("database", "El nombre es $nombreActual con version $versionApi")
            } while (resultado.moveToNext())
        }
        resultado.close()
        dbReadable.close()
    }

    fun toSimpleString(date: Date?) = with(date ?: Date()) {
        SimpleDateFormat("dd/MM/yyy").format(this)
    }

}
