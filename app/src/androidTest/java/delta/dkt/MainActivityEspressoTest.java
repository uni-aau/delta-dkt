package delta.dkt;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import androidx.test.core.app.ActivityScenario;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;

import delta.dkt.activities.MainActivity;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {


  @Before
    public void setup(){
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testCheckIfViewIsDisplayed(){
        onView(withId(R.id.main_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testCheckEnterButtonWithUsername(){
        onView(withId(R.id.username_edittext)).perform(typeText("Angelo"));
        onView(withId(R.id.enter_btn)).perform(click());
        onView(withId(R.id.main_menu_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testCheckEnterButtonWithoutUsername(){
        onView(withId(R.id.username_edittext)).perform(typeText(""));
        onView(withId(R.id.enter_btn)).perform(click());
        onView(withId(R.id.main_view)).check(matches(isDisplayed()));
    }



}
