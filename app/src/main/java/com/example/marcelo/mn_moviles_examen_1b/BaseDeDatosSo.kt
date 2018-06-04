package com.example.marcelo.mn_moviles_examen_1b

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BaseDeDatosSo {

    companion object BaseDeDatos{

        val BDD_NOMBRE = "aplicacion"

        val BDD_TABLA_SO_NOMBRE =   "sistemaOperativo"
        val BDD_TABLA_SO_CAMPO_ID = "idSo"
        val BDD_TABLA_SO_CAMPO_NOMBRE = "nombreSo"
        val BDD_TABLA_SO_CAMPO_VERSIONAPI = "versionApi"
        val BDD_TABLA_SO_CAMPO_FECHALANZAMIENTO = "fechaLanzamientoSo"
        val BDD_TABLA_SO_CAMPO_PESOENGIGAS = "pesoEnGigasSo"
        val BDD_TABLA_SO_CAMPO_INSTALADO = "instalado"


    }

}

class DbHandlerSo(context:Context): SQLiteOpenHelper(context,BaseDeDatosSo.BDD_NOMBRE,null,1){

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSo = "CREATE TABLE ${BaseDeDatosSo.BDD_TABLA_SO_NOMBRE} (${BaseDeDatosSo.BDD_TABLA_SO_CAMPO_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${BaseDeDatosSo.BDD_TABLA_SO_CAMPO_NOMBRE} VARCHAR(60), ${BaseDeDatosSo.BDD_TABLA_SO_CAMPO_VERSIONAPI} INTEGER, ${BaseDeDatosSo.BDD_TABLA_SO_CAMPO_FECHALANZAMIENTO} VARCHAR(60), ${BaseDeDatosSo.BDD_TABLA_SO_CAMPO_PESOENGIGAS} DOUBLE, ${BaseDeDatosSo.BDD_TABLA_SO_CAMPO_INSTALADO} INTEGER)"
        db?.execSQL(createTableSo)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarSo(so: SO){

        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(BaseDeDatosSo.BDD_TABLA_SO_CAMPO_NOMBRE,so.nombre)
        cv.put(BaseDeDatosSo.BDD_TABLA_SO_CAMPO_VERSIONAPI,so.versionApi)
        cv.put(BaseDeDatosSo.BDD_TABLA_SO_CAMPO_FECHALANZAMIENTO,so.fechaLanzamiento)
        cv.put(BaseDeDatosSo.BDD_TABLA_SO_CAMPO_PESOENGIGAS,so.pesoEnGigas)
        cv.put(BaseDeDatosSo.BDD_TABLA_SO_CAMPO_INSTALADO,so.instalado)

        val resultado = dbWriteable.insert(BaseDeDatosSo.BDD_TABLA_SO_NOMBRE,null,cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()

    }

    fun borrarSo(id: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${BaseDeDatosSo.BDD_TABLA_SO_CAMPO_ID} = $id"
        return dbWriteable.delete(BaseDeDatosSo.BDD_TABLA_SO_NOMBRE, whereClause, null) > 0
    }

    fun leerSo(): ArrayList<SO> {

        var lista = ArrayList<SO>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${BaseDeDatosSo.BDD_TABLA_SO_NOMBRE}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {
                val id = resultado.getString(0).toInt()
                val nombre = resultado.getString(1)
                val version = resultado.getString(2).toInt()
                val fecha = resultado.getString(3)
                val peso = resultado.getString(4).toDouble()
                val instaldo = resultado.getString(5).toInt()

                lista.add(SO(id, nombre, version, fecha, peso, instaldo))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }


}
