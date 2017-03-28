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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WL1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
        EditText e1,e2;
        Button b1;
    String url="http://10.0.2.2:48278/WebApplication1/NewWebService?WSDL";
    String ns="http://abc/";
    String mname="workerlogin";
    String SOAP_ACTION="http://abc/workerlogin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wl1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        e1=(EditText)findViewById(R.id.editText2);
        e2=(EditText)findViewById(R.id.editText3);
        b1=(Button)findViewById(R.id.button5);
        b1.setOnClickListener(this);

        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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
        getMenuInflater().inflate(R.menu.wl1, menu);
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
        String s1="",s2="";
        s1=e1.getText().toString();
        s2=e2.getText().toString();
        try{
            SoapObject msg=new SoapObject(ns,mname);
            msg.addProperty("Worker_id",s1);
            msg.addProperty("Password",s2);
            SoapSerializationEnvelope ev=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            ev.setOutputSoapObject(msg);
            HttpTransportSE ttp=new HttpTransportSE(url);
            try{
                ttp.call(SOAP_ACTION,ev);
                SoapPrimitive sp=(SoapPrimitive)ev.getResponse();
                String s3="";
                s3+=sp.toString();
                if(s3.equals("false"))
                {
                    Toast.makeText(WL1.this, "Invalid ID or Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Intent it=new Intent(WL1.this,Tb2.class);
                    //startActivity(it);
                    Toast.makeText(WL1.this, "Correct credentials", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(WL1.this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(WL1.this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }
}
