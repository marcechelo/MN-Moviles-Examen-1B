package com.example.marcelo.mn_moviles_examen_1b

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_crear_app.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CrearAppActivity : AppCompatActivity() {

    var soId = 0
    var appId = 0
    var aplicacion: App? = null
    var tipo = false
    var soIntent: SO? = null
    var longitu: Double = 0.0
    var latitud: Double = 0.0
    var fileName = "";
    var foto = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_app)

        val type = intent.getStringExtra("tipo")
        soIntent = intent.getParcelableExtra("sistema")

        if (type.equals("Edit")) {
            txt_descripcion_app.text = "Editar Aplicacion"
            aplicacion = intent.getParcelableExtra("app")
            soId = aplicacion?.sistemaOperativoId!!
            llenar()
            tipo = true
        } else {
            soId = intent.getIntExtra("sistemaId", 0)
        }

        boton_tomarFoto.setOnClickListener { view: View -> takePicture()

            var toast = Toast.makeText(this,"funciona",Toast.LENGTH_SHORT)
            toast.show()
        }

        boton_guardar_app.setOnClickListener { view: View ->

            //val latitud = -0.188193
            //val longitud = -78.472590

            if (!tipo) {

                if (edit_nombre_app.text.toString().isEmpty() ||
                        edit_peso_app.text.toString().isEmpty() ||
                        edit_version.text.toString().isEmpty() ||
                        edit_url.text.toString().isEmpty() ||
                        edit_fecha_app.text.toString().isEmpty() ||
                        edit_costo.text.toString().isEmpty()||
                        this.fileName.isBlank()){


                            val toast = Toast.makeText(this, "Todos los campos deben estra lleno", Toast.LENGTH_SHORT)
                    toast.show()

                } else {
                    var latitud = (-0.2103764..-0.2003764).random()
                    var longitud = (-78.4991095..-78.4891095).random()
                    var nombre = edit_nombre_app.text.toString()
                    var peso = edit_peso_app.text.toString().toDouble()
                    var version = edit_version.text.toString().toInt()
                    var url = edit_url.text.toString()
                    var fecha = edit_fecha_app.text.toString()
                    var costo = edit_costo.text.toString().toDouble()
                    var app = App(0, nombre, peso, version, url, fecha, costo, 1,  soId, 0, this.fileName, 0, 0)
                    BaseDeDatosApp.postAplicacion(app)
                    irAActividadDetalleSo()
                }

            } else {

                if (edit_nombre_app.text.toString().isEmpty() ||
                        edit_peso_app.text.toString().isEmpty() ||
                        edit_version.text.toString().isEmpty() ||
                        edit_url.text.toString().isEmpty() ||
                        edit_fecha_app.text.toString().isEmpty() ||
                        edit_costo.text.toString().isEmpty() ||
                        this.fileName.isBlank()) {

                    val toast = Toast.makeText(this, "Todos los campos deben estra lleno", Toast.LENGTH_SHORT)
                    toast.show()

                } else {
                    var urlfoto: String
                    if (fileName == null){
                         urlfoto = this.foto
                    }else{  urlfoto = fileName}

                    var nombre = edit_nombre_app.text.toString()
                    var peso = edit_peso_app.text.toString().toDouble()
                    var version = edit_version.text.toString().toInt()
                    var url = edit_url.text.toString()
                    var fecha = edit_fecha_app.text.toString()
                    var costo = edit_costo.text.toString().toDouble()
                    var app = App(aplicacion?.appid!!, nombre, peso, version, url, fecha, costo, 1, soId, 0, urlfoto, 0, 0)
                    BaseDeDatosApp.putAplicacion(app)
                    irAActividadDetalleSo()
                }

            }
        }
    }

    fun ClosedRange<Double>.random() = (Math.random() * (endInclusive - start) + start).toDouble()

    fun llenar() {
        edit_nombre_app.setText(aplicacion?.nombre)
        edit_peso_app.setText(aplicacion?.pesoEnGigas.toString())
        edit_version.setText(aplicacion?.version.toString())
        edit_url.setText(aplicacion?.urlDescarga)
        edit_fecha_app.setText(aplicacion?.fechaLanzamiento)
        edit_costo.setText(aplicacion?.costo.toString())
        this.foto = aplicacion?.foto!!
        //this.longitu = aplicacion?.longitud!!
        //this.latitud = aplicacion?.latitud!!
    }

    private fun irAActividadDetalleSo() {
        var intent = Intent(this, ListarSOActivity::class.java)
        intent.putExtra("sistema", soIntent)
        startActivity(intent)
    }


    private fun takePicture() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File

            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile))
                startActivityForResult(takePictureIntent, TOMAR_FOTO_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == TOMAR_FOTO_REQUEST && resultCode == Activity.RESULT_OK) {

            loadImageFromFile(this.fileName)
            Log.i("file name", fileName)
        }
    }

    fun loadImageFromFile(file: String) {

        val view = this.findViewById(R.id.imagen_crear_app) as ImageView
        view.setVisibility(View.VISIBLE)


        val targetW = view.getWidth()
        val targetH = view.getHeight()

        // Get the dimensions of the bitmap
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
        val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false
        bmOptions.inSampleSize = scaleFactor
        bmOptions.inPurgeable = true

        val bitmap = BitmapFactory.decodeFile(file, bmOptions)
        view.setImageBitmap(bitmap)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        //val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

        // Save a file: path for use with ACTION_VIEW intents
        fileName = image.absolutePath
        return image
    }

    companion object {
        val TOMAR_FOTO_REQUEST = 1
    }


}

