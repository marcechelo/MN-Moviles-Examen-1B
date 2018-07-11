package com.example.marcelo.mn_moviles_examen_1b

import android.os.Parcel
import android.os.Parcelable

class App(var appid: Int,
          var nombre: String,
          var pesoEnGigas: Double,
          var version: Int,
          var urlDescarga: String,
          var fechaLanzamiento: String,
          var costo: Double,
          //var latitud: Double,
          //var longitud: Double,
          var sistemaOperativoId: Int,
          var foto: String,
          var createdAt: Long,
          var updatedAt: Long) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            //parcel.readDouble(),
            //parcel.readDouble(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readLong()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(appid)
        destino?.writeString(nombre)
        destino?.writeDouble(pesoEnGigas)
        destino?.writeInt(version)
        destino?.writeString(urlDescarga)
        destino?.writeString(fechaLanzamiento)
        destino?.writeDouble(costo)
        //destino?.writeDouble(latitud)
        //destino?.writeDouble(longitud)
        destino?.writeInt(sistemaOperativoId)
        destino?.writeString(foto)
        destino?.writeLong(createdAt)
        destino?.writeLong(updatedAt)
    }

    companion object CREATOR : Parcelable.Creator<App> {
        override fun createFromParcel(parcel: Parcel): App {
            return App(parcel)
        }

        override fun newArray(size: Int): Array<App?> {
            return arrayOfNulls(size)
        }
    }

}