package kmitl.project.bosstanayot.runranrun;
import android.support.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import kmitl.project.bosstanayot.runranrun.Control.RunActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by barjord on 12/3/2017 AD.
 */

public class RunTest {
    @Rule
    public ActivityTestRule<RunActivity> mainActivityTestRule = new ActivityTestRule<>(RunActivity.class);

    @Test
    public void textTest(){
        onView(withId(R.id.namestep)).check(matches(isDisplayed()));
        onView(withId(R.id.stepnum)).check(matches(isDisplayed()));
        onView(withId(R.id.distance)).check(matches(isDisplayed()));
        onView(withId(R.id.disnum)).check(matches(isDisplayed()));
        onView(withId(R.id.km)).check(matches(isDisplayed()));
        onView(withId(R.id.Time)).check(matches(isDisplayed()));
        onView(withId(R.id.timenum)).check(matches(isDisplayed()));
        onView(withId(R.id.pausebutton)).check(matches(isDisplayed()));
        onView(withId(R.id.spm)).check(matches(isDisplayed()));
        onView(withId(R.id.steppermin)).check(matches(isDisplayed()));
        onView(withId(R.id.speedtext)).check(matches(isDisplayed()));
        onView(withId(R.id.speed)).check(matches(isDisplayed()));
        onView(withId(R.id.kmperhour)).check(matches(isDisplayed()));
    }
    @Test
    public void buttonTest(){
        onView(withId(R.id.pausebutton)).perform(click());
        onView(withId(R.id.pausebutton)).check(matches(withText("Unpause")));
        onView(withId(R.id.cancel)).check(matches(isDisplayed()));
    }
}
