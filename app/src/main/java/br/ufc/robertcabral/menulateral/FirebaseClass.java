package br.ufc.robertcabral.menulateral;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by robertcabral on 03/12/16.
 */
public class FirebaseClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseReference.goOnline();
    }
}
