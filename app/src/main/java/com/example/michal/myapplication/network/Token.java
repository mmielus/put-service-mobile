package com.example.michal.myapplication.network;

import com.google.gson.annotations.SerializedName;

public class Token implements NetworkRequest.ApiResponse {

   @SerializedName("accessToken")
   private String idToken;

   public String getIdToken() {
      return idToken;
   }

   @Override
   public String string() {
      return idToken;
   }
}