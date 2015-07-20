package com.hackindia.bitu;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Vivek on 7/19/2015.
 */
public class BidFragment extends Fragment {
    View view;
    Handler h;
    Runnable r1;
    int gotresponse;
    String response="";
    public static JSONObject bidData;
    ListView list;
    MyAdapter lol;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bid, container, false);
        //RequestQueue queue = Volley.newRequestQueue(getActivity());
        //String url ="http://192.168.2.30:12347/getlink";
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Waiting for server response");
        progressDialog.show();
        list = (ListView)view.findViewById(R.id.bid_list);
        lol = new MyAdapter();
        list.setAdapter(lol);

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                gotresponse=0;
                String url="http://192.168.43.14:12346/bid"; //fill this url later and then append the totalamount parameter
                //String url="http://darideepacheck.appspot.com";

                response="some shit"; //may be consider changing it
                HttpClient httpClient=new DefaultHttpClient();
                ResponseHandler responseHandler=new BasicResponseHandler();
                HttpGet httpGet=new HttpGet(url);
                try {
                    //response=httpClient.execute(httpGet).toString();
                    response=httpClient.execute(httpGet,responseHandler).toString();
                    gotresponse=1;
                } catch (Exception e) {
                    gotresponse=0;
                    e.printStackTrace();
                }

            }
        });

        t.start();

        h=new Handler();
        r1=new Runnable() {
            @Override
            public void run() {
                if(gotresponse==1){
                    progressDialog.cancel();
                    progressDialog.dismiss();
                    Log.d("response received:",response);
                    try
                    {
                        bidData = new JSONObject(response);
                    }
                    catch (Exception E)
                    {
                        Log.v("json convert error",E.getMessage());
                    }
                    lol.notifyDataSetChanged();
                }

                else h.postDelayed(this,200);
            }
        };
        h.postDelayed(r1,500);



        return view;
    }
    public class MyAdapter extends BaseAdapter{
        public int getCount()
        {
            Log.v("GETCOUNT","OHT");
            try {
                return (bidData.getJSONArray("result").length()-1)/3;
            }
            catch (Exception e)
            {
                return 0;
            }
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.v("Adaptor","ingetView");
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.bid_item, parent, false);
            }
                TextView rate =(TextView)convertView.findViewById(R.id.rate);
                TextView volume =(TextView)convertView.findViewById(R.id.volume);
                TextView sum =(TextView)convertView.findViewById(R.id.sum);
                try {
                    rate.setText(bidData.getJSONArray("result").get(position*3).toString());
                    volume.setText(bidData.getJSONArray("result").get(position * 3 + 1).toString());
                    sum.setText(bidData.getJSONArray("result").get(position * 3 + 2).toString());

                }
                catch (Exception e)
                {
                    Log.e("JSON Eroor",e.getMessage());
                }
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
}