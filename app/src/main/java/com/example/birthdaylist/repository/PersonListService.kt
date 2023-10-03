package com.example.birthdaylist.repository

import com.example.birthdaylist.models.Person
import retrofit2.Call
import retrofit2.http.*

interface PersonListService {
    @GET("persons")
    fun getAllPersons(): Call<List<Person>>

    @GET("persons/{id}")
    fun getPerson(@Path("id") id: Int): Call<Person>

    @POST("persons")
    fun addPerson(@Body person: Person): Call<Person>

    @PUT("persons/{id}")
    fun updatePerson(@Path("id") id: Int, @Body person: Person): Call<Person>

    @DELETE("persons/{id}")
    fun deletePerson(@Path("id") id: Int): Call<Person>
}