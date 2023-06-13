package com.example.mvopo.tsekapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.View;
import android.view.autofill.AutofillManager;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.intent.Intents;

import com.example.mvopo.tsekapp.Helper.DBHelper;
import com.example.mvopo.tsekapp.Model.User;
import com.github.clans.fab.FloatingActionMenu;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InputTextTest {

    private FloatingActionMenu fabMenu;
    @Rule
    public IntentsTestRule<LoginActivity> mActivityRule = new IntentsTestRule<>(LoginActivity.class);
    @Before
    public void removeAutoFill(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Disable autofill services
            InstrumentationRegistry.getInstrumentation().getUiAutomation().executeShellCommand(
                    "settings put secure autofill_service null");
        }
        fabMenu = mActivityRule.getActivity().findViewById(R.id.fabMenu);
    }
    @Test
    public void testAdd(){
        assertEquals(42, Integer.sum(19, 23));
    }
    @Test
    public void view_isCorrect() {
        Espresso.onView(ViewMatchers.withId(R.id.pin_btn)).check(matches(isDisplayed()));
    }
    @Test
    public void login_isCorrect(){
        DBHelper dbHelper = new DBHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
        User user;
        Intents.init();
        try {
            user = dbHelper.getUser();
            if (user != null) {

                Espresso.onView(ViewMatchers.withId(R.id.pin_txt)).perform(typeText("1234"));
                Espresso.onView(ViewMatchers.withId(R.id.pin_btn)).perform(click());
                intended(IntentMatchers.hasComponent(MainActivity.class.getName()));

                // Delay for a short period to allow the transition to complete
                try {
                    Thread.sleep(1000); // Adjust the delay as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Espresso.onView(ViewMatchers.withId(R.id.fabMenu)).check(matches(isDisplayed()));

            }
            else {
                onView(withId(R.id.login_id)).perform(typeText("rtayong_barangay"));
                onView(withId(R.id.login_pass)).perform(typeText("123"));
                onView(withId(R.id.signInBtn)).perform(click());
                onView(withId(R.id.pin_txt)).perform(typeText("1234"));
                onView(withId(R.id.pin_btn)).perform(click());
                Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.click());
                onView(withId(R.id.pin_btn)).check(matches(isCompletelyDisplayed()));
            }
        } catch (Exception e){

        }
        Intents.release();

    }
    @Test
    public void testAgeGroup(){

    }
}
