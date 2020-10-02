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
import com.example.booker.R;

import androidx.activity.OnBackPressedCallback;

public class RegisterStep2Fragment extends BaseFragment implements RegisterContract.RegisterView {

    private EditText inputPwd, inputConfirmationPwd;
    private LogActivity activity;
    private RegisterContract.RegisterPresenter presenter;

    public RegisterStep2Fragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register_step2, container, false);

        activity = (LogActivity) getActivity();

        assert activity != null;
        activity.getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                presenter.goStep1(RegisterStep2Fragment.super.getLastName(),
                        RegisterStep2Fragment.super.getFirstName(),
                        RegisterStep2Fragment.super.getEmail(),
                        RegisterStep2Fragment.super.getPhoneNumber());
            }
        });

        presenter = new RegisterPresenterImpl(new RegisterModelImpl(), getParentFragmentManager(),
                activity);
        presenter.onViewAttach(this);

        inputPwd = rootView.findViewById(R.id.inputPwd);
        inputConfirmationPwd = rootView.findViewById(R.id.inputConfirmationPwd);

        ImageButton btnGoStep1 = rootView.findViewById(R.id.btnGoStep1);
        btnGoStep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.goStep1(RegisterStep2Fragment.super.getLastName(),
                        RegisterStep2Fragment.super.getFirstName(),
                        RegisterStep2Fragment.super.getEmail(),
                        RegisterStep2Fragment.super.getPhoneNumber());
            }
        });

        Button btnRegister = rootView.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onStep2Clicked(RegisterStep2Fragment.super.getEmail(),
                        RegisterStep2Fragment.super.getLastName(),
                        RegisterStep2Fragment.super.getFirstName(),
                        RegisterStep2Fragment.super.getPhoneNumber(),
                        inputPwd.getText().toString(),
                        inputConfirmationPwd.getText().toString());
            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showRegisterError(RegisterError error) {
        switch (error) {
            case EMPTY_PASSWORD:
                inputPwd.setError(getString(R.string.register_empty_pwd));
                break;
            case PASSWORD_CONFIRMATION_FAILED:
                inputConfirmationPwd.setError(getString(R.string.register_invalid_pwd));
                break;
            case PASSWORD_CONFIRMATION_EMPTY:
                inputConfirmationPwd.setError(getString(R.string.register_empty_confirmation_pwd));
                break;
            case UNKNOWN:
                Toast.makeText(activity, R.string.register_failed, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}