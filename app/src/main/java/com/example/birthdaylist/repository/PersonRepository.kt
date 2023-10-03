package com.example.birthdaylist.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.birthdaylist.models.Person
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonRepository {
    private val _baseUrl = "https://birthdaysrest.azurewebsites.net/api/"

    private val personListService: PersonListService
    val personLiveData: MutableLiveData<List<Person>> = MutableLiveData<List<Person>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(_baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        personListService = build.create(PersonListService::class.java)
        getPersons()
    }

    fun getPersons() {
        personListService.getAllPersons().enqueue(object : Callback<List<Person>> {
            override fun onResponse(
                call: Call<List<Person>>,
                response: Response<List<Person>>
            ) {
                if (response.isSuccessful) {
                    val p: List<Person>? = response.body()
                    personLiveData.postValue(p!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Person>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun add(person: Person) {
        personListService.addPerson(person).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    val p: Person? = response.body()
                    updateMessageLiveData.postValue("Added person: $p")
                    getPersons()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun remove(id: Int) {
        personListService.deletePerson(id).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    val p: Person? = response.body()
                    updateMessageLiveData.postValue("Deleted person: $p")
                    getPersons()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    fun update(person: Person) {
        personListService.updatePerson(person.id, person).enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    val p: Person? = response.body()
                    updateMessageLiveData.postValue("Updated person: $p")
                    getPersons()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("APPLE", message)
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("APPLE", t.message!!)
            }
        })
    }

    // TODO: Add methods to sort and filter the list
}