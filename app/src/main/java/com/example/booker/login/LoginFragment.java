package com.example.booker.login;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.booker.arch.BaseFragment;
import com.example.booker.LogActivity;
import com.example.booker.R;
import com.example.booker.register.RegisterStep1Fragment;

public class LoginFragment extends BaseFragment implements LoginContract.LoginView {

    private EditText inputMail, inputPwd;
    private LogActivity logActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        logActivity = (LogActivity) getActivity();

        LoginPresenterImpl loginPresenter = new LoginPresenterImpl(new LoginModelImpl());
        loginPresenter.onViewAttach(this);

        Button btnRegister = rootView.findViewById(R.id.buttonGoRegister);
        Button btnLogin = rootView.findViewById(R.id.btnLogin);

        inputMail = rootView.findViewById(R.id.inputMail);
        inputPwd = rootView.findViewById(R.id.inputPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.checkDataBeforeLogin(
                        inputMail.getText().toString(),
                        inputPwd.getText().toString());
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoginError(LoginError error) {
        switch (error){
            case EMPTY_MAIL:
                inputMail.setError(getString(R.string.login_empty_mail));
                break;
            case EMPTY_PASSWORD:
                inputPwd.setError(getString(R.string.login_empty_pwd));
                break;
            case LOGIN_FAILED:
                Toast.makeText(logActivity, R.string.login_failed, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showSuccessLogin() {
        Toast.makeText(logActivity, R.string.login_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void goRegister() {
        LoginFragment.super.goFragment(new RegisterStep1Fragment());
    }

    @Override
    public void goMainActivity() {
        SharedPreferences settings = logActivity.getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Username", inputMail.getText().toString());
        editor.apply();
        logActivity.goToMainActivity();
    }
}