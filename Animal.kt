package com.example.gramavaxi

data class Animal(
    val id: Int,
    val name: String,
    val species: Species,
    val breed: String,
    val age: String,
    val gender: String,
    val tagId: String,
    val ownerName: String,
    val village: String,
    val campId: Int,
    val vaccineStatus: VaccineStatus,
    val vaccines: List<VaccineRecord>,
    val weight: String,
    val colour: String
)