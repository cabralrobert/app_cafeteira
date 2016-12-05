package br.ufc.robertcabral.menulateral;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    DatabaseReference firebase;
    EditText password, email, nome, idade;
    FirebaseAuth auth;
    User user = new User();
    protected ProgressBar progressBar;
    private int CONST = 12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
        auth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.emailUser);
        idade = (EditText)findViewById(R.id.idadeUsuario);
        nome = (EditText)findViewById(R.id.nomeUsuario);
        password = (EditText)findViewById(R.id.passwordUser);

        firebase = BibliotecaAll.getFirebase();
        progressBar = (ProgressBar)findViewById(R.id.progressBar2);
        closeProgressBar();

    }

    public void fotoPerfil(View view){
        Intent intent = new Intent(getIntent().ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CONST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == CONST){
            if(resultCode == RESULT_OK){
                Uri imagem = intent.getData();

                user.setFoto(imagem);

                ImageView img = (ImageView)findViewById(R.id.imagemPerfilCadastro);

                img.setImageURI(imagem);
            }
        }
    }

    protected void openProgressBar(){
        progressBar.setVisibility( View.VISIBLE );
    }

    protected void closeProgressBar(){
        progressBar.setVisibility( View.GONE );
    }

    public void cadastroCode(View view){

        if(user.getFoto() != null) {
            openProgressBar();
            user.setNome(nome.getText().toString());
            user.setIdade(idade.getText().toString());
            user.setEmail(email.getText().toString());
            user.setPassword(password.getText().toString());
            user.setValorConta("0");

            auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Falha", Toast.LENGTH_SHORT).show();
                            } else {
                                final FirebaseUser usuario;
                                usuario = auth.getCurrentUser();
                                firebase.child("users").child(usuario.getUid()).child("nome").setValue(user.getNome());
                                firebase.child("users").child(usuario.getUid()).child("idade").setValue(user.getIdade());
                                firebase.child("users").child(usuario.getUid()).child("valorConta").setValue(user.getValorConta());

                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReferenceFromUrl("gs://honesty-coffee.appspot.com");
                                StorageReference riversRef = storageRef.child(usuario.getUid());
                                UploadTask uploadTask = riversRef.putFile(user.getFoto());

                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                        auth.getInstance().signOut();
                                        closeProgressBar();
                                        finish();
                                        Toast.makeText(getApplicationContext(), "Cadastro com sucesso!!!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "Importe uma foto.", Toast.LENGTH_SHORT).show();
        }
    }

}
