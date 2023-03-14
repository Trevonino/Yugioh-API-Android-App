package com.example.cis385finalproject.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.cis385finalproject.R;
import com.example.cis385finalproject.RandomCard;
import com.example.cis385finalproject.SearchCard;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class FetchInfo extends AsyncTask<String, Void, String> {

    private String mClassName;

    private WeakReference<TextView> mCardNameText;
    private WeakReference<ImageView> mCardImage;
    private WeakReference<TextView> mCardLevel;
    private WeakReference<TextView> mCardRace;
    private WeakReference<TextView> mCardAttribute;
    private WeakReference<TextView> mCardTypeText;
    private WeakReference<TextView> mCardDescText;
    private WeakReference<TextView> mCardAttackAndDef;
    private WeakReference<TextView> mCardArchetype;
    private WeakReference<TextView> mCardPrice;

    public FetchInfo(TextView nameText, ImageView cardImage, TextView cardLevel, TextView cardRace, TextView cardAttribute,TextView cardType, TextView cardDesc, TextView cardAtkAndDef,TextView cardArch, TextView cardPrice, String className) {
        this.mCardNameText = new WeakReference<>(nameText);
        this.mCardImage = new WeakReference<>(cardImage);
        this.mCardLevel = new WeakReference<>(cardLevel);
        this.mCardRace = new WeakReference<>(cardRace);
        this.mCardAttribute = new WeakReference<>(cardAttribute);
        this.mCardTypeText = new WeakReference<>(cardType);
        this.mCardDescText = new WeakReference<>(cardDesc);
        this.mCardAttackAndDef = new WeakReference<>(cardAtkAndDef);
        this.mCardArchetype = new WeakReference<>(cardArch);
        this.mCardPrice = new WeakReference<>(cardPrice);
        this.mClassName = className;
    }


    @Override
    protected String doInBackground(String... strings) {
        if (mClassName.equals("Search Card")) {
            return CardSearchUtil.getCardInfo(strings[0]);
        }

        if (mClassName.equals("Random Card")){
            return RandomCardUtil.getCardInfo();
        }
        else return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            if (s != null){
            JSONObject jsonObject = new JSONObject(s);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            int i = 0;
            String name = null;
            String imageURL = null;
            String cardLevel = null;
            String cardLinkVal = null;
            String cardRace = null;
            String cardAttribute = null;
            String cardType = null;
            String cardDesc = null;
            String cardAtk = null;
            String cardDef = null;
            String cardArch = null;
            String cardPrice = null;
            while (i < dataArray.length() &&
                    (imageURL == null && name == null && cardDesc == null && cardType == null)) {
                // Get the current item information.
                JSONObject cardInfo = dataArray.getJSONObject(i);
                JSONArray cardImage = cardInfo.getJSONArray("card_images");
                JSONArray cardPrices = cardInfo.getJSONArray("card_prices");
                JSONObject imageURLObject = cardImage.getJSONObject(i);
                JSONObject cardPriceObject = cardPrices.getJSONObject(i);

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    name = cardInfo.getString("name");
                    imageURL = imageURLObject.getString("image_url");
                    SearchCard.setImageURL(imageURL);
                    try {
                        cardLevel = cardInfo.getString("level");
                    }
                    catch (Exception e){
                        cardLevel = null;
                    }
                    try {
                        cardLinkVal = cardInfo.getString("linkval");
                    }
                    catch (Exception e){
                        cardLinkVal = null;
                    }
                    try {
                        cardAttribute = cardInfo.getString("attribute");
                    }
                    catch (Exception e){
                        cardAttribute = null;
                    }
                    cardRace = cardInfo.getString("race");
                    cardType = cardInfo.getString("type");
                    cardDesc = cardInfo.getString("desc");
                    try {
                        cardAtk = cardInfo.getString("atk");
                    }
                    catch (Exception e){
                        cardAtk = null;
                    }
                    try {
                        cardDef = cardInfo.getString("def");
                    }
                    catch (Exception e){
                        cardDef = null;
                    }
                    try {
                        cardArch = cardInfo.getString("archetype");
                    }
                    catch (Exception e){
                        cardArch = null;
                    }

                    try {
                        cardPrice = cardPriceObject.getString("tcgplayer_price");
                    }
                    catch (Exception e){
                        cardPrice = null;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }
            if (name != null && imageURL != null && cardType != null && cardDesc != null) {
                mCardNameText.get().setText(name);
                Picasso.get().load(imageURL).into(mCardImage.get());
                if (cardLevel != null){

                    mCardLevel.get().setVisibility(View.VISIBLE);
                    mCardRace.get().setVisibility(View.VISIBLE);
                    mCardAttribute.get().setVisibility(View.VISIBLE);

                    String tempLevelString = "Level: ";
                    String tempRaceString = "Race: ";
                    String tempAttributeString = "Attribute: ";

                    tempLevelString = tempLevelString.concat(cardLevel);
                    tempRaceString = tempRaceString.concat(cardRace);
                    tempAttributeString = tempAttributeString.concat(cardAttribute);

                    mCardLevel.get().setText(tempLevelString);
                    mCardRace.get().setText(tempRaceString);
                    mCardAttribute.get().setText(tempAttributeString);
                }

                if (cardLinkVal != null){
                    mCardLevel.get().setVisibility(View.VISIBLE);
                    mCardRace.get().setVisibility(View.VISIBLE);
                    mCardAttribute.get().setVisibility(View.VISIBLE);

                    String tempLinkString = "Link Value: ";
                    String tempRaceString = "Race: ";
                    String tempAttributeString = "Attribute: ";

                    tempLinkString = tempLinkString.concat(cardLinkVal);
                    tempRaceString = tempRaceString.concat(cardRace);
                    tempAttributeString = tempAttributeString.concat(cardAttribute);

                    mCardLevel.get().setText(tempLinkString);
                    mCardRace.get().setText(tempRaceString);
                    mCardAttribute.get().setText(tempAttributeString);
                }

                if (cardLinkVal == null && cardLevel == null) {
                    mCardLevel.get().setVisibility(View.GONE);
                    mCardRace.get().setVisibility(View.GONE);
                    mCardAttribute.get().setVisibility(View.GONE);
                }
                mCardTypeText.get().setText(cardType);
                mCardDescText.get().setText(cardDesc);
                if (cardAtk != null) {
                    mCardAttackAndDef.get().setVisibility(View.VISIBLE);
                    String cardAtkDeftemp = "ATK: ";
                    cardAtkDeftemp = cardAtkDeftemp.concat(cardAtk);
                    if (cardDef != null) {
                        cardAtkDeftemp = cardAtkDeftemp.concat("/ DEF: ");
                        cardAtkDeftemp = cardAtkDeftemp.concat(cardDef);
                    }
                    mCardAttackAndDef.get().setText(cardAtkDeftemp);
                }
                else{
                    mCardAttackAndDef.get().setVisibility(View.GONE);
                }
                if (cardArch != null) {
                    mCardArchetype.get().setVisibility(View.VISIBLE);

                    String tempCardArch = "Card Archetype: ";
                    tempCardArch = tempCardArch.concat(cardArch);

                    mCardArchetype.get().setText(tempCardArch);
                }
                else{
                    mCardArchetype.get().setVisibility(View.GONE);
                }

                if (cardPrice != null) {
                    mCardPrice.get().setVisibility(View.VISIBLE);

                    String tempCardPrice = "Card Price: $";
                    tempCardPrice = tempCardPrice.concat(cardPrice);

                    mCardPrice.get().setText(tempCardPrice);
                }
                else{
                    mCardPrice.get().setVisibility(View.GONE);
                }


            }
            } else {
                // If none are found, update the UI to
                // show failed results.
                Log.d("test","item not found?");
                mCardNameText.get().setText(R.string.no_results);
                Picasso.get().load(R.drawable.card_blank).into(mCardImage.get());
                mCardAttribute.get().setText("");
                mCardLevel.get().setText("");
                mCardRace.get().setText("");
                mCardTypeText.get().setText("");
                mCardDescText.get().setText("");
                mCardAttackAndDef.get().setText("");
                mCardArchetype.get().setText("");
                mCardPrice.get().setText("");
            }
        } catch (JSONException e) {
            Log.d("test","found nothing");
            e.printStackTrace();
            mCardNameText.get().setText(R.string.no_results);
            mCardAttribute.get().setText("");
            mCardLevel.get().setText("");
            mCardRace.get().setText("");
            mCardTypeText.get().setText("");
            mCardDescText.get().setText("");
            mCardAttackAndDef.get().setText("");
            mCardArchetype.get().setText("");
            mCardPrice.get().setText("");
            e.printStackTrace();
        }
    }

    public static boolean cardsInBudget(double cardTotal, double budgetTotal) {
        if (cardTotal < budgetTotal) {
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean cardIsSpell(String cardType) {
        if (cardType.equals("Spell Card")) {
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean cardsLevelInRange(int cardLevel, int levelMax, int levelMin) {
        if (cardLevel < levelMax && cardLevel > levelMin) {
            return true;
        }
        else{
            return false;
        }
    }

}
