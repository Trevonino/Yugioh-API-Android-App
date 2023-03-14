package com.example.cis385finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cis385finalproject.models.FetchInfo;
import com.example.cis385finalproject.models.FetchInfoRandom;
import com.squareup.picasso.Picasso;

public class RandomCard extends AppCompatActivity {

    private static final String CARD_INFO = "";

    private static String imageURL;
    private String mClassName = "Random Card";

    private TextView mNameText;
    private ImageView mCardImage;
    private TextView mCardLevel;
    private TextView mCardRace;
    private TextView mCardAttribute;
    private TextView mCardType;
    private TextView mDescText;
    private TextView mCardAttackAndDef;
    private TextView mCardArchetype;
    private TextView mCardPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_card);

        mNameText = (TextView)findViewById(R.id.cardNameText);
        mCardImage = (ImageView)findViewById(R.id.cardImage);
        mCardLevel = (TextView)findViewById(R.id.cardLevel);
        mCardRace = (TextView)findViewById(R.id.cardRace);
        mCardAttribute = (TextView)findViewById(R.id.cardAttribute);
        mCardType = (TextView)findViewById(R.id.cardType);
        mDescText = (TextView)findViewById(R.id.cardDescription);
        mCardAttackAndDef = (TextView)findViewById(R.id.cardAtkAndDef);
        mCardArchetype = (TextView)findViewById(R.id.cardArchetype);
        mCardPrice = (TextView)findViewById(R.id.cardPrice);

        if (savedInstanceState != null) {

            String[] test = savedInstanceState.getStringArray(CARD_INFO);

            Picasso.get().load(test[0]).into(mCardImage);
            mNameText.setText(test[1]);
            if (!test[2].equals("")) {
                mCardLevel.setVisibility(View.VISIBLE);
                mCardLevel.setText(test[2]);
            }
            else{
                mCardLevel.setVisibility(View.GONE);
            }
            if (!test[3].equals("")) {
                mCardRace.setVisibility(View.VISIBLE);
                mCardRace.setText(test[3]);
            }
            else{
                mCardRace.setVisibility(View.GONE);
            }
            if (!test[4].equals("")) {
                mCardAttribute.setVisibility(View.VISIBLE);
                mCardAttribute.setText(test[4]);
            }
            else{
                mCardAttribute.setVisibility(View.GONE);
            }
            mCardType.setText(test[5]);
            mDescText.setText(test[6]);
            if (!test[7].equals("")) {
                mCardAttackAndDef.setVisibility(View.VISIBLE);
                mCardAttackAndDef.setText(test[7]);
            }
            else{
                mCardAttackAndDef.setVisibility(View.GONE);
            }
            if (!test[8].equals("")) {
                mCardArchetype.setVisibility(View.VISIBLE);
                mCardArchetype.setText(test[8]);
            }
            else{
                mCardArchetype.setVisibility(View.GONE);
            }
            mCardPrice.setText(test[9]);
        }
        else {
            new FetchInfoRandom(mNameText, mCardImage, mCardLevel, mCardRace, mCardAttribute, mCardType, mDescText, mCardAttackAndDef, mCardArchetype, mCardPrice, mClassName).execute("");
        }
    }

    public static void setImageURL(String url){
        imageURL = url;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String[] bundleArray = {imageURL, mNameText.getText().toString(), mCardLevel.getText().toString(), mCardRace.getText().toString(), mCardAttribute.getText().toString(), mCardType.getText().toString(), mDescText.getText().toString(), mCardAttackAndDef.getText().toString(), mCardArchetype.getText().toString(), mCardPrice.getText().toString()};
        outState.putStringArray(CARD_INFO, bundleArray);
    }

    public void newRandom(View view) {
        new FetchInfoRandom(mNameText, mCardImage, mCardLevel, mCardRace, mCardAttribute, mCardType, mDescText, mCardAttackAndDef, mCardArchetype, mCardPrice, mClassName).execute("");
    }
}