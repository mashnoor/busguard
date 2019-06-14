package net.mashnoor.trainticketapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.orhanobut.hawk.Hawk;

import net.mashnoor.trainticketapp.R;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    BootstrapEditText betPhoneNumber, betPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        betPhoneNumber = findViewById(R.id.betPhoneNumber);
        betPassword = findViewById(R.id.betPassword);
        Hawk.init(this).build();
    }

    public void goSignUpPage(View v) {

        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void goLogin(View v) {
        RequestParams params = new RequestParams();
        String number = betPhoneNumber.getText().toString();
        String password = betPassword.getText().toString();
        params.put("number", number);
        params.put("password", password);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading. Please wait...");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(AppUrl.SIGN_IN_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                if (response.equals("failed")) {
                    dialog.dismiss();
                    showToast("Credentials didn't match. Try again!");


                } else {
                    dialog.dismiss();
                    Gson gson = new Gson();
                    User user = gson.fromJson(response, User.class);
                    AppHelper.saveUser(user);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();


                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showToast("Something went wrong");
                dialog.dismiss();

            }
        });
    }

    private void showToast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
