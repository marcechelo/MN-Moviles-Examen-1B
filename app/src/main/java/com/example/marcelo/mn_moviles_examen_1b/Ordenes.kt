package com.example.marcelo.mn_moviles_examen_1b

import android.os.Parcel
import android.os.Parcelable

class Ordenes(var id: Int,
              var fecha: String,
              var total: Double,
              var estado: Int,
              var latitud: Double,
              var longitud: Double,
              var costoDelivery: Double,
              var fechaEntrega: String,
              var idUsuario:Int,
              var idDetalle: Int,
              var createdAt: Long,
              var updatedAt: Long) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readLong()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(id)
        destino?.writeString(fecha)
        destino?.writeDouble(total)
        destino?.writeInt(estado)
        destino?.writeDouble(latitud)
        destino?.writeDouble(longitud)
        destino?.writeDouble(costoDelivery)
        destino?.writeString(fechaEntrega)
        destino?.writeInt(idUsuario)
        destino?.writeInt(idDetalle)
        destino?.writeLong(createdAt)
        destino?.writeLong(updatedAt)
    }

    companion object CREATOR : Parcelable.Creator<Ordenes> {
        override fun createFromParcel(parcel: Parcel): Ordenes {
            return Ordenes(parcel)
        }

        override fun newArray(size: Int): Array<Ordenes?> {
            return arrayOfNulls(size)
        }
    }
}