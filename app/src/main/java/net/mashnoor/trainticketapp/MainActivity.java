package net.mashnoor.trainticketapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    RecyclerView rvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvPosts = findViewById(R.id.rvPosts);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        loadPosts();
    }

    private void loadPosts() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading. Please wait...");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(AppUrl.GET_ALL_POSTS_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                Gson g = new Gson();
                Post[] posts = g.fromJson(response, Post[].class);

                rvPosts.setAdapter(new PostsAdapter(Arrays.asList(posts)));
                dialog.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                dialog.dismiss();
                //Toast.makeText(MainActivity.class, "Something went wrong", Toast.LENGTH_LONG).show();
                showToast("Something went wrong!");

            }
        });

    }

    private void showToast(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void goPost(View v)
    {
        startActivity(new Intent(this, PostTicket.class));
    }
}
