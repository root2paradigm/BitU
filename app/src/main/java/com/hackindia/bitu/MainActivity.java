package com.hackindia.bitu;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;


public class MainActivity extends ActionBarActivity {

    public static DrawerLayout mainDrawer;
    ActionBarDrawerToggle mainDrawerToggle;
    public static Toolbar mainToolbar;
    public int listDrawable[] = {R.drawable.drawericon_user,R.drawable.drawericon_transaction,R.drawable.drawericon_send,R.drawable.drawericon_order,R.drawable.drawericon_pay};
    public String list[] = {"Account","Balance","Transfer","Bid","Pay"};
    public ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mainToolbar.setTitle("BitU");
        setSupportActionBar(mainToolbar);
        mainDrawer = (DrawerLayout) findViewById(R.id.mainDrawerLayout);
        drawerList = (ListView)findViewById(R.id.drawer_list);
        drawerList.setAdapter(new MyAdapter());
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        mainDrawerToggle = new ActionBarDrawerToggle(this,mainDrawer,mainToolbar,R.string.openDrawer,R.string.closeDrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }



        }; // Drawer Toggle Object Made
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_content_frame, new AccountFragment());
        ft.commit();
        mainDrawer.setDrawerListener(mainDrawerToggle); // Drawer Listener set to the Drawer toggle
        mainDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
    public class MyAdapter extends BaseAdapter{
        int position;
        public int getCount()
        {
            return 5;
        }
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.list_item, parent, false);
            }
            ImageView icon = (ImageView)convertView.findViewById(R.id.listImage);
            TextView listText = (TextView)convertView.findViewById(R.id.listText);
            icon.setImageResource(listDrawable[position]);
            listText.setText(list[position]);
            return convertView;
        }
        public long  getItemId(int lol)
        {
            return 0;
        }
        public Object getItem(int position) {

            return null;
        }
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:getFragmentManager().beginTransaction().replace(R.id.main_content_frame,new AccountFragment()).commit();
                        break;
                    case 1:getFragmentManager().beginTransaction().replace(R.id.main_content_frame,new BalanceFragment()).commit();
                        break;
                    case 2:getFragmentManager().beginTransaction().replace(R.id.main_content_frame,new TransferFragment()).commit();
                        break;
                    case 3:getFragmentManager().beginTransaction().replace(R.id.main_content_frame, new BidFragment()).commit();
                        break;
                    case 4:getFragmentManager().beginTransaction().replace(R.id.main_content_frame,new PayFragment()).commit();
                        break;
                }
                mainDrawer.closeDrawers();
                getSupportActionBar().setTitle(list[position]);
        }
    }
    public void onStart() {
        super.onStart();

        Branch branch = Branch.getInstance();
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    // params are the deep linked params associated with the link that the user clicked before showing up
                    Log.i("BranchConfigTest", "deep link data: " + referringParams.toString());
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }
}
