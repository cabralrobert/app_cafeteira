package br.ufc.robertcabral.menulateral;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.widget.ProfilePictureView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String idUsuario = null, nomeUsuario;
    TextView nameMenu, emailMenu;
    CircleImageView profilepic;
    User usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        usuario = (User) bundle.getSerializable("usuario");
        idUsuario = usuario.id;
        nomeUsuario = usuario.nome;



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ProfilePictureView mImage = (ProfilePictureView) findViewById(R.id.profilepic);
                mImage.setVisibility(View.INVISIBLE);
                mImage.setDrawingCacheEnabled(true);
                mImage.setProfileId(idUsuario);
                Bitmap bitmap = mImage.getDrawingCache();

                profilepic = (CircleImageView) findViewById(R.id.profilePicture);
                profilepic.setImageBitmap(bitmap);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ProfilePictureView mImage = (ProfilePictureView) findViewById(R.id.profilepic);
                mImage.setVisibility(View.INVISIBLE);

                mImage.setDrawingCacheEnabled(true);
                mImage.setProfileId(idUsuario);

                //profilepic.setProfileId(idUsuario);
                nameMenu = (TextView)findViewById(R.id.nameMenu);
                emailMenu = (TextView)findViewById(R.id.emailMenu);
                nameMenu.setText(nomeUsuario);
                emailMenu.setText(idUsuario);


                Bitmap bitmap = mImage.getDrawingCache();

                profilepic = (CircleImageView) findViewById(R.id.profilePicture);
                profilepic.setImageBitmap(bitmap);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(drawer.isDrawerOpen(GravityCompat.START)){

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        return super.onMenuOpened(featureId, menu);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            User usuario = new User(idUsuario,nomeUsuario);
            Intent it = new Intent(MainActivity.this, QRcode.class);
            it.putExtra("usuario", usuario);
            startActivity(it);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
