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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText emailId,passwordText,conformPasswordText;
    Button creatAccountTextBtn;
    TextView loginBtn;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        emailId=findViewById(R.id.emailIdBtn);
        passwordText=findViewById(R.id.passwordBtn);
        conformPasswordText=findViewById(R.id.conformPasswordBtn);
        creatAccountTextBtn=findViewById(R.id.createAccountsubmitBtn);
        loginBtn=findViewById(R.id.login_text_btn);
        loading=findViewById(R.id.progress_bar_btn);
        creatAccountTextBtn.setOnClickListener(e->userInformation());
        loginBtn.setOnClickListener(e->startActivity(new Intent(CreateAccountActivity.this,LoginPage.class)));

    }
    void userInformation(){
         String userEmailId=emailId.getText().toString();
         String userPassword=passwordText.getText().toString();
         String userConformPassword=conformPasswordText.getText().toString();
         boolean checkVaidInformation=isVaildInformation(userEmailId,userPassword,userConformPassword);
         if(!checkVaidInformation) return;
         userInformationIntoFireBase(userEmailId,userPassword);

    }
    private void userInformationIntoFireBase( String email,String password){
        progressProcess(true);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressProcess(false);
                if(task.isSuccessful()){
                    UtiltyToast.toastMessage(CreateAccountActivity.this,"Sucessfull  Create Account and Please check mail ");
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    //to clear userinformation
                    clearUserInformation();

                }else{
                    progressProcess(false);
                    UtiltyToast.toastMessage(CreateAccountActivity.this,task.getException().getLocalizedMessage());
                    //to clear userinformation
                    clearUserInformation();
                }
            }

        });

    }
    private  void clearUserInformation(){
        emailId.setText("");
        passwordText.setText("");
        conformPasswordText.setText("");
    }
    private void progressProcess(boolean progress){
        if(progress){
            loading.setVisibility(View.VISIBLE);
            creatAccountTextBtn.setVisibility(View.GONE);
        }else{
            loading.setVisibility(View.GONE);
            creatAccountTextBtn.setVisibility(View.VISIBLE);
        }

    }
    boolean isVaildInformation( String email,String password,String conformPassword){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailId.setError("inVaild EmailId");
            return  false;
        }
        Pattern p=Pattern.compile("(?=.*[!@#%])(?=.*[a-z]).{6,}");
        if(!p.matcher(password).matches()) {
            passwordText.setError("Please enter strong password");
            return  false;
        }
        if(!password.equals(conformPassword)) {
            conformPasswordText.setError("Passwords not matching");
            return  false;
        }
        return  true;
    }
}