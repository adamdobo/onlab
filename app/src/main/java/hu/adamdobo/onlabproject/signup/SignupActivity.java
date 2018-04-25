package hu.adamdobo.onlabproject.signup;

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
import hu.adamdobo.onlabproject.login.LoginActivity;
import hu.adamdobo.onlabproject.model.User;

public class SignupActivity extends AppCompatActivity implements SignupView {

    private EditText emailInput, passwordInput, nickNameInput, addressInput, reEnterPasswordInput;
    private TextInputLayout emailLayout, passwordLayout, nickNameLayout, addressLayout, reEnterPasswordLayout;
    private FrameLayout progressBarHolder;
    private Button signUpBtn;
    private TextView loginLink;
    private SignupPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginLink = findViewById(R.id.link_login);
        signUpBtn = findViewById(R.id.btn_signup);
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        nickNameInput = findViewById(R.id.input_nickname);
        addressInput = findViewById(R.id.input_address);
        reEnterPasswordInput = findViewById(R.id.input_reEnterPassword);
        emailLayout = findViewById(R.id.layout_email);
        passwordLayout = findViewById(R.id.layout_password);
        nickNameLayout = findViewById(R.id.layout_nickname);
        reEnterPasswordLayout = findViewById(R.id.layout_reenter);
        addressLayout = findViewById(R.id.layout_address);
        progressBarHolder = findViewById(R.id.progressBarHolder);
        presenter = new SignupPresenterImpl(this, new SignupInteractorImpl());

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearErrors();
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                signUpUser();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToLogin();
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void signUpUser() {
        presenter.registerUser(new User(emailInput.getText().toString(), addressInput.getText().toString(), nickNameInput.getText().toString()), passwordInput.getText().toString(), reEnterPasswordInput.getText().toString());
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
    public void setEmailEmptyError() {
        emailLayout.setError(getString(R.string.email_error));
    }

    @Override
    public void setPasswordEmptyError() {
       passwordLayout.setError(getString(R.string.password_error));
    }

    @Override
    public void navigateToLogin() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    @Override
    public void setAddressEmptyError() {
        addressLayout.setError(getString(R.string.address_empty));
    }

    @Override
    public void setNickNameEmptyError() {
        nickNameLayout.setError(getString(R.string.nickname_empty));
    }

    @Override
    public void setSignupError() {
        Snackbar.make(getCurrentFocus(), getString(R.string.signup_failed), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPasswordMismatchError() {
        passwordLayout.setError(getString(R.string.passwords_not_match));
        reEnterPasswordLayout.setError(getString(R.string.passwords_not_match));
    }

    @Override
    public void clearErrors() {
        nickNameLayout.setError(null);
        emailLayout.setError(null);
        addressLayout.setError(null);
        passwordLayout.setError(null);
        reEnterPasswordLayout.setError(null);
    }

    @Override
    public void setPasswordTooShortError() {
        passwordLayout.setError(getString(R.string.password_too_short));
    }
}
