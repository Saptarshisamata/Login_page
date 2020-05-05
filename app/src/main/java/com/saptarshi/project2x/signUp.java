package com.saptarshi.project2x;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saptarshi.project2x.databinding.ActivitySignUpBinding;

import static android.widget.Toast.LENGTH_SHORT;

public class signUp extends AppCompatActivity {

    ActivitySignUpBinding activitySignUpBinding;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(signUp.this, R.layout.activity_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");

    }

    public void create(View view){
        final String email = activitySignUpBinding.email.getText().toString();
        String password = activitySignUpBinding.password.getText().toString();
        final String username = activitySignUpBinding.username.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(signUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user u;
                            u = new user(username,email);
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).setValue(u);
                            login();
                        }else{
                            Toast.makeText(signUp.this, "failed", LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void login() {
        Log.d("login", "createUserWithEmail:success");
        Intent intent = new Intent(this,logIn.class);
        startActivity(intent);
    }
}
