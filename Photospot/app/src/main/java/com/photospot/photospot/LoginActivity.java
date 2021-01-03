package com.photospot.photospot;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.photospot.photospot.R;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    static final int GOOGLE_SIGN = 123;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    //    Animations
    AnimationDrawable backgroundAnimation;
    Drawable backgroundDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        //Hiding statusbar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //Making the background gradient animate
        backgroundDrawable = findViewById(R.id.root_layout).getBackground();
        Utils.animateGradient(backgroundDrawable, 4000);

        findViewById(R.id.sign_in_button).setOnClickListener(view -> signInGoogle());

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUi(user);
        }
    }

    void signInGoogle() {
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    //make request firebase
                    firebaseAuthWithGoogle(account);
                    updateUi(mAuth.getCurrentUser());
                }

            } catch (ApiException e) {
                e.printStackTrace();
            }
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle: " + account.getId());

        AuthCredential credential = GoogleAuthProvider
                .getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signin succes");

                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUi(user);
                    } else {
                        Log.w(TAG, "sigin failure", task.getException());

                        Toast.makeText(this, "SignIn failed!", Toast.LENGTH_SHORT).show();
                        updateUi(null);
                    }
                });
    }

    private void updateUi(FirebaseUser user) {
        if (user != null) {

            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        } else {
            setContentView(R.layout.login_screen);
        }
    }
}
