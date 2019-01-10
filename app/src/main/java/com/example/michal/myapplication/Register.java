package com.example.michal.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.michal.myapplication.network.NetworkRequest;
import com.example.michal.myapplication.network.Token;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity
{
   private TextView mTitleAction;

   private EditText mEditName;
   private EditText mEditUsername;
   private EditText mEditEmail;
   private EditText mEditPassword;
   Button registerButton;
   TextView loginLink;
   private ProgressDialog mProgressDialog;
   private AuthHelper mAuthHelper;

   public static Intent getCallingIntent(View.OnClickListener context)
   {
      return new Intent((Context) context, Register.class);
   }

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_register);
      mAuthHelper = AuthHelper.getInstance(this);
      mEditName = (EditText) findViewById(R.id.nameBox);
      mEditUsername = (EditText) findViewById(R.id.usernameBox);
      mEditEmail = (EditText) findViewById(R.id.emailBox);
      mEditPassword = (EditText) findViewById(R.id.passwordBox);

      registerButton = (Button) findViewById(R.id.registerButton);
      loginLink = (TextView) findViewById(R.id.loginLink);
      mProgressDialog = new ProgressDialog(this);
      registerButton.setOnClickListener(doSignUpClickListener);
   }

   private void setupView(boolean isSignUpShowing)
   {

   }

   private void doSignUp()
   {
      String name = getNameText();
      String username = getUsernameText();
      String password = getPasswordText();
      String email = getEmailText();

      if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) ||
            TextUtils.isEmpty(name) || TextUtils.isEmpty(email))
      {
         Toast.makeText(this, R.string.toast_no_empty_field, Toast.LENGTH_SHORT).show();
         return;
      }

      mProgressDialog.setMessage(getString(R.string.progress_signup));
      mProgressDialog.setCancelable(true);
      mProgressDialog.show();
      NetworkRequest request = new NetworkRequest();
      request.doSignUp(name, username, password, email, mSignUpCallback);
   }

   private NetworkRequest.Callback<Token> mSignUpCallback = new NetworkRequest.Callback<Token>()
   {
      @Override
      public void onResponse(@NonNull Token response)
      {
         dismissDialog();
         // save token and go to profile page
         saveSessionDetails(response);
      }

      @Override
      public void onError(String error)
      {
         dismissDialog();
         Toast.makeText(Register.this, error, Toast.LENGTH_SHORT).show();
      }

      @Override
      public Class<Token> type()
      {
         return Token.class;
      }
   };

   private void dismissDialog()
   {
      if (mProgressDialog != null && mProgressDialog.isShowing())
      {
         mProgressDialog.dismiss();
      }
   }

   private void saveSessionDetails(@NonNull Token token)
   {
      mAuthHelper.setIdToken(token);
      mAuthHelper.setUserName(getUsernameText());

      // start profile activity
      startActivity(CardListActivity.getCallingIntent(this));
   }

   private String getUsernameText()
   {
      return mEditEmail.getText().toString().trim();
   }

   private String getNameText()
   {
      return mEditName.getText().toString().trim();
   }

   private String getPasswordText()
   {
      return mEditPassword.getText().toString().trim();
   }

   private String getEmailText()
   {
      return mEditEmail.getText().toString().trim();
   }

   private final View.OnClickListener doSignUpClickListener = new View.OnClickListener()
   {
      @Override
      public void onClick(View view)
      {
         doSignUp();
      }
   };
}
