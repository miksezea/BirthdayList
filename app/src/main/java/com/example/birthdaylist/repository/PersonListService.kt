package com.example.birthdaylist.repository

import com.example.birthdaylist.models.Person
import retrofit2.Call
import retrofit2.http.*

interface PersonListService {
    @GET("Persons")
    fun getAllPersons(): Call<List<Person>>

    @GET("Persons/{id}")
    fun getPerson(@Path("id") id: Int): Call<Person>

    @POST("Persons")
    fun addPerson(@Body person: Person): Call<Person>

    @PUT("Persons/{id}")
    fun updatePerson(@Path("id") id: Int, @Body person: Person): Call<Person>

    @DELETE("Persons/{id}")
    fun deletePerson(@Path("id") id: Int): Call<Person>
}