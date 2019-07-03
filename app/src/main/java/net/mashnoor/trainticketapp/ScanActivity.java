package net.mashnoor.trainticketapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class ScanActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sendData(result.getText());
                    }
                });
            }
        });

        mCodeScanner.startPreview();


    }

    private void sendData(String s)
    {
        Gson g = new Gson();
        Bus bus = g.fromJson(s, Bus.class);

        RequestParams params = new RequestParams();
        params.put("userid", AppHelper.getUser().getId());
        params.put("busname", bus.getBusname());
        params.put("licenseno", bus.getLicenseno());
        params.put("company", bus.getCompany());
        params.put("route", bus.getRoute());
        params.put("lat", 0);
        params.put("lon", 0);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Sending Report. Please wait...");


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(AppUrl.SEND_REPORT, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dialog.dismiss();
                Toast.makeText(ScanActivity.this, "Sent report successfully", Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dialog.dismiss();
                Toast.makeText(ScanActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                finish();

            }
        });

    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
