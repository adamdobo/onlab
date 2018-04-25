package hu.adamdobo.onlabproject.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.drawer.DrawerActivity;
import hu.adamdobo.onlabproject.signup.SignupActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private Button loginBtn;
    private TextView signUpLink;
    private EditText emailInput, passwordInput;
    private TextInputLayout emailLayout, passwordLayout;
    private FrameLayout progressBarHolder;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenterImpl(this, new LoginInteractorImpl());

        presenter.checkIfUserIsLoggedIn();

        loginBtn = findViewById(R.id.btn_login);
        signUpLink = findViewById(R.id.link_signup);
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        emailLayout = findViewById(R.id.layout_email);
        passwordLayout = findViewById(R.id.layout_password);
        progressBarHolder = findViewById(R.id.progressBarHolder);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearErrors();
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                loginUser();
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignUp();
            }
        });


    }

    @Override
    public void loginUser() {
        presenter.validateCredentials(emailInput.getText().toString(), passwordInput.getText().toString());
    }

    @Override
    public void showProgress() {
        AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
    }

    @Override
    public void setLoginError() {
        Snackbar.make(getCurrentFocus(), getString(R.string.auth_failed), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void navigateToHome() {
        Intent home = new Intent(this, DrawerActivity.class);
        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(home);
        finish();
    }

    @Override
    public void navigateToSignUp() {
        Intent signup = new Intent(this, SignupActivity.class);
        startActivity(signup);
    }

    @Override
    public void setPasswordEmptyError() {
        passwordLayout.setError(getString(R.string.password_error));
    }

    @Override
    public void setEmailEmptyError() {
        emailLayout.setError(getString(R.string.email_error));
    }

    @Override
    public void clearErrors() {
        emailLayout.setError(null);
        passwordLayout.setError(null);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
