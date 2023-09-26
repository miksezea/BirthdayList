package models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonsViewModel : ViewModel() {
    //private var _restUrl = "https://birthdaysrest.azurewebsites.net/api/persons"
    private var _nextId = 1
    private var _personsList = mutableListOf(
        Person(
            _nextId++,
            "mikkel.eilskov@gmail.com", "Mikkel",
            2000, 12, 7,
            null, null, 22
        ),
        Person(_nextId++, "mikkel.eilskov@gmail.com", "Kurt",
            2002, 10, 13,
            null, null, 20
        ),
    )

    private var _persons = MutableLiveData<List<Person>>(_personsList)
    val selected = MutableLiveData<Person>()
    var persons: LiveData<List<Person>> = _persons

    fun add(person: Person) {
        person.id = _nextId++
        _personsList.add(person)
        _persons.value = _personsList
    }

    fun remove(id: Int) {
        _personsList.removeAll {p -> p.id == id}
    }

    fun update(id: Int, info: Person) {
        val person: Person = _personsList.first { p -> p.id == id}
        person.userId = info.userId
        person.name = info.name
        person.birthYear = info.birthYear
        person.birthMonth = info.birthMonth
        person.birthDayOfMonth = info.birthDayOfMonth
        person.age = info.age
    }

    operator fun get(position: Int): Person {
        return _personsList[position]
    }
}