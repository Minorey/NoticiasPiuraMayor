package com.utp.testinnp.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utp.testinnp.R;

public class FavoritePostsActivity extends AppCompatActivity {
    private TextView mTextViewEmail;
    Button mButtomCerrarSesion;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_posts);
        /*
        mTextViewEmail = findViewById(R.id.textViewEmail);
        mButtomCerrarSesion = findViewById(R.id.btn_CerrarSesion);
         */
        mAuth = FirebaseAuth.getInstance();

        // Obtener el correo electrónico del usuario desde la base de datos de Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
            usersRef.child(user.getUid()).child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.getValue(String.class);
                    mTextViewEmail.setText(email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar el error si es necesario
                }
            });
        }
        mButtomCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }

            private void logout() {
                mAuth.signOut();
                Context context = FavoritePostsActivity.this;
                GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).revokeAccess();
                irMain();
            }

            private void onStart(){
                mAuth.signOut();
                irMain();
            }
            private void irMain() {
                Intent intent = new Intent(FavoritePostsActivity.this, login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}