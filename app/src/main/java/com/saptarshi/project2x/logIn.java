package com.saptarshi.project2x;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.saptarshi.project2x.databinding.ActivityLogInBinding;

import java.util.Objects;

public class logIn extends AppCompatActivity {

    ActivityLogInBinding activityLogInBinding;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogInBinding = DataBindingUtil.setContentView(logIn.this,R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            updateUi();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        backgroundAnimation();
    }

    private void backgroundAnimation() {
        ConstraintLayout constraintLayout = findViewById(R.id.root_layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

    }

    public void signUpPage(View view){
        Intent intent = new Intent(logIn.this,signUp.class);
        startActivity(intent);
    }


    public void login(View view) {
        activityLogInBinding.login.setEnabled(false);
        String email = Objects.requireNonNull(activityLogInBinding.emailTextLayout.getEditText()).getText().toString();
        String password = Objects.requireNonNull(activityLogInBinding.passwordLayout.getEditText()).getText().toString();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    updateUi();
                }else{
                    Toast.makeText(logIn.this, Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                    activityLogInBinding.login.setEnabled(true);
                }
            }
        });

    }
    public void updateUi(){
        Intent intent = new Intent(logIn.this,homeActivity.class);
        startActivity(intent);
        finish();
    }

}
