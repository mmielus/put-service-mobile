package com.example.michal.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.example.michal.myapplication.network.NetworkRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CardListActivity extends Activity
{

   private static final String TAG = "CardListActivity";
   private CardArrayAdapter cardArrayAdapter;
   private ListView listView;
   private static AuthHelper mAuthHelper;

   private ProgressDialog mProgressDialog;

   public static Intent getCallingIntent(Context context)
   {
      return new Intent(context, CardListActivity.class);
   }

   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.listview);

      mProgressDialog = new ProgressDialog(this);
      mAuthHelper = AuthHelper.getInstance(this);

      listView = (ListView) findViewById(R.id.card_listView);

      listView.addHeaderView(new View(this));
      listView.addFooterView(new View(this));

      cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);

      if (mAuthHelper.isLoggedIn())
      {
         setupView();
      }
      else
      {
         finish();
      }

      listView.setAdapter(cardArrayAdapter);
   }

   private void setupView()
   {
      doGetQuote(mAuthHelper.getIdToken());
   }

   private void doGetQuote(String token)
   {
      NetworkRequest request = new NetworkRequest();
      mProgressDialog.setMessage(getString(R.string.progress_quote));
      mProgressDialog.setCancelable(true);
      mProgressDialog.show();
      request.getAllOffers(token, new NetworkRequest.Callback<String>()
      {
         @Override
         public void onResponse(@NonNull String response)
         {
            JSONObject reader;
            List<Card> cardList = new ArrayList<>();
            try
            {
               reader = new JSONObject(response);
               JSONArray jsonArray = Objects.requireNonNull(reader).getJSONArray("content");
               for (int i = 0; i < jsonArray.length(); i++)
               {
                  JSONObject jsonObject = jsonArray.getJSONObject(i);
                  JSONArray dimensionsArray = jsonObject.getJSONArray("dimensions");
                  List<String> dimensionsList = new ArrayList<>();

                  for (int j = 0; j < dimensionsArray.length(); j++)
                  {
                     JSONObject dimensionObject = dimensionsArray.getJSONObject(j);
                     dimensionsList.add(dimensionObject.getString("text"));

                  }
                  cardArrayAdapter
                        .add(new Card(jsonObject.getLong("id"), jsonObject.getString("description"), dimensionsList,
                              jsonObject.getString("phoneNumber"),
                              jsonObject.getString("email"), jsonObject.getString("city"),
                              jsonObject.getString("street"), jsonObject.getString("houseNumber"),
                              jsonObject.getString("payment")));
               }
            }
            catch (JSONException e)
            {
               e.printStackTrace();
            }

            //  cardList.add(new Card(reader.get))

            dismissDialog();
         }

         @Override
         public void onError(String error)
         {
            dismissDialog();
            Toast.makeText(CardListActivity.this, error, Toast.LENGTH_SHORT).show();
         }

         @Override
         public Class<String> type()
         {
            return String.class;
         }
      });
   }

   public static void test(Card card)
   {

      NetworkRequest request = new NetworkRequest();
      request.doObserveOffer(card.getId(), mAuthHelper.getUsername(), new NetworkRequest.Callback<String>()
      {
         @Override
         public void onResponse(String response)
         {

         }

         @Override
         public void onError(String error)
         {

         }

         @Override
         public Class<String> type()
         {
            return null;
         }
      });
   }

   private void dismissDialog()
   {
      if (mProgressDialog != null && mProgressDialog.isShowing())
      {
         mProgressDialog.dismiss();
      }
   }
}