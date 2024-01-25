package com.example.nestedrecyclertry

import java.util.UUID

data class AnimalSection(
    //we will need an ID later on
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val animals: List<Animal> = mutableListOf(),
)

data class Animal(
    val name: String,
    val image: String,
)