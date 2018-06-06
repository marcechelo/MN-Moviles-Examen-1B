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
          var sistemaOperativoId: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt()) {
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
        destino?.writeInt(sistemaOperativoId)
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