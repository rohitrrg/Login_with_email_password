package com.example.googlesignin;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView newUser;
    private FirebaseAuth mAuth;
    private EditText email,password;
    private Button login;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        newUser = (TextView)findViewById(R.id.new_user);

        progressDialog = new ProgressDialog(this);
        email = (EditText)findViewById(R.id.editEmail);
        password = (EditText)findViewById(R.id.editPass);
        login = (Button)findViewById(R.id.btn_Login);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterUser.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });
    }

    private void SignIn(){
        final String Email, Password;
        Email = email.getText().toString();
        Password = password.getText().toString();

        if (TextUtils.isEmpty(Email)){
            Toast.makeText(MainActivity.this,"Please Enter email...",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(Password)){
            Toast.makeText(MainActivity.this,"Please Enter Password...",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Loging In User...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Welcome "+Email,Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }else
                    progressDialog.dismiss();
            }
        });
    }
}
