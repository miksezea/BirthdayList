package com.example.birthdaylist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.assertion.ViewAssertions.matches

@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loginToApp() {
        onView(withId(R.id.edittext_email)).perform(typeText("mikkel.eilskov@icloud.com"))
        onView(withId(R.id.edittext_password)).perform(typeText("bibliotek"))

        onView(withId(R.id.button_login)).perform(click())

        Thread.sleep(2000)

        onView(withId(R.id.textview_hello_user)).check(matches(isDisplayed()))
    }
}