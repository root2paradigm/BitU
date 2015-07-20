package com.hackindia.bitu;

import android.app.DownloadManager;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.lang.reflect.Method;

/**
 * Created by Vivek on 7/19/2015.
 */
public class BalanceFragment extends Fragment {
    View view;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_balance, container, false);
        final TextView coinBalance=(TextView)view.findViewById(R.id.balance_balance);
        final TextView coinBids = (TextView)view.findViewById(R.id.balance_bids);
        final TextView coinAddres = (TextView)view.findViewById(R.id.balance_address);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://192.168.43.14:12346";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("GOT STING", response);
                        try {

                            JSONObject temp = new JSONObject(response);
                            coinBalance.setText(temp.getJSONArray("result").get(0).toString());
                            coinAddres.setText(temp.getJSONArray("result").get(1).toString());
                            coinBids.setText(temp.getJSONArray("result").get(2).toString());
                        }
                        catch (Exception e)
                        {
                            Log.v("R",e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return view;
    }
}