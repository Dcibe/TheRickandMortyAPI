package com.example.therickandmortyapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// La anotación @Entity indica que esta clase representa una entidad en la base de datos
// y se mapeará a una tabla llamada "personajes"
@Entity(tableName = "personajes")
data class PersonajesLocal (
 // La anotación @PrimaryKey indica que el campo "id" es la clave primaria de la tabla
 // y cada registro debe tener un valor único para este campo
 @PrimaryKey val id: Int,

 // Los siguientes campos representan las columnas de la tabla "personajes"
 val name: String,
 val status: String,
 val species: String,
 val type: String?,
 val gender: String,
 val image: String,
 val url: String,
 val created: String
)
