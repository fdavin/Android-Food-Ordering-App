package com.majika.api.branch

data class BranchData(
    val name: String,
    val popular_food: String,
    val address: String,
    val contact_person: String,
    val phone_number: String,
    val longitude: Double,
    val latitude: Double
)