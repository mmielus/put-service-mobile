package com.example.michal.myapplication;

import static com.example.michal.myapplication.R.string.error_signup;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.michal.myapplication.network.NetworkRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CardArrayAdapter extends ArrayAdapter<Card>
{
   private static final String TAG = "CardArrayAdapter";
   private List<Card> cardList = new ArrayList<Card>();

   static class CardViewHolder
   {
      TextView description;
      TextView dimensions;
      TextView phoneNumber;
      TextView email;
      TextView city;
      TextView street;
      TextView houseNumber;
      TextView payment;

   }

   @SuppressLint("ResourceType")
   public CardArrayAdapter(Context context, int textViewResourceId)
   {
      super(context, R.id.list_item_card, textViewResourceId);
   }

   @Override
   public void add(Card object)
   {
      cardList.add(object);
      super.add(object);
   }

   @Override
   public int getCount()
   {
      return this.cardList.size();
   }

   @Override
   public Card getItem(int index)
   {
      return this.cardList.get(index);
   }

   @Override
   public View getView(final int position, View convertView, ViewGroup parent)
   {
      View row = convertView;
      CardViewHolder viewHolder;
      if (row == null)
      {
         LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         row = inflater.inflate(R.layout.list_item_card, parent, false);
         viewHolder = new CardViewHolder();
         viewHolder.description = (TextView) row.findViewById(R.id.description);
         viewHolder.dimensions = (TextView) row.findViewById(R.id.dimensions);
         viewHolder.phoneNumber = (TextView) row.findViewById(R.id.phoneNumber);
         viewHolder.email = (TextView) row.findViewById(R.id.email);
         viewHolder.city = (TextView) row.findViewById(R.id.city);
         viewHolder.street = (TextView) row.findViewById(R.id.street);
         viewHolder.houseNumber = (TextView) row.findViewById(R.id.houseNumber);
         viewHolder.payment = (TextView) row.findViewById(R.id.payment);

         row.setTag(viewHolder);

         row.setOnClickListener(new View.OnClickListener()
         {
            @Override
            public void onClick(View view)
            {

             //  CardViewHolder clickedObject = (CardViewHolder) view.getTag();
               Object object = getItem(position);
               Card card = (Card) object;

               CardListActivity.test(card);
            }

         });
      }
      else
      {
         viewHolder = (CardViewHolder) row.getTag();
      }
      Card card = getItem(position);

      List<String> dimensionsList = card.getDimensions();

      String dimensions = String.join(" - ", dimensionsList);

      viewHolder.description.setText(Objects.requireNonNull(card).getDescription());
      viewHolder.dimensions.setText(dimensions);
      viewHolder.phoneNumber.setText(Objects.requireNonNull(card).getPhoneNumber());
      viewHolder.email.setText(Objects.requireNonNull(card).getEmail());
      viewHolder.city.setText(Objects.requireNonNull(card).getCity());
      viewHolder.street.setText(Objects.requireNonNull(card).getStreet());
      viewHolder.houseNumber.setText(Objects.requireNonNull(card).getHouseNumber());
      viewHolder.payment.setText(Objects.requireNonNull(card).getPayment());

      return row;
   }

   public Bitmap decodeToBitmap(byte[] decodedByte)
   {
      return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
   }
}