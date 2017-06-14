package com.example.imdou.altp1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by imdou on 6/11/2017.
 */

public class CustomAdapter extends ArrayAdapter<Player_score> {

    public CustomAdapter(Context context, int resource, List<Player_score> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        View v = convertView;

        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_listview,null);
        }

        Player_score player_score = getItem(position);
        if (player_score != null){
            TextView txtname = (TextView) v.findViewById(R.id.txtname);
            TextView txtscore = (TextView) v.findViewById(R.id.txtscore);
            txtname.setText(player_score.name);
            txtscore.setText(player_score.score + "");
        }
        return v;
    }
}
