package net.mashnoor.trainticketapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class PostTicket extends AppCompatActivity {


    BootstrapEditText betFrom, betTo, betJourneyDate, betJourneyTime, betNoOfSeats, betTrainName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ticket);

        betFrom = findViewById(R.id.betFromStation);
        betTo = findViewById(R.id.betToStation);
        betJourneyDate = findViewById(R.id.betJournetDate);
        betJourneyTime = findViewById(R.id.betJourneyTime);
        betNoOfSeats = findViewById(R.id.betNoOfSeats);
        betTrainName = findViewById(R.id.betTrainName);
    }

    public void postTicketOnline(View v)
    {
        String from = betFrom.getText().toString();
        String to = betTo.getText().toString();
        String journeyDate = betJourneyDate.getText().toString();
        String journeyTime = betJourneyTime.getText().toString();
        String noOfSeats = betNoOfSeats.getText().toString();
        String trainName = betTrainName.getText().toString();

        RequestParams params = new RequestParams();
        params.put("trainname", trainName);
        params.put("journeytime", journeyTime);
        params.put("journeydate", journeyDate);
        params.put("fromplace", from);
        params.put("toplace", to);
        params.put("noofseats", noOfSeats);
        params.put("userid", AppHelper.getUser().getId());

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Posting. Please wait...");

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(AppUrl.POST_TICKET_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                dialog.dismiss();

                if(response.equals("success"))
                {

                    showToast("Successfully posted!");
                    finish();

                }
                else
                {
                    showToast("Something went wrong!");
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dialog.dismiss();
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
