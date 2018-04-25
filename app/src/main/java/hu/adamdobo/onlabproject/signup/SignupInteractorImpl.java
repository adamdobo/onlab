package hu.adamdobo.onlabproject.signup;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hu.adamdobo.onlabproject.model.User;

/**
 * Created by Ádám on 3/11/2018.
 */

public class SignupInteractorImpl implements SignupInteractor {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseDatabase db = FirebaseDatabase.getInstance();


    @Override
    public void signup(final User user, String password, String passwordAgain, final OnSignupFinishedListener listener) {
        int errors = 0;
        if (TextUtils.isEmpty(user.email)) {
            listener.onEmailEmptyError();
            errors++;
        }
        if (TextUtils.isEmpty(password)) {
            listener.onPasswordEmptyError();
            errors++;
        }
        if(TextUtils.isEmpty(user.address)){
            listener.onAddressEmptyError();
            errors++;
        }
        if(TextUtils.isEmpty(user.nickname)){
            listener.onNickNameEmptyError();
            errors++;
        }

        if(password.length() < 6){
            listener.onPasswordTooShort();
            errors++;
        }

        if(!password.equals(passwordAgain)){
            listener.onPasswordsMismatchError();
            errors++;
        }
        if(errors>0){
            return;
        }

        auth.createUserWithEmailAndPassword(user.email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    listener.onSignupError();
                } else {
                    saveUserToFirebase(user);
                    updateUserProfile(user);
                    auth.signOut();
                    listener.onSuccess();
                }
            }
        });
    }

    @Override
    public void saveUserToFirebase(User user) {
        DatabaseReference usersRef = db.getReference().child("users");
        usersRef.child(auth.getCurrentUser().getUid()).setValue(user);
    }

    @Override
    public void updateUserProfile(User user) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.nickname)
                .build();
        firebaseUser.updateProfile(profileChangeRequest);
    }
}
