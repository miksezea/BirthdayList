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
    val personsLiveData: MutableLiveData<List<Person>> = MutableLiveData<List<Person>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(_baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        personListService = build.create(PersonListService::class.java)
    }

    fun getPersons() {
        personListService.getAllPersons().enqueue(object : Callback<List<Person>> {
            override fun onResponse(call: Call<List<Person>>,
                response: Response<List<Person>>) {
                if (response.isSuccessful) {
                    val p: List<Person>? = response.body()
                    personsLiveData.postValue(p!!)
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

    fun getPersonsByUserId(userId: String) {
        personListService.getPersonsByUserId(userId).enqueue(object : Callback<List<Person>> {
            override fun onResponse(
                call: Call<List<Person>>,
                response: Response<List<Person>>) {
                if (response.isSuccessful) {
                    personsLiveData.postValue(response.body())
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
                    updateMessageLiveData.postValue("Added person: ${response.body()}")

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
                    updateMessageLiveData.postValue("Deleted person: ${response.body()}")

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
                    updateMessageLiveData.postValue("Updated person: ${response.body()}")

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

    fun sortByName() {
        personsLiveData.value = personsLiveData.value?.sortedBy { it.name }
    }

    fun sortByNameDescending() {
        personsLiveData.value = personsLiveData.value?.sortedByDescending { it.name }
    }

    fun sortByAge() {
        personsLiveData.value = personsLiveData.value?.sortedBy { it.age }
    }

    fun sortByAgeDescending() {
        personsLiveData.value = personsLiveData.value?.sortedByDescending { it.age }
    }

    fun sortByBirthday() {
        personsLiveData.value = personsLiveData.value?.sortedWith(compareBy({ it.birthMonth }, { it.birthDayOfMonth }))
    }

    fun sortByBirthdayDescending() {
        personsLiveData.value = personsLiveData.value?.sortedWith(
            compareByDescending<Person>{ it.birthMonth }.thenByDescending{ it.birthDayOfMonth })
    }

    fun filterByName(name: String) {
        personsLiveData.value = personsLiveData.value?.filter { it.name.contains(name.lowercase()) }
    }

    fun filterByAgeBelow(age: Int) {
        personsLiveData.value = personsLiveData.value?.filter { it.age < age }
    }

    fun filterByAgeAbove(age: Int) {
        personsLiveData.value = personsLiveData.value?.filter { it.age > age }
    }
}