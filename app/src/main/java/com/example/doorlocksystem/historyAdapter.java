package com.example.doorlocksystem;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class historyAdapter extends ArrayAdapter<cardH> {

    private Context mContext;
    private int mResource;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView Time, Id, Direc, Res;
    }

    /**
     * Default constructor for the PersonListAdapter
     * //     *
     * //     * @param context
     * //     * @param resource
     * //     * @param objects
     * //
     */
    public historyAdapter(Context context, int resource, ArrayList<cardH> items) {
        super(context, resource, items);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String Time = getItem(position).getTime(),
                Id = getItem(position).getId(),
                Direc = getItem(position).getDirec(),
                Res = getItem(position).getRes();


        cardH cardH = new cardH(Time, Id, Direc,Res);

        final View result;

        //ViewHolder object
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.Time = (TextView) convertView.findViewById(R.id.time);
            holder.Id = (TextView) convertView.findViewById(R.id.IdNum);
            holder.Direc = (TextView) convertView.findViewById(R.id.direction);
            holder.Res = (TextView) convertView.findViewById(R.id.Result);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.Time.setText(cardH.getTime());
        holder.Id.setText(cardH.getId());
        holder.Direc.setText(cardH.getDirec());
        holder.Res.setText(cardH.getRes());

        return convertView;
    }
}
