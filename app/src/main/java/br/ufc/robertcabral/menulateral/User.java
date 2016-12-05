package br.ufc.robertcabral.menulateral;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

/**
 * Created by robertcabral on 11/09/16.
 */
public class User implements Serializable {

    public static String TOKEN = "br.ufc.robertcabral.menulateral.User.TOKEN";
    public String id = null;
    public String nome = null;
    public String idade = null;
    public String email = null;
    public String password = null;
    public String valorConta = null;
    public Uri foto = null;

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getValorConta() {
        return valorConta;
    }

    public Uri getFoto() {
        return foto;
    }

    public void setFoto(Uri foto) {
        this.foto = foto;
    }

    public void setValorConta(String valorConta) {
        this.valorConta = valorConta;
    }

    public void saveDB(){
        DatabaseReference firebase = BibliotecaAll.getFirebase();
        firebase = firebase.child("users").child(getId());

        setPassword(null);
        setId(null);
        firebase.setValue( this );
    }

    public void saveTokenSP(Context context, String token){
        BibliotecaAll.saveSP(context, TOKEN, token);
    }

    public String getTokenSP(Context context){
        String token = BibliotecaAll.getSP(context, TOKEN);
        return token;
    }


}
