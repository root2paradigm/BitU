package com.hackindia.bitu;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.FadeInAnimation;
import com.easyandroidanimations.library.ScaleInAnimation;
import com.easyandroidanimations.library.SlideInAnimation;
import com.easyandroidanimations.library.SlideInUnderneathAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import io.branch.referral.Branch;

public class TransferFragment extends Fragment implements View.OnClickListener{
    View view;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transfer, container, false);
        LinearLayout linkButton = (LinearLayout)view.findViewById(R.id.linkbutton);
        LinearLayout qrButton =(LinearLayout) view.findViewById(R.id.qrbutton);
        linkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BranchActivity.class));
            }
        });
        new ScaleInAnimation(linkButton).animate();
        new ScaleInAnimation(qrButton).animate();
        return view;
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.linkbutton:
                break;
            case R.id.qrbutton:
                break;
        }
    }
}