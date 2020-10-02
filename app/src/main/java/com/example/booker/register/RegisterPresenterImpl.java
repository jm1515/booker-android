package com.example.booker.register;

import android.util.Patterns;

import com.example.booker.LogActivity;
import com.example.booker.R;

import androidx.fragment.app.FragmentManager;

public class RegisterPresenterImpl implements RegisterContract.RegisterPresenter,
        RegisterContract.RegisterModel.Callback {

    private RegisterContract.RegisterView view;
    private LogActivity logActivity;
    private FragmentManager fragmentManager;
    private RegisterContract.RegisterModel model;

    public RegisterPresenterImpl(RegisterContract.RegisterModel model, FragmentManager fragmentManager) {
        this.model = model;
        this.fragmentManager = fragmentManager;
    }

    public RegisterPresenterImpl(RegisterContract.RegisterModel model, FragmentManager fragmentManager,
                                 LogActivity logActivity) {
        this.model = model;
        this.fragmentManager = fragmentManager;
        this.logActivity = logActivity;
    }

    @Override
    public void onViewAttach(RegisterContract.RegisterView registerView) {
        this.view = registerView;
    }

    @Override
    public void onViewDetach() {
        view = null;
    }

    @Override
    public void onDestroy() {
        onViewDetach();
    }

    @Override
    public void checkForStep2(String lastName, String firstName, String email, String phoneNumber) {
        if (lastName.isEmpty()) {
            view.showRegisterError(RegisterError.EMPTY_LASTNAME);
            return;
        }
        if (firstName.isEmpty()) {
            view.showRegisterError(RegisterError.EMPTY_FIRSTNAME);
            return;
        }
        if (email.isEmpty()) {
            view.showRegisterError(RegisterError.EMPTY_EMAIL);
            return;
        }
        if (!isValidEmail(email)) {
            view.showRegisterError(RegisterError.INVALID_EMAIL);
            return;
        }
        if (!isValidPhone(phoneNumber)) {
            view.showRegisterError(RegisterError.INVALID_PHONE);
            return;
        }

        model.isMailUsed(lastName, firstName, email, phoneNumber, this);

    }

    @Override
    public void goStep2(String lastName, String firstName, String email, String phoneNumber) {
        RegisterStep2Fragment step2 = new RegisterStep2Fragment();
        step2.setData(email, firstName, lastName, phoneNumber);
        this.fragmentManager.beginTransaction().replace(R.id.placeholder, step2).commit();
    }

    @Override
    public void goStep1(String lastName, String firstName, String email, String phoneNumber) {
        RegisterStep1Fragment step1 = new RegisterStep1Fragment();
        step1.setData(email, firstName, lastName, phoneNumber);
        this.fragmentManager.beginTransaction().replace(R.id.placeholder, step1).commit();
    }


    public boolean isValidEmail(CharSequence target) {
        return !target.equals("") && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean isValidPhone(CharSequence target) {
        return !target.equals("") && Patterns.PHONE.matcher(target).matches();
    }

    @Override
    public void onStep2Clicked(String email, String lastname, String firstname, String phoneNumber, String password, String confirmPassword) {
        if(password.isEmpty()){
            view.showRegisterError(RegisterError.EMPTY_PASSWORD);
            return;
        }
        if(confirmPassword.isEmpty()){
            view.showRegisterError(RegisterError.PASSWORD_CONFIRMATION_EMPTY);
            return;
        }
        if (!password.equals(confirmPassword)){
            view.showRegisterError(RegisterError.PASSWORD_CONFIRMATION_FAILED);
            return;
        }
        model.registerUser(lastname, firstname, email, phoneNumber, password, confirmPassword, this);
    }

    @Override
    public void onRegisterSuccess() {
        if(view != null){
            logActivity.goToMainActivity();
        }
    }

    @Override
    public void onStep1Success(String lastName, String firstName, String email, String phoneNumber) {
        if(view != null){
            goStep2(lastName, firstName, email, phoneNumber);
        }
    }

    @Override
    public void onRegisterError(RegisterError error) {
        if(view != null){
            view.showRegisterError(error);
        }
    }
}
