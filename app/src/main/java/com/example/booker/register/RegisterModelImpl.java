package com.example.booker.register;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;

import com.example.booker.data.APIClient;
import com.example.booker.data.APIInterface;
import com.example.booker.data.Users;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class RegisterModelImpl implements RegisterContract.RegisterModel {

    private static final int CODE_REQUEST = 200;

    @Override
    public boolean isMailUsed(String lastName, String firstName, String email, String phoneNumber, Callback callback) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        if(email == null ){
            callback.onRegisterError(RegisterError.UNKNOWN);
        }
        else {
            Log.i("RegisterModelImpl", "Check user mail : " + email);

            Call<JsonPrimitive> call = apiInterface.getUserExist(email);
            call.enqueue(new retrofit2.Callback<JsonPrimitive>() {
                @Override
                public void onResponse(Call<JsonPrimitive> call, Response<JsonPrimitive> response) {
                    Log.i("RegisterModelImpl", "Server response for checkUserMail, http return code : " + response.code());

                    if(response.code() != CODE_REQUEST){
                        callback.onRegisterError(RegisterError.EMAIL_ALREADY_EXIST);
                        call.cancel();
                        return;
                    }
                    callback.onStep1Success(lastName, firstName, email, phoneNumber);
                }

                @Override
                public void onFailure(Call<JsonPrimitive> call, Throwable t) {
                    callback.onRegisterError(RegisterError.EMAIL_ALREADY_EXIST);
                    call.cancel();
                }
            });
        }
        return true;
    }

    @Override
    public void registerUser(String lastName, String firstName, String email, String phoneNumber,
                             String password, String confirmPassword, Callback callback) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        if(lastName == null || firstName == null || email == null || phoneNumber == null || password == null){
            callback.onRegisterError(RegisterError.UNKNOWN);
        }
        else {
            Users user = new Users(lastName, firstName, email, password, phoneNumber);
            Log.i("RegisterModelImpl", "Creating user : " + user.toString());

            Call<Users> call = apiInterface.createUser(user);
            call.enqueue(new retrofit2.Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    Log.i("RegisterModelImpl", "Server response for createUser, http return code : " + response.code());

                    if(response.code() != CODE_REQUEST){
                        callback.onRegisterError(RegisterError.UNKNOWN);
                        call.cancel();
                        return;
                    }
                    callback.onRegisterSuccess();
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    callback.onRegisterError(RegisterError.UNKNOWN);
                    call.cancel();
                }
            });
        }

    }
}
