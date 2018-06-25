package com.example.marcelo.mn_moviles_examen_1b

import java.util.*

class SistemaOperativo(var nombreSo: String,
                       var versionApi: Int,
                       var fechaLanzamiento: String,
                       var pesoGigasSo: Double,
                       var instalado: Int,
                       var createdAt: Long,
                       var updatedAt: Long,
                       var id: Int,
                       var aplicaciones: List<Aplicaciones>) {
    var createdAtDate = Date(updatedAt)
    var updatedAtDate = Date(createdAt)


}