package com.example.marcelo.mn_moviles_examen_1b

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BaseDeDatosApp {
    companion object {

        val BDD_NOMBRE = "aplicacion"
        val BDD_TABLA_APLICACION_NOMBRE = "tablaAplicacion"
        val BDD_TABLA_APLICACIO_CAMPO_ID = "idApp"
        val BDD_TABLA_APLICACIO_CAMPO_NOMBRE = "nombreApp"
        val BDD_TABLA_APLICACIO_CAMPO_PESOENGIGAS = "pesoEnGigasApp"
        val BDD_TABLA_APLICACIO_CAMPO_VERSION = "version"
        val BDD_TABLA_APLICACIO_CAMPO_URLDESCARGA = "urlDescarga"
        val BDD_TABLA_APLICACIO_CAMPO_FECHALANZAMIENTO = "fechaLanzamientoApp"
        val BDD_TABLA_APLICACIO_CAMPO_COSTO = "costo"
        val BDD_TABLA_APLICACIO_CAMPO_SISTEMAOPERATIVOID = "sistemaOperativoId"
    }
}

class DbHandlerApp(context: Context) : SQLiteOpenHelper(context, BaseDeDatosApp.BDD_NOMBRE, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableSQL = "CREATE TABLE ${BaseDeDatosApp.BDD_TABLA_APLICACION_NOMBRE} (${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_ID} INTEGER PRIMARY KEY, ${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_NOMBRE} VARCHAR(50), ${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_PESOENGIGAS} DOUBLE, ${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_VERSION} INTEGER, ${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_URLDESCARGA} VARCHAR(100), ${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_FECHALANZAMIENTO} VARCHAR(20),  ${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_COSTO} DOUBLE, ${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_SISTEMAOPERATIVOID} INTEGER)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertarApp(aplicacion: App) {

        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_NOMBRE, aplicacion.nombre)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_PESOENGIGAS, aplicacion.pesoEnGigas)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_VERSION, aplicacion.version)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_URLDESCARGA, aplicacion.urlDescarga)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_FECHALANZAMIENTO, aplicacion.fechaLanzamiento)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_COSTO, aplicacion.costo)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_SISTEMAOPERATIVOID, aplicacion.sistemaOperativoId)

        val resultado = dbWriteable.insert(BaseDeDatosApp.BDD_TABLA_APLICACION_NOMBRE, null, cv)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun updateApp(app: App) {
        val dbWriteable = writableDatabase
        val cv = ContentValues()

        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_NOMBRE, app.nombre)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_PESOENGIGAS, app.pesoEnGigas)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_VERSION, app.version)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_URLDESCARGA, app.urlDescarga)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_FECHALANZAMIENTO, app.fechaLanzamiento)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_COSTO, app.costo)
        cv.put(BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_SISTEMAOPERATIVOID, app.sistemaOperativoId)

        val whereClause = "${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_ID} = ${app.appid}"
        val resultado = dbWriteable.update(BaseDeDatosApp.BDD_TABLA_APLICACION_NOMBRE, cv, whereClause, null)

        Log.i("database", "Si es -1 hubo error, sino exito: Respuesta: $resultado")

        dbWriteable.close()
    }

    fun borrarApp(id: Int): Boolean {
        val dbWriteable = writableDatabase
        val whereClause = "${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_ID} = $id"
        return dbWriteable.delete(BaseDeDatosApp.BDD_TABLA_APLICACION_NOMBRE, whereClause, null) > 0
    }

    fun leerApp(idSo: Int): ArrayList<App> {
        var lista = ArrayList<App>()
        val dbReadable = readableDatabase
        val query = "SELECT * FROM ${BaseDeDatosApp.BDD_TABLA_APLICACION_NOMBRE} WHERE ${BaseDeDatosApp.BDD_TABLA_APLICACIO_CAMPO_SISTEMAOPERATIVOID} = ${idSo}"
        val resultado = dbReadable.rawQuery(query, null)

        if (resultado.moveToFirst()) {
            do {

                val id = resultado.getString(0).toInt()
                val nombre = resultado.getString(1)
                val peso = resultado.getString(2).toDouble()
                val version = resultado.getString(3).toInt()
                val url = resultado.getString(4)
                val fecha = resultado.getString(5)
                val costo = resultado.getString(6).toDouble()
                val sistema = resultado.getString(7).toInt()

                lista.add(App(id,nombre, peso, version, url, fecha, costo, sistema))
            } while (resultado.moveToNext())
        }

        resultado.close()
        dbReadable.close()

        return lista
    }

}