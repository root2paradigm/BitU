package com.hackindia.bitu;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;


public class BranchActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        final TextView sendbutton = (TextView)findViewById(R.id.sendlinkbutton);
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText)findViewById(R.id.editText2);
                JSONObject mydata = new JSONObject();
                try {
                    mydata.put("Ammount",et.getText().toString());
                    mydata.put("UserId","TestUser");
                }
                catch (Exception e)
                {
                    Log.v("error",e.getMessage());
                }
                Branch testBranch = Branch.getInstance(getApplicationContext());
                testBranch.getShortUrl("bitutestapp", "text", Branch.FEATURE_TAG_SHARE, "USER", mydata, new Branch.BranchLinkCreateListener() {
                    @Override
                    public void onLinkCreate(String s, BranchError branchError) {
                        if(branchError ==null)
                        {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Check Out BitU!: "+s);
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        }
                        else
                        {
                            Log.v("Eroor",branchError.getMessage());
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_branch, menu);
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
}
