package com.example.doorlocksystem;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class fruitAdapter extends ArrayAdapter<fruit> {

    private Context mContext;
    private int mResource;
    FragmentCardList fragmentCardList;
    String newCard="";
    boolean activated = false;
    fruit fruit;
    ArrayList<String> ID = new ArrayList<>();

    public fruitAdapter setFragmentCardList(FragmentCardList fragmentCardList) {
        this.fragmentCardList = fragmentCardList;
        return this;
    }

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView Id;
        EditText NewID;
        CheckBox checkBox, NewCheckBox;
        ImageButton delete, add, cancel;
        LinearLayout main, Secondary;
    }

    /**
     * Default constructor for the PersonListAdapter
     * //     *
     * //     * @param context
     * //     * @param resource
     * //     * @param objects
     * //
     */
    public fruitAdapter(Context context, int resource, ArrayList<fruit> items) {
        super(context, resource, items);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String FruitName = getItem(position).getName();
        Boolean Ckecked = getItem(position).getChecked();
        int type = getItem(position).getType();
        fruit = new fruit(FruitName, Ckecked, type);

        final View result;

        //ViewHolder object
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.Id = (TextView) convertView.findViewById(R.id.ID);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox);
            holder.delete = (ImageButton) convertView.findViewById(R.id.delete);
            holder.NewID = (EditText) convertView.findViewById(R.id.NewID);
            holder.NewID.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    newCard = s.toString();
                }
            });
            holder.NewCheckBox = (CheckBox) convertView.findViewById(R.id.NewCheckBox);
            holder.NewCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        activated=true;
                    }else{
                        activated=false;
                    }
                }
            });
            holder.cancel = (ImageButton) convertView.findViewById(R.id.cancel);
            holder.main = (LinearLayout) convertView.findViewById(R.id.main);
            holder.Secondary = (LinearLayout) convertView.findViewById(R.id.Secondary);
            holder.add = (ImageButton) convertView.findViewById(R.id.add);
            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        if (type == 3) {
            holder.main.setVisibility(View.GONE);
            holder.add.setVisibility(View.VISIBLE);
            holder.Secondary.setVisibility(View.GONE);

            holder.add.setImageResource(R.drawable.ic_save_black_24dp);
            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentCardList.save(activated,newCard);
                }
            });
        } else if (type == 2) {
            holder.main.setVisibility(View.GONE);
            holder.add.setVisibility(View.GONE);
            holder.Secondary.setVisibility(View.VISIBLE);

            holder.NewID.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentCardList.showSoftKeyboard(holder.NewID);
                }
            }, 100);

            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentCardList.cancel();
                }
            });

        } else if (type == 1) {
            holder.main.setVisibility(View.GONE);
            holder.add.setVisibility(View.VISIBLE);
            holder.Secondary.setVisibility(View.GONE);

            holder.NewID.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentCardList.hideKeyBoard(holder.NewID);
                }
            }, 100);

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentCardList.add();
                }
            });
        } else if (type == 0) {
            holder.main.setVisibility(View.VISIBLE);
            holder.add.setVisibility(View.GONE);
            holder.Secondary.setVisibility(View.GONE);

            holder.Id.setText(fruit.getName());


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentCardList.delete(position);
                }
            });

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    fragmentCardList.checked(position, isChecked);
                }
            });

            if (fruit.getChecked()) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
        }


        return convertView;
    }
}
