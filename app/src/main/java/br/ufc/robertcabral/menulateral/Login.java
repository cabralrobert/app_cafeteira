package br.ufc.robertcabral.menulateral;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    DatabaseReference firebase;
    Button botaoCadastro;
    User user;
    FirebaseAuth auth;
    EditText nomeCadastro, senhaCadastro;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        firebase = BibliotecaAll.getFirebase();

        auth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent it = new Intent(Login.this, MainActivity.class);
                    startActivity(it);
                    finish();
                }
            }
        };


        botaoCadastro = (Button)findViewById(R.id.button2);

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }


    public void loginEmail(View view) {
        user = new User();

        nomeCadastro = (EditText) findViewById(R.id.nomeCadastro);
        senhaCadastro = (EditText) findViewById(R.id.passwordUser);

        user.setEmail(nomeCadastro.getText().toString());
        user.setPassword(senhaCadastro.getText().toString());

        auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Falha na autenticação!!!"+task.getException().toString(), Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Logado!!!", Toast.LENGTH_LONG).show();
                            Intent it = new Intent(Login.this, MainActivity.class);
                            startActivity(it);
                            finish();
                        }

                    }
                });

    }

    public void botaoCadastro(View view){
        Intent it = new Intent(Login.this, Cadastro.class);
        startActivity(it);
    }

}
