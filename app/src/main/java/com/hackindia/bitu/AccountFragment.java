package com.hackindia.bitu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by Vivek on 7/19/2015.
 */
public class AccountFragment extends Fragment {
    View view;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        RelativeLayout rl = (RelativeLayout)view.findViewById(R.id.shar_bitaddress);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "My BitAddress: 1809982099180";
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                getActivity().startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        return view;
    }
}