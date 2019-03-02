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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity {

    private EditText Email, Password;
    private Button SignUp;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        Email = (EditText) findViewById(R.id.editnewEmail1);
        Password = (EditText)findViewById(R.id.editnewPass1);
        SignUp = (Button)findViewById(R.id.btn_SignUp1);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegister();

            }
        });
    }

    void UserRegister() {
        String email, password;
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Registering User...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(RegisterUser.this, MainActivity.class));
                            Toast.makeText(RegisterUser.this, "SignUp Complete", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        } else
                            Toast.makeText(RegisterUser.this, "Registration Failed", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
    }

}
