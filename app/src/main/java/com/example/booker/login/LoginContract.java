package com.example.booker.login;

import com.example.booker.arch.BasePresenter;
import com.example.booker.arch.BaseView;

public interface LoginContract {

    interface LoginView extends BaseView{
        void showLoginError(LoginError error);
        void showProgress();
        void hideProgress();
        void showSuccessLogin();
        void goRegister();
        void goMainActivity();
    }

    interface LoginPresenter extends BasePresenter<LoginView>{
        void checkDataBeforeLogin(String email, String pwd);
    }

    interface LoginModel {
        void login(String email, String pwd, Callback callback);

        interface Callback{
            void onRegisterSuccess();
            void onRegisterError(LoginError error);
        }
    }

}
