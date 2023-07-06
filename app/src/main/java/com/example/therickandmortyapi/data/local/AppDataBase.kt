package com.example.therickandmortyapi.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Database(entities = [PersonajesLocal::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun PersonajesDao(): PersonajesDao

    companion object {
        @Volatile // Garantiza lectura o escritura desde múltiples hilos de ejecución

        private var _instance: AppDataBase? = null

        // Método para obtener una instancia de la base de datos
        fun getInstance(context: Context): AppDataBase = _instance ?: synchronized(this) {
            _instance ?: buildDatabase(context)
        }

        // Método para construir la instancia de la base de datos
        private fun buildDatabase(context: Context): AppDataBase =
            Room.databaseBuilder(context, AppDataBase::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()

        // Método para limpiar todas las tablas de la base de datos de forma asíncrona
        suspend fun clean(context: Context) = coroutineScope {
            launch(Dispatchers.IO) {
                getInstance(context).clearAllTables()
            }
        }
    }
}
