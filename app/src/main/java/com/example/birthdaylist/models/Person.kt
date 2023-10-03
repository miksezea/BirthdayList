package com.example.birthdaylist.models

import com.squareup.moshi.Json
import java.io.Serializable

data class Person(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "userId") val userId: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "birthYear") val birthYear: Int = 0,
    @Json(name = "birthMonth") val birthMonth: Int = 0,
    @Json(name = "birthDayOfMonth") val birthDayOfMonth: Int = 0,
    @Json(name = "pictureUrl") val pictureUrl: String? = null,
    @Json(name = "remarks") val remarks: String? = null,
    @Json(name = "age") val age: Int = 0
) {
    override fun toString(): String {
        return "Person($id $name, Birth date: $birthDayOfMonth/$birthMonth $birthYear Age: $age)"
    }
}
