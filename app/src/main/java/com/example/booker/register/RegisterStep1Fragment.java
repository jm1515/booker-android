package com.example.booker.register;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.booker.arch.BaseFragment;
import com.example.booker.LogActivity;
import com.example.booker.login.LoginFragment;
import com.example.booker.R;

import androidx.activity.OnBackPressedCallback;

public class RegisterStep1Fragment extends BaseFragment implements RegisterContract.RegisterView,
        RegisterContract.RegisterView.RegisterViewStep1{

    private RegisterContract.RegisterPresenter presenter;
    private EditText inputLastName, inputFirstName, inputEmail, inputPhoneNumber;

    public RegisterStep1Fragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_step1, container, false);

        presenter = new RegisterPresenterImpl(new RegisterModelImpl(), getParentFragmentManager());
        presenter.onViewAttach(this);

        LogActivity activity = (LogActivity) getActivity();

        assert activity != null;
        activity.getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goToLoginView();
            }
        });

        inputFirstName = rootView.findViewById(R.id.inputFirstName);
        inputLastName = rootView.findViewById(R.id.inputLastName);
        inputEmail = rootView.findViewById(R.id.inputEmail);
        inputPhoneNumber = rootView.findViewById(R.id.inputPhoneNumber);

        ImageButton btnGoLogin = rootView.findViewById(R.id.btnGoLogin);
        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginView();
            }
        });

        Button btnNext = rootView.findViewById(R.id.btnNextStep);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputLastName.hasFocus()) {
                    inputFirstName.requestFocus();
                    return;
                }
                if (inputFirstName.hasFocus()) {
                    inputEmail.requestFocus();
                    return;
                }
                if (inputEmail.hasFocus()) {
                    inputPhoneNumber.requestFocus();
                    return;
                }

                presenter.checkForStep2(inputLastName.getText().toString(),
                        inputFirstName.getText().toString(),
                        inputEmail.getText().toString(),
                        inputPhoneNumber.getText().toString());
            }
        });

        if (super.getFirstName() != null){
            this.inputFirstName.setText(super.getFirstName());
        }
        if (super.getLastName() != null){
            this.inputLastName.setText(super.getLastName());
        }
        if (super.getPhoneNumber() != null){
            this.inputPhoneNumber.setText(super.getPhoneNumber());
        }
        if (super.getEmail() != null){
            this.inputEmail.setText(super.getEmail());
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void goToLoginView() {
        RegisterStep1Fragment.super.goFragment(new LoginFragment());
    }

    @Override
    public void showRegisterError(RegisterError error) {
        switch (error) {
            case EMPTY_EMAIL:
                inputEmail.setError(getString(R.string.register_empty_field));
                break;
            case EMPTY_LASTNAME:
                inputLastName.setError(getString(R.string.register_empty_field));
                break;
            case EMPTY_FIRSTNAME:
                inputFirstName.setError(getString(R.string.register_empty_field));
                break;
            case EMPTY_PHONE:
                inputPhoneNumber.setError(getString(R.string.register_empty_field));
                break;
            case INVALID_EMAIL:
                inputEmail.setError(getString(R.string.register_invalid_email));
                Toast.makeText(getActivity(), getString(R.string.register_invalid_email), Toast.LENGTH_LONG).show();
                break;
            case INVALID_PHONE:
                inputPhoneNumber.setError(getString(R.string.register_invalid_phone));
                Toast.makeText(getActivity(), getString(R.string.register_invalid_phone), Toast.LENGTH_LONG).show();
                break;
            case EMAIL_ALREADY_EXIST:
                inputEmail.setError(getString(R.string.register_email_already_exist));
                Toast.makeText(getActivity(), getString(R.string.register_email_already_exist), Toast.LENGTH_LONG).show();
                break;
        }
    }
}