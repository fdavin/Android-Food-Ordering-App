package com.majika.api.branch


data class BranchData(
    val name: String,
    val popular_food: String,
    val address: String,
    val contact_person: String,
    val phone: String,
    val longitude: Long,
    val latitude: Long
)

data class BranchResponse(val data: ArrayList<BranchData>, val size: Int)