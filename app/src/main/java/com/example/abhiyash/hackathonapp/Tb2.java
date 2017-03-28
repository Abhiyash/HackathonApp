package com.example.abhiyash.hackathonapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

public class Tb2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tv;
    CheckBox c1,c2,c3;
    String st="";
    Firebase fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent it=getIntent();
        Bundle ob=it.getExtras();
         st=ob.getString("baggage_id");
        //tv.setText(""+s);
        c1=(CheckBox)findViewById(R.id.checkBox);
        c2=(CheckBox)findViewById(R.id.checkBox2);
        c3=(CheckBox)findViewById(R.id.checkBox3);
        tv=(TextView)findViewById(R.id.textView2);
        Firebase.setAndroidContext(this);

        fb=new Firebase("https://hackathonapp-52b62.firebaseio.com/ScanningHistory");

       select();
       //startService(new Intent(this,MyService.class));
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
        getMenuInflater().inflate(R.menu.tb2, menu);
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

        if (id == R.id.Track_Baggage) {
            Intent it = new Intent(this, Tb1.class);
            startActivity(it);
        } else if (id == R.id.Worker_Login) {
            Intent it = new Intent(this, WL1.class);
            startActivity(it);
        } else if (id == R.id.Complaint) {
            Intent it = new Intent(this, C1.class);
            startActivity(it);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
public void select()
{
    try
    {
         com.firebase.client.Query q=fb.orderByKey();
        q.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                for(DataSnapshot sn:dataSnapshot.getChildren())
                {
                    Fetchbagno fb12=dataSnapshot.getValue(Fetchbagno.class);
                    String s2=fb12.getBaggage_id();
                    if(s2.equals(st))
                    {
                           String s3=fb12.getScanner_id();
                            if(s3.equals("21"))
                            {
                                Toast.makeText(Tb2.this, s2+""+s3, Toast.LENGTH_SHORT).show();
                                c1.setChecked(true);
                            }
                        if(s3.equals("201"))
                        {
                            Toast.makeText(Tb2.this, s2+""+s3, Toast.LENGTH_SHORT).show();
                            c2.setChecked(true);
                            // c1.isSelected(true);
                        }
                        if(s3.equals("7"))
                        {
                            Toast.makeText(Tb2.this, s2+""+s3, Toast.LENGTH_SHORT).show();
                            c3.setChecked(true);
                            // c1.isSelected(true);
                        }

                    }
                    //Toast.makeText(Tb2.this, fb12.getBaggage_id()+","+fb12.getScanner_id(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                for(DataSnapshot sn:dataSnapshot.getChildren())
                {
                    Fetchbagno fb13=dataSnapshot.getValue(Fetchbagno.class);
                    String s2=fb13.getBaggage_id();
                    if(s2.equals(st))
                    {

                            String s3=fb13.getScanner_id();
                            if(s3.equals("21"))
                            {
                                c1.setChecked(true);
                                Toast.makeText(Tb2.this, s2+""+s3, Toast.LENGTH_SHORT).show();
                                Intent it23=new Intent(Tb2.this,Tb2.class);
                                PendingIntent pd=PendingIntent.getActivities(Tb2.this,123, new Intent[]{it23},0);
                                NotificationCompat.Builder b=new NotificationCompat.Builder(Tb2.this);
                                b.setTicker("This is ticker message");
                                b.setSmallIcon(R.drawable.checked);
                                b.setContentTitle("This is title");
                                b.setAutoCancel(true);
                                b.setContentIntent(pd);
                                Notification nf=b.build();
                                NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                nm.notify(1,nf);
                                finish();
                               // c1.isSelected(true);
                            }
                        if(s3.equals("201"))
                        {
                            Toast.makeText(Tb2.this, s2+""+s3, Toast.LENGTH_SHORT).show();
                            c2.setChecked(true);
                            // c1.isSelected(true);
                        }
                        if(s3.equals("7"))
                        {
                            Toast.makeText(Tb2.this, s2+""+s3, Toast.LENGTH_SHORT).show();
                            c3.setChecked(true);
                            // c1.isSelected(true);
                        }

                    }
                 //   Toast.makeText(Tb2.this, fb13.getBaggage_id()+","+fb13.getScanner_id(), Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    catch (Exception e)
    {
        Toast.makeText(Tb2.this, ""+e, Toast.LENGTH_SHORT).show();
    }
}


}
