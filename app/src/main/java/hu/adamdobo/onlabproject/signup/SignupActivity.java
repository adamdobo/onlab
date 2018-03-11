package hu.adamdobo.onlabproject.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.login.LoginActivity;

public class SignupActivity extends AppCompatActivity implements SignupView {

    private EditText emailInput, passwordInput, nickNameInput, addressInput, nameInput, reEnterPasswordInput;
    private ProgressBar progressBar;
    private Button signUpBtn;
    private TextView loginLink;
    private SignupPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loginLink = findViewById(R.id.link_login);
        signUpBtn = findViewById(R.id.btn_signup);
        nameInput = findViewById(R.id.input_name);
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        nickNameInput = findViewById(R.id.input_nickname);
        addressInput = findViewById(R.id.input_address);
        reEnterPasswordInput = findViewById(R.id.input_reEnterPassword);
        progressBar = findViewById(R.id.progressBar);
        presenter = new SignupPresenterImpl(this, new SignupInteractorImpl());

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    public void signUpUser() {
        presenter.registerUser(emailInput.getText().toString(), passwordInput.getText().toString());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setEmailEmptyError() {
        emailInput.setError(getString(R.string.email_error));
    }

    @Override
    public void setPasswordEmptyError() {
        passwordInput.setError(getString(R.string.password_error));
    }

    @Override
    public void setSignupError() {
        //TODO: sign up validation
    }

    @Override
    public void navigateToLogin() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }
}
