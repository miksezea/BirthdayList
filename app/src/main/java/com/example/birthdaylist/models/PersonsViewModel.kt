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
        val userUid = FirebaseAuth.getInstance().currentUser?.uid
        if (userUid != null) {
            repository.getPersonsByUserId(userUid)
        } else {
            repository.getPersons()
        }
    }

    fun reload() {
        val userUid = FirebaseAuth.getInstance().currentUser?.uid
        if (userUid != null) {
            repository.getPersonsByUserId(userUid)
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
}