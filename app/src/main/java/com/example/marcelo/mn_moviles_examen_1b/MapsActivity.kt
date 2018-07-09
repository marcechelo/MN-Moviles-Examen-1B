package com.example.marcelo.mn_moviles_examen_1b

import android.content.Context
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.util.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



class MapsActivity : AppCompatActivity(),
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    lateinit var aplicaciones: ArrayList<App>
    var idSo = 0
    var context:Context = this
    private lateinit var mMap: GoogleMap

    var arregloMarcadores = ArrayList<Marker>()

    val epnLatLang = LatLng(-0.2103764, -78.4891095)
    //(-0.2103764, -78.4891095)

    val zoom = 15f

    var usuarioTienePermisosLocalizacion = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        solicitarPermisosLocalizacion()

        idSo = intent.getIntExtra("sistemaId",0)
        aplicaciones = BaseDeDatosApp.getAplicaciones(idSo)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        with(googleMap) {

            establecerListeners(googleMap)
            establecerSettings(googleMap)

            //idSo = intent.getIntExtra("sistemaId",0)
            Log.i("mensaje",idSo.toString())
            //var latleng = LatLng(aplicaciones[0].latitud,aplicaciones[0].longitud)
            //aplicaciones = BaseDeDatosApp.getAplicaciones(idSo)
            //anadirMarcador(latleng, aplicaciones[0].nombre)
            //anadirMarcador(epnLatLang,"quito")
            aplicaciones.forEach {
                var latleng = LatLng(it.latitud,it.longitud)
                //latlngs.add(latleng)
                //anadirMarcador(latleng, it.nombre)
                //moverCamaraPorLatLongZoom(latleng, zoom)
                googleMap.addMarker(MarkerOptions().position(latleng).title(it.nombre))
                moverCamaraPorLatLongZoom(latleng, zoom)
            }


            //moverCamaraPorLatLongZoom(latleng, zoom)

        }
    }

    fun solicitarPermisosLocalizacion() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            usuarioTienePermisosLocalizacion = true
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    private fun establecerSettings(googleMap: GoogleMap) {
        with(googleMap) {
            uiSettings.isZoomControlsEnabled = false
            uiSettings.isMyLocationButtonEnabled = true
        }
    }

    private fun anadirMarcador(latitudLongitud: LatLng, titulo: String) {

        arregloMarcadores.forEach { marker: Marker ->
            marker.remove()
        }

        arregloMarcadores = ArrayList<Marker>()

        val marker = mMap.addMarker(
                MarkerOptions()
                        .position(latitudLongitud)
                        .title(titulo)
        )

        arregloMarcadores.add(marker)

        Log.i("google-map", "$arregloMarcadores")
    }


    private fun moverCamaraPorLatLongZoom(latitudLongitud: LatLng, zoom: Float) {


        mMap.moveCamera(
                CameraUpdateFactory
                        .newLatLngZoom(latitudLongitud, zoom)
        )


    }


    private fun moverCamaraPorPosicion(posicionCamara: CameraPosition) {
        mMap.moveCamera(
                CameraUpdateFactory
                        .newCameraPosition(posicionCamara)
        )
    }


    private fun establecerListeners(googleMap: GoogleMap) {
        with(googleMap) {

            setOnCameraIdleListener(this@MapsActivity)
            setOnCameraMoveStartedListener(this@MapsActivity)
            setOnCameraMoveListener(this@MapsActivity)
            setOnCameraMoveCanceledListener(this@MapsActivity)


            setOnPolylineClickListener(this@MapsActivity)
            setOnPolygonClickListener(this@MapsActivity)
        }
    }


    override fun onCameraMoveStarted(p0: Int) {
    }

    override fun onCameraMove() {
    }

    override fun onCameraMoveCanceled() {
    }

    override fun onCameraIdle() {
    }

    override fun onPolylineClick(p0: Polyline?) {
        Log.i("google-map", " Dio click en la ruta $p0")
    }

    override fun onPolygonClick(p0: Polygon?) {
        Log.i("google-map", " Dio click en el poligono $p0")
    }

}
