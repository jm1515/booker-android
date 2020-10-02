package com.example.booker.login;

import android.util.Log;

import com.example.booker.data.APIClient;
import com.example.booker.data.APIInterface;
import com.example.booker.data.Users;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public class LoginModelImpl implements LoginContract.LoginModel {

    private static final int CODE_REQUEST = 200;

    @Override
    public void login(String email, String pwd, Callback callback) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        if(email == null || pwd == null){
            callback.onRegisterError(LoginError.LOGIN_FAILED);
            Log.i("LoginModelImpl", "Login failed");
        }
        else {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("email", email);
            jsonObject.addProperty("password", pwd);

            Call<JsonObject> call = apiInterface.getUser(jsonObject);
            call.enqueue(new retrofit2.Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.i("LoginModelImpl", "Server response for doLogin http return code : " + response.code());

                    if (response.code() != CODE_REQUEST) {
                        callback.onRegisterError(LoginError.LOGIN_FAILED);
                        call.cancel();
                        return;
                    }

                    callback.onRegisterSuccess();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.i("LoginModelImpl", "Login failed failed");
                    callback.onRegisterError(LoginError.LOGIN_FAILED);
                    call.cancel();
                }
            });

        }

    }
}
