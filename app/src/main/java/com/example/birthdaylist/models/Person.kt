package com.example.birthdaylist.models

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: String?,
    @SerializedName("name") val name: String = "",
    @SerializedName("birthYear") val birthYear: Int = 0,
    @SerializedName("birthMonth") val birthMonth: Int = 0,
    @SerializedName("birthDayOfMonth") val birthDayOfMonth: Int = 0,
    @SerializedName("pictureUrl") val pictureUrl: String? = null,
    @SerializedName("remarks") val remarks: String? = null,
    @SerializedName("age") val age: Int) {

    constructor(userId: String, name: String, birthYear: Int, birthMonth: Int, birthDayOfMonth: Int, remarks: String? = null) :
            this(0, userId, name, birthYear, birthMonth, birthDayOfMonth, null, remarks,0)
    override fun toString(): String {
        return "Born: $birthDayOfMonth/$birthMonth - $birthYear\nAge: $age"
    }
    fun getBirthdayString(): String {
        return "$birthDayOfMonth/$birthMonth - $birthYear"
    }
}
