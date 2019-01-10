package com.example.michal.myapplication.network;

import java.io.IOException;
import java.util.List;

import com.google.gson.GsonBuilder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;

public class LoginRequest extends RequestBody
{

   private List<String> encodedNames;
   private List<String> encodedValues;

   private String usernameOrEmail;

   private String password;

   LoginRequest(String usernameOrEmail, String password)
   {
      this.usernameOrEmail = usernameOrEmail;
      this.password = password;
   }

   LoginRequest(List<String> encodedNames, List<String> encodedValues)
   {
      this.encodedNames = Util.immutableList(encodedNames);
      this.encodedValues = Util.immutableList(encodedValues);
   }

   public String getUsernameOrEmail()
   {
      return usernameOrEmail;
   }

   public void setUsernameOrEmail(String usernameOrEmail)
   {
      this.usernameOrEmail = usernameOrEmail;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }

   @Override
   public MediaType contentType()
   {
      return MediaType.parse("application/json");
   }

   @Override
   public void writeTo(BufferedSink sink) throws IOException
   {
      writeOrCountBytes(sink, false);
   }

   private long writeOrCountBytes(BufferedSink sink, boolean countBytes)
   {
      long byteCount = 0L;

      Buffer buffer;
      if (countBytes)
      {
         buffer = new Buffer();
      }
      else
      {
         buffer = sink.buffer();
      }

      for (int i = 0; i < 2; i++)
      {
         if (i > 0)
         {
            buffer.writeByte('&');
         }
         //   buffer.writeUtf8(encodedNames.get(i));
         //    buffer.writeByte('=');
         buffer.writeUtf8(usernameOrEmail);
         buffer.writeUtf8(password);
      }

      if (countBytes)
      {
         byteCount = buffer.size();
         buffer.clear();
      }

      return 2;
   }

   @Override
   public String toString()
   {
      return new GsonBuilder().create().toJson(this, LoginRequest.class);
   }
}
