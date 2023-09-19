package com.example.birthdaylist

data class Person(
    var id: Int = -1,
    var userId: String,
    var name: String,
    var birthYear: Int,
    var birthMonth: Int,
    var birthDayOfMonth: Int,
    var remarks: String? = null,
    var pictureUrl: String? = null,
    var age: Int
) {
}
