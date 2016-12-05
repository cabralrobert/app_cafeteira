package br.ufc.robertcabral.menulateral;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by robertcabral on 03/12/16.
 */
public class BibliotecaAll {
    public static String PREF = "br.ufc.robertcabral.menulateral.PREF";
    private static DatabaseReference firebase;

    public BibliotecaAll() {}

    public static DatabaseReference getFirebase(){
        if(firebase == null) {
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }

    static public void saveSP(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(PREF, context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    static public String getSP(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(PREF, context.MODE_PRIVATE);
        String token = sp.getString(key, "");

        return token;
    }
}
