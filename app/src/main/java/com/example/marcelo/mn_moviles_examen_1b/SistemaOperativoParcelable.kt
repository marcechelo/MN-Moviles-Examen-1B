package com.example.marcelo.mn_moviles_examen_1b

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class SistemaOperativoParcelable (var nombre:String,
                                  var versionApi:Int,
                                  var fechaLanzamaineto: String,
                                  var pesoEnGigas:Double,
                                  var instalado:Boolean): Parcelable {

    constructor(parcel: Parcel):this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readByte() != 0.toByte()){

    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(nombre)
        dest?.writeInt(versionApi)
        dest?.writeString(fechaLanzamaineto)
        dest?.writeByte((if(instalado)1 else 0).toByte())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SistemaOperativoParcelable>{
        override fun createFromParcel(source: Parcel): SistemaOperativoParcelable {
            return SistemaOperativoParcelable(source)
        }

        override fun newArray(size: Int): Array<SistemaOperativoParcelable?> {
            return arrayOfNulls(size)
        }
    }


}

fun Parcel.escribirDate(date:Date?){
    writeLong(date?.time?: -1)
}

fun Parcel.leerDate(): Date? {
    val long = readLong()
    return if (long != -1L) Date(long) else null
}