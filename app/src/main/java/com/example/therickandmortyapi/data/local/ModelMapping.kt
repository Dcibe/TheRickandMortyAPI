package com.example.therickandmortyapi.data.local

import com.example.therickandmortyapi.model.Personajes_model

// Función de extensión para convertir un objeto de tipo Personajes_model a PersonajesLocal
fun Personajes_model.toLocal() = PersonajesLocal(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image,
    url = url,
    created = created
)

// Función de extensión para convertir una lista de objetos de tipo Personajes_model a una lista de PersonajesLocal
fun List<Personajes_model>.toLocal() = map(Personajes_model::toLocal)

// Función de extensión para convertir un objeto de tipo PersonajesLocal a Personajes_model
fun PersonajesLocal.toExternal() = Personajes_model(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    image = image,
    url = url,
    created = created
)

// Función de extensión para convertir una lista de objetos de tipo PersonajesLocal a una lista de Personajes_model
fun List<PersonajesLocal>.toExternal() = map(PersonajesLocal::toExternal)
