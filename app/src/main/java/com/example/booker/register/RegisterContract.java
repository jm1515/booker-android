package com.example.booker.register;

import com.example.booker.arch.BasePresenter;
import com.example.booker.arch.BaseView;

public interface RegisterContract {

    interface RegisterView extends BaseView {

        interface RegisterViewStep1 {
            void goToLoginView();
        }

        void showRegisterError(RegisterError error);

    }

    interface RegisterPresenter extends BasePresenter<RegisterView> {
        void checkForStep2(String lastName,
                           String firstName,
                           String email,
                           String phoneNumber);

        void goStep2(String lastName,
                     String firstName,
                     String email,
                     String phoneNumber);

        void goStep1(String lastName,
                     String firstName,
                     String email,
                     String phoneNumber);

        void onStep2Clicked(String email,
                            String lastname,
                            String firstname,
                            String phoneNumber,
                            String password,
                            String confirmPassword);
    }

    interface RegisterModel {
        boolean isMailUsed(String lastName,
                           String firstName,
                           String email,
                           String phoneNumber, Callback callback);

        void registerUser(String lastName,
                          String firstName,
                          String email,
                          String phoneNumber,
                          String password,
                          String confirmPassword,
                          RegisterContract.RegisterModel.Callback callback);

        interface Callback {
            void onRegisterSuccess();
            void onStep1Success(String lastName,
                                String firstName,
                                String email,
                                String phoneNumber);
            void onRegisterError(RegisterError error);
        }

    }

}
