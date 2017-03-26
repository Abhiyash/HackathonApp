package com.example.abhiyash.hackathonapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Tb1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    EditText e1;
    Button b1;
    String s1="",s2="";
    String url="http://10.0.2.2:48278/WebApplication1/NewWebService?WSDL";
    String ns="http://abc/";
    String mname="fetchbagno";
    String SOAP_ACTION="http://abc/fetchbagno";
    Firebase fb,fb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tb1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        e1=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button4);
        b1.setOnClickListener(this);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Firebase.setAndroidContext(this);
        fb=new Firebase("https://hackathonapp-52b62.firebaseio.com/");
        fb1=new Firebase("https://hackathonapp-52b62.firebaseio.com/ticketandbagno");
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
        getMenuInflater().inflate(R.menu.tb1, menu);
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

    @Override
    public void onClick(View v) {
        s1=e1.getText().toString();
            check();
        //This code is for webservice
        /*
        try{
            SoapObject msg=new SoapObject(ns,mname);
            msg.addProperty("ticket_id",s1);
            SoapSerializationEnvelope ev=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            ev.setOutputSoapObject(msg);
            HttpTransportSE ttp=new HttpTransportSE(url);
            try{
                ttp.call(SOAP_ACTION,ev);
                SoapPrimitive sp=(SoapPrimitive)ev.getResponse();
                String s2="";
                s2+=sp.toString();
                if(s2.equals("Record not Found"))
                {
                    Toast.makeText(Tb1.this, "Record not Found", Toast.LENGTH_SHORT).show();
                }
                else
                {
                   Intent it=new Intent(Tb1.this,Tb2.class);
                    startActivity(it);
                }
            }
            catch (Exception e)
            {
                Toast.makeText(Tb1.this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(Tb1.this, ""+e, Toast.LENGTH_SHORT).show();
        }*/

    }
    public void check(){
        //int flag=0;
        try {
            com.firebase.client.Query q = fb1.orderByKey();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int flag=0;
                    for(DataSnapshot sn:dataSnapshot.getChildren())
                    {
                         Ticketidchecker t1=sn.getValue(Ticketidchecker.class);
                           String s2=t1.getTicket_id();
                        if(flag==0)
                        {
                            if (s2.equals(s1))
                            {
                                Intent it1 = new Intent(Tb1.this, Tb2.class);
                                it1.putExtra("baggage_id", t1.baggage_id);
                                startActivity(it1);
                                flag++;
                                break;

                            }



                        }

                        System.out.println(flag);
                    }
                    if(flag==0)
                    {
                        Toast.makeText(Tb1.this, "Invalid ticke id", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
             catch (Exception e) {
            Toast.makeText(Tb1.this, "" + e, Toast.LENGTH_SHORT).show();
        }

    }
}
