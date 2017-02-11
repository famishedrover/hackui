package com.example.android.wildcards;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.id.progress;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FABToolbarLayout layout;
    private static final int REQUEST_CALL = 1;
    TextView one ;
    TextView mtitle , mcontent ;
    private ProgressDialog progress;
    final String str2="100";
    View bottomSheet ;
    private BottomSheetBehavior mBottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progress = new ProgressDialog(this);
        progress.setMessage("Loading");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        layout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        final FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fabtoolbar_fab) ;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.show();
            }
        });

        one=(TextView)findViewById(R.id.running) ;
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int permission = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        // We don't have permission so prompt the user
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.CALL_PRIVILEGED},
                                REQUEST_CALL);
                    }


                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+str2));
                    startActivity(callIntent);
                } catch (SecurityException e) {
                    Log.e("myphone dialer", "Call failed", e);
                }
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button check=(Button)findViewById(R.id.button_check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Depression_TestActivity.class);

                startActivity(i);
            }
        });



        bottomSheet = findViewById( R.id.bottom_sheet );
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(300);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });

        mtitle=(TextView)findViewById(R.id.title)  ;
        mcontent=(TextView)findViewById(R.id.content) ;
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetBehavior.setPeekHeight(300);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        CNAPIInterface apiService = Client.getService();
        Call<ArrayList<EventResponse>> call = apiService.getEvents();
        call.enqueue(new Callback<ArrayList<EventResponse>>() {


            @Override
            public void onResponse(Call<ArrayList<EventResponse>> call, Response<ArrayList<EventResponse>> response) {
                if (response.isSuccessful()) {
                    progress.dismiss();
                    EventResponse e = response.body().get(0);
                    String c=e.getContent() ;
                    String t=e.getTitle() ;

                    mtitle.setText(t+"");
                    mcontent.setText(Html.from
                            Html(c)+" ");

                }
                else {
                    progress.dismiss();
                    //Snackbar.make(navigationView, "Weak/No Internet Connection", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EventResponse>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(MainActivity.this , "Weak/No Internet Connection :/" , Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            layout.hide();
            //super.onBackPressed();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_cause) {
            Intent intent=new Intent(MainActivity.this ,CausesActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
