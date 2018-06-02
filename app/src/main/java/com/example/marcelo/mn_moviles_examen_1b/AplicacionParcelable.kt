/*package com.example.marcelo.mn_moviles_examen_1b

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class AplicacionParcelable (var pesoEnGigas: Double,
                            var version:Int,
                            var nombre:String,
                            var urlDescarga:String,
                            var fechaLanzamiento:Date?,
                            var costo:Double,
                            var sistemaOperativoId:String){

    constructor(parcel:Parcel):this(
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.leerDate(),
            parcel.readDouble(),
            parcel.readString()){
    }

    override fun writeToParcel(destino: Parcel?, p1: Int) {
        destino?.writeDouble(pesoEnGigas)
        destino?.writeInt(version)
        destino?.writeString(nombre)
        destino?.writeString(urlDescarga)
        destino?.escribirDate(fechaLanzamiento)
        destino?.writeDouble(costo)
        destino?.writeString(sistemaOperativoId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AplicacionParcelable> {
        override fun createFromParcel(parcel: Parcel): AplicacionParcelable {
            return AplicacionParcelable(parcel)
        }

        override fun newArray(size: Int): Array<AplicacionParcelable?> {
            return arrayOfNulls(size)
        }
    }

}*/
