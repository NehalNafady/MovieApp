package com.nehalnafady.MovieApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nehal nafady on 2/25/2016.
 */
public class ReviewsAdapter extends ArrayAdapter<Review> {

    public ReviewsAdapter(Context c, List<Review> reviews)
    {
        super(c ,0,reviews);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Review review = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item , parent, false);
        }

        TextView autherName =  (TextView) convertView.findViewById(R.id.auther_name);
        TextView reviewContent =  (TextView) convertView.findViewById(R.id.review_content);

        autherName.setText(review.getAuthor());
        reviewContent.setText(review.getContent() );

        return convertView;

    }
}
