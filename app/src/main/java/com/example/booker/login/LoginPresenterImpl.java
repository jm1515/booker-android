package com.example.booker.login;

public class LoginPresenterImpl implements LoginContract.LoginPresenter, LoginContract.LoginModel.Callback {

    private LoginContract.LoginView loginView;
    private LoginContract.LoginModel loginModel;

    public LoginPresenterImpl(){}

    public LoginPresenterImpl(LoginContract.LoginModel model) {
        this.loginModel = model;
    }

    @Override
    public void checkDataBeforeLogin(String email, String pwd) {
        if(email.isEmpty()){
            this.loginView.showLoginError(LoginError.EMPTY_MAIL);
            return;
        }
        if (pwd.isEmpty()){
            this.loginView.showLoginError(LoginError.EMPTY_PASSWORD);
            return;
        }

        this.loginModel.login(email, pwd, this);
    }

    @Override
    public void onViewAttach(LoginContract.LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void onViewDetach() {
        this.loginView = null;
    }

    @Override
    public void onDestroy() {
        this.onViewDetach();
    }

    @Override
    public void onRegisterSuccess() {
        this.loginView.showSuccessLogin();
        this.loginView.goMainActivity();
    }

    @Override
    public void onRegisterError(LoginError error) {
        this.loginView.showLoginError(LoginError.LOGIN_FAILED);
    }
}
