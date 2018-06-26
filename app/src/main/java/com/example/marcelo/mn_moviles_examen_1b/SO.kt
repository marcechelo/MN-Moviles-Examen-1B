package com.example.marcelo.mn_moviles_examen_1b

import android.content.Context
import android.os.Parcel
import android.os.Parcelable

class SO(var id: Int,
         var nombre: String,
         var versionApi: Int,
         var fechaLanzamiento: String,
         var pesoEnGigas: Double,
         var instalado: Int,
         var createdAt: Long,
         var updatedAt: Long): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readLong()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(id)
        destino?.writeString(nombre)
        destino?.writeInt(versionApi)
        destino?.writeString(fechaLanzamiento)
        destino?.writeDouble(pesoEnGigas)
        destino?.writeInt(instalado)
        destino?.writeLong(createdAt)
        destino?.writeLong(updatedAt)
    }

    companion object CREATOR : Parcelable.Creator<SO> {
        override fun createFromParcel(parcel: Parcel): SO {
            return SO(parcel)
        }

        override fun newArray(size: Int): Array<SO?> {
            return arrayOfNulls(size)
        }
    }

}