package hu.adamdobo.onlabproject;

import android.support.design.widget.TextInputLayout;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import hu.adamdobo.onlabproject.login.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Ádám on 5/9/2018.
 */


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void testSetEmailEmptyError(){
        TextInputLayout inputLayout = (TextInputLayout)loginActivityActivityTestRule.getActivity().findViewById(R.id.layout_email);
        onView(withId(R.id.btn_login)).perform(click());
        boolean isItTheSame = inputLayout.getError().toString().equals(loginActivityActivityTestRule.getActivity().getString(R.string.email_error));
        assertEquals(isItTheSame, true);
    }

    @Test
    public void testSetPasswordEmptyError(){
        TextInputLayout inputLayout = (TextInputLayout)loginActivityActivityTestRule.getActivity().findViewById(R.id.layout_password);
        onView(withId(R.id.btn_login)).perform(click());
        boolean isItTheSame = inputLayout.getError().toString().equals(loginActivityActivityTestRule.getActivity().getString(R.string.password_error));
        assertEquals(isItTheSame, true);
    }

}
