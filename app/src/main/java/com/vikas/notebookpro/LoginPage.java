package com.vikas.notebookpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginPage extends AppCompatActivity {
    private EditText emailId,passwordText;
    Button loginTextBtn;
    TextView accountcreateBtn;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        emailId=findViewById(R.id.emailIdBtn);
        passwordText=findViewById(R.id.passwordBtn);
        loginTextBtn=findViewById(R.id.loginsubmitBtn);
        accountcreateBtn=findViewById(R.id.createAccount_text_btn);
        loading=findViewById(R.id.progress_bar_btn);
        loginTextBtn.setOnClickListener((e)->userLoginInformation());
        accountcreateBtn.setOnClickListener(e->startActivity(new Intent(LoginPage.this,CreateAccountActivity.class)));
    }
    private void userLoginInformation(){
        String userEmailId=emailId.getText().toString();
        String userPassword=passwordText.getText().toString();
        boolean isItVaildInformation=isVaildInformation(userEmailId,userPassword);
        if(!isItVaildInformation) return;
        loginInformationInToFireBase(userEmailId,userPassword);
    }
      private void loginInformationInToFireBase(String email,String password){
        progressProcess(true);
          FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
          firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  progressProcess(false);
                  if(task.isSuccessful()){
                      //if user already create account
                      //if user not verify email id
                      if(!firebaseAuth.getCurrentUser().isEmailVerified())
                      {
                          UtiltyToast.toastMessage(LoginPage.this,"Please verify email,Please check Onces mail");
                          finish();
                      }
                      else{
                          //useer already create account and verified emailId
                          startActivity(new Intent(LoginPage.this,MainActivity.class));
                      }
                  }else{
                      //user enter incorrent details
                      UtiltyToast.toastMessage(LoginPage.this,task.getException().getLocalizedMessage());
                  }
              }
          });


      }
    private void progressProcess(boolean progress){
        if(progress){
            loading.setVisibility(View.VISIBLE);
            loginTextBtn.setVisibility(View.GONE);
        }else{
            loading.setVisibility(View.GONE);
            loginTextBtn.setVisibility(View.VISIBLE);
        }

    }
    boolean isVaildInformation( String email,String password){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailId.setError("inVaild EmailId");
            return  false;
        }
        Pattern p=Pattern.compile("(?=.*[!@#%])(?=.*[a-z]).{6,}");
        if(!p.matcher(password).matches()) {
            passwordText.setError("Please enter strong password");
            return  false;
        }
        return  true;
    }
}