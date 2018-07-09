package com.example.marcelo.mn_moviles_examen_1b

import android.os.Parcel
import android.os.Parcelable

class Usuario (var id: Int,
               var tipo: Int,
               var username: String,
               var password: String): Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeInt(id)
        destino?.writeInt(tipo)
        destino?.writeString(username)
        destino?.writeString(password)
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }
}