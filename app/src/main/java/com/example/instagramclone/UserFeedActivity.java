package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        Intent intent = getIntent();
        String username = intent.getStringExtra(Constants.PARSE_USERNAME_COLUMN);

        setTitle(username + "'s Photos");

        linearLayout = findViewById(R.id.linLayout);
        //textView = findViewById(R.id.textView);

        final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(Constants.PARSE_CLASS_NAME_FOR_IMAGES);

        query.whereEqualTo(Constants.PARSE_USERNAME_COLUMN, username);
        query.orderByDescending(Constants.PARSE_CREATED_AT);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0){
                    for (ParseObject object : objects){
                        ParseFile file = (ParseFile) object.get(Constants.PARSE_IMAGE_COLUMN );
                        final String postText = object.getString(Constants.PARSE_POST_TEXT_COLUMN);

                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                            if (e == null && data != null){
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                                ImageView imageView = new ImageView(getApplicationContext());

                                imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));

                                imageView.setImageBitmap(bitmap);

                                linearLayout.addView(imageView);

                                // text view
                                TextView linearTextView = new TextView(getApplicationContext());

                                linearTextView.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT
                                ));
                                linearTextView.setText(postText);

                                linearTextView.setTextSize(20);

                                linearLayout.addView(linearTextView);

                                TextView textView = findViewById(R.id.textView2);
                            }
                            }
                        });
                    }
                }
            }
        });

    }
}