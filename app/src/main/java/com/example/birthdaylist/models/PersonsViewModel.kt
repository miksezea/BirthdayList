package com.example.birthdaylist.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.birthdaylist.repository.PersonRepository
import com.google.firebase.auth.FirebaseAuth

class PersonsViewModel : ViewModel() {
    private val repository = PersonRepository()
    val personsLiveData: LiveData<List<Person>> = repository.personsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    //val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        if (userEmail != null) {
            repository.getPersonsByUserId(userEmail)
        } else {
            repository.getPersons()
        }
    }

    fun reload() {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        if (userEmail != null) {
            repository.getPersonsByUserId(userEmail)
        } else {
            repository.getPersons()
        }
    }

    operator fun get(index: Int): Person? {
        return personsLiveData.value?.get(index)
    }

    fun add(person: Person) {
        repository.add(person)
    }

    fun delete(id: Int) {
        repository.remove(id)
    }

    fun update(person: Person) {
        repository.update(person)
    }

    fun sortByName() {
        repository.sortByName()
    }

    fun sortByNameDescending() {
        repository.sortByNameDescending()
    }

    fun sortByAge() {
        repository.sortByAge()
    }

    fun sortByAgeDescending() {
        repository.sortByAgeDescending()
    }

    fun sortByBirthday() {
        repository.sortByBirthday()
    }

    fun sortByBirthdayDescending() {
        repository.sortByBirthdayDescending()
    }

    fun filterByName(name: String) {
        repository.filterByName(name)
    }

    fun filterByAgeBelow(age: Int) {
        repository.filterByAgeBelow(age)
    }

    fun filterByAgeAbove(age: Int) {
        repository.filterByAgeAbove(age)
    }
}