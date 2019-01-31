package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    //pass in context and list of tweets
    private Context context;
    private List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //for each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the view
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);

        //wrap view in ViewHolder
        return new ViewHolder(view);
    }

    //bind values based on the position of the element (the tweet in the List)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);

        //use data of tweet to populate ViewHolder
        holder.tvBody.setText(tweet.body);
        holder.tvScreenName.setText("@" + tweet.user.screenName);
        holder.tvUserName.setText(tweet.user.userName);
        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);

        String relativeTime = getRelativeTimeAgo(tweet.createdAt);
        holder.tvTimeStamp.setText(relativeTime);

    }

    //calculate relative time for tweet timestamp
    private String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

            //remove all whitespaces in string
            relativeDate = relativeDate.replaceAll("\\s+", "");

            //cut off everything after first 3 characters in string
            relativeDate = relativeDate.substring(0, 3);
            String testDate = relativeDate;
            testDate = testDate.substring(0,2);

            //check if first two chars in string are digits
            //if yes, keep first 3 chars in string
            //if no, keep first 2 chars in string
            if (TextUtils.isDigitsOnly(testDate))
                relativeDate = relativeDate.substring(0,3);
            else
                relativeDate = relativeDate.substring(0,2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    // how many items are in our data set (List of tweets)
    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addTweets(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }


    //define the ViewHolder (for an individual tweet)
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
        @BindView(R.id.tvScreenName) TextView tvScreenName;
        @BindView(R.id.tvBody) TextView tvBody;
        @BindView(R.id.tvUserName) TextView tvUserName;
        @BindView(R.id.tvTimeStamp) TextView tvTimeStamp;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
