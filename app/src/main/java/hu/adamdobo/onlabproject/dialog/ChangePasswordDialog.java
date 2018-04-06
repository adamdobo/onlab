package hu.adamdobo.onlabproject.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hu.adamdobo.onlabproject.R;
import hu.adamdobo.onlabproject.profile.ChangePasswordCallbackListener;

/**
 * Created by Ádám on 4/5/2018.
 */

public class ChangePasswordDialog extends ChangeCredentialsDialog {

    private static ChangePasswordCallbackListener listener;
    public static final String TAG = "ChangePasswordDialog";
    private EditText oldPasswordEditText, newPasswordEditText, reEnterPasswordEditText;
    private TextInputLayout oldPasswordLayout, newPasswordLayout, reEnterPasswordLayout;

    public static ChangePasswordDialog newInstance(ChangePasswordCallbackListener changePasswordCallbackListener){
        listener = changePasswordCallbackListener;
        return new ChangePasswordDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getContext())
                .setTitle("Change password")
                .setView(getContentView())
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    clearErrors();
                    Boolean wantToCloseDialog = false;
                    if(noEmptyFields()){
                        if (passwordMatches()) {
                            wantToCloseDialog = true;
                            listener.changePassword(oldPasswordEditText.getText().toString(), newPasswordEditText.getText().toString());
                        } else {
                            newPasswordLayout.setError(getString(R.string.passwords_not_match));
                            reEnterPasswordLayout.setError(getString(R.string.passwords_not_match));
                        }
                    }
                    if(wantToCloseDialog) {
                        dismiss();
                    }
                }
            });
        }
    }


    @Override
    protected boolean noEmptyFields() {
        if(TextUtils.isEmpty(newPasswordEditText.getText()) || TextUtils.isEmpty(reEnterPasswordEditText.getText()) || TextUtils.isEmpty(oldPasswordEditText.getText())){
            setEmptyErrors();
            return false;
        }
        return true;
    }

    @Override
    protected void clearErrors() {
        oldPasswordLayout.setError(null);
        newPasswordLayout.setError(null);
        reEnterPasswordLayout.setError(null);
    }

    @Override
    protected void setEmptyErrors() {
        if(TextUtils.isEmpty(newPasswordEditText.getText())){
            newPasswordLayout.setError(getString(R.string.fill_out));
        }
        if(TextUtils.isEmpty(oldPasswordEditText.getText())){
            oldPasswordLayout.setError(getString(R.string.fill_out));
        }
        if(TextUtils.isEmpty(reEnterPasswordEditText.getText())){
            reEnterPasswordLayout.setError(getString(R.string.fill_out));
        }
    }

    private boolean passwordMatches() {
        if(newPasswordEditText.getText().toString().equals(reEnterPasswordEditText.getText().toString())){
            return true;
        }
        return false;
    }

    public View getContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.change_password_dialog, null);
        oldPasswordEditText = contentView.findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = contentView.findViewById(R.id.newPasswordEditText);
        reEnterPasswordEditText = contentView.findViewById(R.id.reEnterPasswordEditText);
        oldPasswordLayout = contentView.findViewById(R.id.oldPassword);
        newPasswordLayout = contentView.findViewById(R.id.newPassword);
        reEnterPasswordLayout = contentView.findViewById(R.id.reEnterPassword);
        return contentView;
    }
}
