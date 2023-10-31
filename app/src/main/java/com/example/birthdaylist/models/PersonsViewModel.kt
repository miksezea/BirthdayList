package com.example.birthdaylist.models

import androidx.lifecycle.LiveData
// import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.birthdaylist.repository.PersonRepository

class PersonsViewModel : ViewModel() {
    private val repository = PersonRepository()
    val personsLiveData: LiveData<List<Person>> = repository.personLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    // val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPersons()
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

    // TODO: Add sort and filter functions
}