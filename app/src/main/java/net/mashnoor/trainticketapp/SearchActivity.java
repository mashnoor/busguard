package net.mashnoor.trainticketapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    BootstrapEditText betFromStation, betToStation, betJorneyDate;

    RecyclerView rvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        betFromStation = findViewById(R.id.betFromStation);
        betToStation = findViewById(R.id.betToStation);
        betJorneyDate = findViewById(R.id.betJournetDate);
        rvPosts = findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

    }

    public void goSearch(View v)
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading. Please wait...");

        String fromStation = betFromStation.getText().toString();
        String toStation = betToStation.getText().toString();
        String journeyDate = betJorneyDate.getText().toString();

        RequestParams params = new RequestParams();
        params.put("journeydate", journeyDate);
        params.put("fromplace", fromStation);
        params.put("toplace", toStation);

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(AppUrl.SEARCH_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Gson g = new Gson();
                final Post[] posts = g.fromJson(response, Post[].class);

                PostsAdapter adapter = new PostsAdapter(Arrays.asList(posts));
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                        Button b = (Button) view;
                        if (b.getText().toString().equals("Delete")) {
                            final ProgressDialog dialog1 = new ProgressDialog(SearchActivity.this);
                            dialog1.setMessage("Deleting. Please wait...");
                            AsyncHttpClient clnt = new AsyncHttpClient();
                            clnt.get(AppUrl.getPostDeleteUrl(posts[position].getId()), new AsyncHttpResponseHandler() {
                                @Override
                                public void onStart() {
                                    super.onStart();
                                    dialog1.show();

                                }

                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                    showToast("Deleted Successfully!");
                                    dialog1.dismiss();
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);

                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                    showToast("Something went wrong!");
                                    dialog1.dismiss();

                                }
                            });
                        }
                    }
                });

                rvPosts.setAdapter(adapter);
                dialog.dismiss();


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                dialog.dismiss();
                showToast("Something went wrong!");

            }
        });


    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
