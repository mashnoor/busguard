package net.mashnoor.trainticketapp;

import android.graphics.Color;
import android.opengl.Visibility;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class PostsAdapter extends BaseQuickAdapter<Post, BaseViewHolder> {

    public PostsAdapter(@Nullable List<Post> data) {
        super(R.layout.row_ticket, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Post post) {
        helper.setText(R.id.tvTrainName, post.getTrainName());
        helper.setText(R.id.tvFrom, post.getFromPlace());
        helper.setText(R.id.tvTo, post.getToPlace());
        helper.setText(R.id.tvNoOfSeats, post.getNoOfSeats());
        helper.setText(R.id.tvJournetDate, post.getJourneyDate());
        helper.setText(R.id.tvJourneyTime, post.getJourneyTime());
        if (AppHelper.getUser().getId() == post.getPostedBy().getId()) {
            helper.setText(R.id.tvPostedBy, "Posted By: Me");
            helper.setText(R.id.btnCall, "Delete");
            helper.setBackgroundColor(R.id.btnCall, Color.RED);


        } else {
            helper.setText(R.id.tvPostedBy, "Posted By: " + post.getPostedBy().getName());
        }
        helper.addOnClickListener(R.id.btnCall);



    }
}
