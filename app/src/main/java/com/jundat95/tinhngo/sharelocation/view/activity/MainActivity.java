package com.jundat95.tinhngo.sharelocation.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.jundat95.tinhngo.sharelocation.R;
import com.jundat95.tinhngo.sharelocation.until.TiSharedPreferences;
import com.jundat95.tinhngo.sharelocation.view.fragment.MapFragment;
import com.jundat95.tinhngo.sharelocation.view.temp.Login1Activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.jundat95.tinhngo.sharelocation.ShareLocationApp.context;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private MapFragment mapFragment;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;

    private LatLng latLng = null;
    private Retrofit retrofit;
    private List<String> categories;

    private static final Integer LOCATION = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //getKey();
        // set up toolbar
        setSupportActionBar(toolbar);

        initNavigationDrawer();

        addMap(savedInstanceState);

        addFloatActionButton();

        getItemType();

        hideLogin();


    }

    // Get key hash map 5, for facebook developer
    private void getKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.jundat95.tinhngo.sharelocation",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("--------KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    private void startGPS(){
        //get position
        latLng = mapFragment.getPosition();
        if(latLng == null){
            turnOnGPS();
        }
    }

    // Add du lieu
    private void addFloatActionButton() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                latLng = mapFragment.getPosition();
                if(latLng == null){
                    Toast.makeText(MainActivity.this,"Please On GPS",Toast.LENGTH_LONG).show();
                    turnOnGPS();

                }else {

                    Intent intentPostSubject = new Intent(MainActivity.this,PostSubjectActivity.class);
                    Gson gson = new Gson();
                    String position = gson.toJson(latLng);
                    Bundle bundle = new Bundle();
                    bundle.putString("POSITION",position);

                    intentPostSubject.putExtras(bundle);
                    startActivity(intentPostSubject);
                }

            }
        });
    }

    private void turnOnGPS(){
        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus != true)
        {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);

        }else {
            askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
            mapFragment.enablePermissionLocation();
            // Restart fragment
            replaceFragment(mapFragment);
        }

    }

    // Turn on permission location
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            //Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    private void addMap(Bundle savedInstanceState) {
        mapFragment = MapFragment.newInstanse(this);
        if(savedInstanceState == null){
            fragmentManager = getFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_map,mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    private void replaceFragment(Fragment fragment){
        if(fragment != null){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_map,fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id){

                    case R.id.about  :
                        Toast.makeText(getApplicationContext(),"About",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.my_subject:
                        Toast.makeText(getApplicationContext(),"My Subject",Toast.LENGTH_SHORT).show();
                        Intent demo = new Intent(MainActivity.this,SubjectPostedListActivity.class);
                        startActivity(demo);
                        //setTitle("Home");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.post_subject  :
                        //Toast.makeText(getApplicationContext(),"Post Subject",Toast.LENGTH_SHORT).show();
                        Intent intentPostSubject = new Intent(MainActivity.this,PostSubjectActivity.class);
                        Gson gson = new Gson();
                        String position = gson.toJson(latLng);
                        Bundle bundle = new Bundle();
                        bundle.putString("POSITION",position);

                        intentPostSubject.putExtras(bundle);
                        startActivity(intentPostSubject);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.login:
                        Intent intentLogin = new Intent(MainActivity.this,Login1Activity.class);
                        intentLogin.putExtra("MAIN_ACTIVITY",true);
                        startActivity(intentLogin);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout  :
                        Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
                        if(TiSharedPreferences.getSharedPreferences(getApplicationContext(),"AccessToken") != null){
                            LoginManager.getInstance().logOut();
                            TiSharedPreferences.removeSharedPreferences(getApplicationContext(),"AccessToken");
                            TiSharedPreferences.removeSharedPreferences(getApplicationContext(),"IdUser");
                            TiSharedPreferences.removeSharedPreferences(getApplication(),"Email");
                            hideLogin();
                        }
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.exit  :
                        drawerLayout.closeDrawers();
                        finish();
                        break;

                }

                return  true;

            }
        });

        View header = navigationView.getHeaderView(0);
        TextView txtEmail = (TextView) header.findViewById(R.id.tv_email);
        if(TiSharedPreferences.getSharedPreferences(getApplicationContext(),"Email") != null){
            txtEmail.setText(TiSharedPreferences.getSharedPreferences(getApplicationContext(),"Email"));
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        }  ;

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    private void hideLogin(){
        NavigationView nav = (NavigationView) findViewById(R.id.navigation_view);
        Menu menu = nav.getMenu();
        if(TiSharedPreferences.getSharedPreferences(getApplicationContext(),"AccessToken") != null){
            menu.findItem(R.id.login).setVisible(false);
            menu.findItem(R.id.logout).setVisible(true);
        }else {
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.logout).setVisible(false);
        }
    }

    public void getItemType(){

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLogin();
    }





}
