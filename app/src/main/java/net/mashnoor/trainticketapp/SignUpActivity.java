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

import net.mashnoor.trainticketapp.R;

import cz.msebera.android.httpclient.Header;

public class SignUpActivity extends AppCompatActivity {


    BootstrapEditText betName, betPhoneNumber, betPassword, betAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        betName = findViewById(R.id.betUserName);
        betPhoneNumber = findViewById(R.id.betPhoneNumber);
        betPassword = findViewById(R.id.betPassword);
        betAddress = findViewById(R.id.betAddress);
    }

    public void goSignUp(View v)
    {

        String name = betName.getText().toString();
        String phoneNumber = betPhoneNumber.getText().toString();
        String password = betPassword.getText().toString();
        String address = betAddress.getText().toString();

        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("number", phoneNumber);
        params.put("password", password);
        params.put("address", address);

        final ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Creating account. Please wait...");

        AsyncHttpClient client = new AsyncHttpClient();

        client.post(AppUrl.SIGN_UP_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                dialog.dismiss();
                String response = new String(responseBody);

                if(response.equals("exists"))
                {
                    showToast("User already exists!");
                }
                else
                {
                    Gson gson = new Gson();

                    User user = gson.fromJson(response, User.class);
                    AppHelper.saveUser(user);
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    finish();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showToast("Something went wrong!");
                dialog.dismiss();

            }
        });

    }

    private void showToast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
