package delta.dkt.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import org.junit.Rule;
import org.junit.Test;

import delta.dkt.R;

public class GameViewTest {
    @Rule
    public ActivityScenarioRule<GameViewActivity> activityRule =
            new ActivityScenarioRule<>(GameViewActivity.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withText("General Information")).check(matches(isDisplayed()));
    }

    @Test
    public void testViewSwitch() {
        Intents.init();
        // Perform an action on the button
        onView(ViewMatchers.withId(R.id.button_property_infos)).perform(ViewActions.click());

        // Verify that the activity you expect to be launched has started
        Intents.intended(IntentMatchers.hasComponent(PropertyListActivity.class.getName()));
        Intents.release();
    }
}
