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
        // Check that the login screen is displayed
        onView(withId(R.id.textview_register)).check(matches(isDisplayed()))

        // Enter email and password
        onView(withId(R.id.edittext_email)).perform(typeText("mikkel.eilskov@icloud.com"))
        onView(withId(R.id.edittext_password)).perform(typeText("bibliotek"))

        // Click the login button
        onView(withId(R.id.button_login)).perform(click())

        // Wait for the FriendsFragment to be displayed
        Thread.sleep(2000)

        // Check that the FriendsFragment is displayed
        onView(withId(R.id.textview_hello_user)).check(matches(isDisplayed()))

        // Check that the textview contains email
        onView(withId(R.id.textview_hello_user)).check(matches(withText("Hello mikkel.eilskov@icloud.com")))
    }
}