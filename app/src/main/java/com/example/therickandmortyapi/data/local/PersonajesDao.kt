package com.example.therickandmortyapi.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PersonajesDao {
    // Método de consulta para obtener todos los registros de la tabla "personajes"
    // Devuelve una lista de objetos PersonajesLocal
    @Query("SELECT * FROM personajes")
    fun getAll() : List<PersonajesLocal>
    // Método de consulta para obtener un registro de la tabla "personajes" por nombre
    // El parámetro ":name" es el nombre que se desea buscar en la tabla
    // Se utiliza "LIMIT 1" para obtener solo un registro
    @Query("SELECT * FROM personajes WHERE name =:name LIMIT 1")
    fun getByName(name: String) : PersonajesLocal
    // Permite insertar uno o más objetos PersonajesLocal
    // La estrategia "onConflict = OnConflictStrategy.REPLACE" indica que si hay conflictos con registros existentes,
    // se deben reemplazar con los nuevos objetos proporcionados
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg Personajes : PersonajesLocal)
        // Se elimina un objeto PersonajesLocal especificado
    @Delete
    fun delete(Personajes: PersonajesLocal)
    //Este método utilizará la cláusula LIKE en la consulta SQL para buscar registros que contengan el nombre buscado.
    @Query("SELECT * FROM personajes WHERE name LIKE '%' || :name || '%'")
    fun searchByName(name: String): List<PersonajesLocal>
}