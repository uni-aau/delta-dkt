package delta.dkt.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class GameViewTest {
    @Rule
    public ActivityScenarioRule<GameViewActivity> activityRule =
            new ActivityScenarioRule<>(GameViewActivity.class);

    @Test
    public void testTextViewGeneralInfo() {
        onView(withText("General Information")).check(matches(isDisplayed()));
    }
}
