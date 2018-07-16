package com.example.marcelo.mn_moviles_examen_1b

import android.os.Parcel
import android.os.Parcelable

class DetalleOrden(var id: Int,
                   var precio: Double,
                   var idOrden: Int,
                   var createdAt: Long,
                   var updatedAt: Long) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
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
        destino?.writeDouble(precio)
        destino?.writeInt(idOrden)
        destino?.writeLong(createdAt)
        destino?.writeLong(updatedAt)
    }

    companion object CREATOR : Parcelable.Creator<DetalleOrden> {
        override fun createFromParcel(parcel: Parcel): DetalleOrden {
            return DetalleOrden(parcel)
        }

        override fun newArray(size: Int): Array<DetalleOrden?> {
            return arrayOfNulls(size)
        }
    }
}