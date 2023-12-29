package com.example.adamatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adamatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    public EditText emailText, passwordText;
    public MaterialButton loginButton, registerButton, forgotButton, submitButton, backButton;
    public CheckBox rememberCheckbox;
    private FirebaseAuth mAuth;
    public TextView welcomePrompt, loginPrompt, emailPrompt , passwordPrompt, forgotText;
    public RelativeLayout forgotBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        setListeners();
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void initComponents() {
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        rememberCheckbox = findViewById(R.id.rememberMe);
        forgotButton = findViewById(R.id.forgot_button);
        submitButton = findViewById(R.id.submit_button);
        forgotBox = findViewById(R.id.checkbox);
        welcomePrompt = findViewById(R.id.welcome_text);
        loginPrompt = findViewById(R.id.login_prompt);
        emailPrompt = findViewById(R.id.email_text);
        passwordPrompt = findViewById(R.id.password_text);
        backButton = findViewById(R.id.back_button);
        forgotText = findViewById(R.id.forgot_password);

    }

    private void setListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = emailText.getText().toString();
                password = passwordText.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Ingresa un correo electronico", Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Ingresa una contraseña", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String TAG = "FirebaseAUTH";
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Proceed with activity
                                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setVisibility(View.GONE);
                registerButton.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
                forgotBox.setVisibility(View.GONE);
                welcomePrompt.setText("Nuevo Usuario");
                loginPrompt.setText("Establece tus credenciales");
                backButton.setVisibility(View.VISIBLE);
                forgotButton.setVisibility(View.GONE);
            }
        });

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email;
                email = emailText.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Ingresa un correo electronico",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                return;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TAG = "FirebaseAUTH";
                String email, password;
                email = emailText.getText().toString();
                password = passwordText.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Ingresa un correo electronico", Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Ingresa una contraseña", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Proceed with activity
                                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordText.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                registerButton.setVisibility(View.VISIBLE);
                forgotButton.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                forgotBox.setVisibility(View.VISIBLE);
                welcomePrompt.setText("Bienvenid@");
                loginPrompt.setText("Procede a autenticarte");
                passwordPrompt.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.GONE);
            }
        });

        forgotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setVisibility(View.GONE);
                registerButton.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
                forgotBox.setVisibility(View.GONE);
                welcomePrompt.setText("Recuperar Contraseña");
                loginPrompt.setText("Indica tu correo");
                backButton.setVisibility(View.VISIBLE);
                passwordText.setVisibility(View.GONE);
                forgotButton.setVisibility(View.VISIBLE);
                passwordPrompt.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}