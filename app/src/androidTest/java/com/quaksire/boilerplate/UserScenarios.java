package com.quaksire.boilerplate;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.quaksire.apprepository.db.AppDatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Julio.
 */

@RunWith(AndroidJUnit4.class)
public class UserScenarios extends BaseScenario {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class, true, false);

    @Test
    public void scenarioViewPupil() throws Exception  {
        setResponse200ToServer(LIST_PUPILS_200, 200);

        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);

        onView(allOf(
                withText("Julio"),
                isDisplayed())).check(matches(withText("Julio")));

        onView(allOf(
                withText("Spain"),
                isDisplayed())).check(matches(withText("Spain")));

        onView(withText("Julio")).perform(click());

        onView(withText("Name")).check(matches(isDisplayed()));
        onView(withText("Country")).check(matches(isDisplayed()));
        onView(withText("Delete pupil")).check(matches(isDisplayed()));

        onView(allOf(
                withId(R.id.name),
                withText("Julio"),
                isDisplayed())).check(matches(withText("Julio")));

        onView(allOf(
                withId(R.id.country),
                withText("Spain"),
                isDisplayed())).check(matches(withText("Spain")));
    }

    @Test
    public void canDeleteUser() throws Exception {
        setResponse200ToServer(LIST_PUPILS_200, 200);

        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);

        onView(allOf(
                withText("Julio"),
                isDisplayed())).check(matches(withText("Julio")));

        onView(allOf(
                withText("Spain"),
                isDisplayed())).check(matches(withText("Spain")));

        onView(withText("Julio")).perform(click());

        onView(withText("Name")).check(matches(isDisplayed()));
        onView(withText("Country")).check(matches(isDisplayed()));
        onView(withText("Delete pupil")).check(matches(isDisplayed()));

        onView(allOf(
                withId(R.id.name),
                withText("Julio"),
                isDisplayed())).check(matches(withText("Julio")));

        onView(allOf(
                withId(R.id.country),
                withText("Spain"),
                isDisplayed())).check(matches(withText("Spain")));

        onView(withText("Delete pupil")).perform(click());

        onView(withText(R.string.delete_pupil)).check(matches(isDisplayed()));
        onView(withText(R.string.delete_pupil_message)).check(matches(isDisplayed()));

        onView(withText(R.string.delete)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel)).check(matches(isDisplayed()));

        //Stack Delete response from the server
        setResponse200ToServer(RESPONSE_EMPTY, 204);
        //Stack new server error message
        setResponse200ToServer(RESPONSE_EMPTY, 503);

        onView(withText(R.string.delete)).perform(click());

        Thread.sleep(5000);

        onView(withId(R.id.pupils)).check(matches(not(isDisplayed())));
        onView(withId(R.id.error)).check(matches(isDisplayed()));
    }

    @Test
    public void canSavePupil() throws Exception {
        setResponse200ToServer(LIST_PUPILS_200, 200);

        Intent intent = new Intent();
        mActivityTestRule.launchActivity(intent);

        onView(allOf(
                withText("Julio"),
                isDisplayed())).check(matches(withText("Julio")));

        onView(allOf(
                withText("Spain"),
                isDisplayed())).check(matches(withText("Spain")));

        onView(withText("Julio")).perform(click());

        onView(withText("Name")).check(matches(isDisplayed()));
        onView(withText("Country")).check(matches(isDisplayed()));
        onView(withText("Delete pupil")).check(matches(isDisplayed()));

        onView(allOf(
                withId(R.id.name),
                withText("Julio"),
                isDisplayed())).check(matches(withText("Julio")));

        onView(allOf(
                withId(R.id.country),
                withText("Spain"),
                isDisplayed())).check(matches(withText("Spain")));

        onView(withId(R.id.name)).perform(replaceText("Manuel"));

        onView(withId(R.id.fab)).perform(click());
        onView(withText("Saved!")).check(matches(isDisplayed()));
    }
}
