package com.example.birthdaylist.models

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("userId") val userId: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("birthYear") val birthYear: Int = 0,
    @SerializedName("birthMonth") val birthMonth: Int = 0,
    @SerializedName("birthDayOfMonth") val birthDayOfMonth: Int = 0,
    @SerializedName("pictureUrl") val pictureUrl: String? = null,
    @SerializedName("remarks") val remarks: String? = null,
    @SerializedName("age") val age: Int = 0) {
    override fun toString(): String {
        return "Person($id $name, Birth date: $birthDayOfMonth/$birthMonth - $birthYear Age: $age)"
    }
}
