package com.example.pari.olxelem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
// Inflate the layout for this fragment
        String item = getArguments().getString("item");
        String msg = item;
        View myInflatedView = inflater.inflate(R.layout.fragment_details, container,false);
        TextView TextView_msg = (TextView)myInflatedView.findViewById(R.id.textView);
        TextView_msg.setText(msg);
        return myInflatedView;
    }
}
