package com.example.marcelo.mn_moviles_examen_1b

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import kotlinx.android.synthetic.main.activity_tomar_foto.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TomarFoto : AppCompatActivity() {
    var directorioActualImagen = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tomar_foto)


        /*val fotoActual = File(
                Environment.getExternalStorageDirectory().path + // ->
                        "/Android/data/com.example.usrgam.ejemplo/files/Pictures/JPEG_20180613_144431_1735249381.jpg")

        val fotoActualBitmap = BitmapFactory
                .decodeFile(fotoActual.getAbsolutePath())

        imagen_view.setImageBitmap(fotoActualBitmap)*/




        boton_camara.setOnClickListener { view ->
            tomarFoto()
        }
    }

    private fun tomarFoto() {
        val archivoImagen = crearArchivo(
                "JPEG_",
                Environment.DIRECTORY_PICTURES,
                ".jpg")

        enviarIntentFoto(archivoImagen)
    }

    private fun crearArchivo(prefijo: String,
                             directorio: String,
                             extension: String): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(Date())

        val imageFileName = prefijo + timeStamp + "_"
        val storageDir = getExternalFilesDir(directorio)

        return File.createTempFile(
                imageFileName, /* prefix */
                extension, /* suffix */
                storageDir      /* directory */
        )
    }

    private fun enviarIntentFoto(archivo: File) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val photoURI: Uri = FileProvider
                .getUriForFile(
                        this,
                        "com.example.usrgam.ejemplo.fileprovider",
                        archivo)

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, TOMAR_FOTO_REQUEST);
        }

    }

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent) {
        when (requestCode) {
            TOMAR_FOTO_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val extras = data.extras
                    val fotoActualBitmap = extras!!.get("data") as Bitmap
                    imagen_view.setImageBitmap(fotoActualBitmap)
                    //obtenerInfoCodigoBarras(fotoActualBitmap)
                }
            }
        }
    }


    companion object {
        val TOMAR_FOTO_REQUEST = 1
    }


}


class GenericFileProvider : FileProvider() {

}

