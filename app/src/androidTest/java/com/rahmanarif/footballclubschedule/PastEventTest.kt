package com.rahmanarif.footballclubschedule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.rahmanarif.footballclubschedule.main.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PastEventTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        val recyclerView = onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.nextEventMatch)))
        recyclerView.check(ViewAssertions.matches(isDisplayed()))

        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        recyclerView.perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))

        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        pressBack()
    }

    @Test
    fun testAppBehaviour() {
        val recyclerView = onView(CoreMatchers.allOf(isDisplayed(), withId(R.id.nextEventMatch)))
        recyclerView.check(ViewAssertions.matches(isDisplayed()))

        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        recyclerView.perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withId(R.id.add_to_favorite))
                .check(matches(isDisplayed()))

        onView(withId(R.id.add_to_favorite)).perform(click())

        pressBack()

        onView(withId(R.id.bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(R.id.favorite)).perform(click())

    }
}