package com.example.michal.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.auth0.android.jwt.JWT;
import com.example.michal.myapplication.network.Token;

public class AuthHelper
{

   /**
    * Key for username in the jwt claim
    */
   private static final String JWT_KEY_USERNAME = "username";

   private static final String PREFS = "prefs";
   private static final String PREF_TOKEN = "pref_token";
   private SharedPreferences mPrefs;

   private static AuthHelper sInstance;

   private AuthHelper(@NonNull Context context)
   {

      mPrefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
      clear();
      sInstance = this;
   }

   public static AuthHelper getInstance(@NonNull Context context)
   {
      if (sInstance == null)
      {
         sInstance = new AuthHelper(context);
      }
      return sInstance;
   }

   public void setIdToken(@NonNull Token token)
   {
      SharedPreferences.Editor editor = mPrefs.edit();
      editor.putString(PREF_TOKEN, token.getIdToken());
      editor.apply();
   }

   @Nullable
   public String getIdToken()
   {
      return mPrefs.getString(PREF_TOKEN, null);
   }

   public boolean isLoggedIn()
   {
      String token = getIdToken();
      return token != null;
   }

   /**
    * Gets the username of the signed in user
    *
    * @return - username of the signed in user
    */
   public String getUsername()
   {
      if (isLoggedIn())
      {
         return decodeUsername(getIdToken());
      }
      return null;
   }

   @Nullable
   private String decodeUsername(String token)
   {
      //     JWT jwt = new JWT(token);
      try
      {
         // if (jwt.getClaim(JWT_KEY_USERNAME) != null)
         // {
         return mPrefs.getString(JWT_KEY_USERNAME, null);
         // }

      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      return null;
   }

   public void clear()
   {
      mPrefs.edit().clear().commit();
   }

   public void setUserName(String username)
   {
      SharedPreferences.Editor editor = mPrefs.edit();
      editor.putString(JWT_KEY_USERNAME, username);
      editor.apply();
   }
}