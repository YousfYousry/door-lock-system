package com.example.doorlocksystem;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentCardList extends Fragment {
    ListView listView;
    ArrayList<fruit> fruits = new ArrayList<>();
    fruitAdapter adapter;
    FragmentCardList fragmentCardList = this;
    MainActivity mainActivity;
    Context context;

    public FragmentCardList setContext(Context context) {
        this.context = context;
        return this;
    }

    public FragmentCardList setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getView()!=null){
            listView = (ListView) getView().findViewById(R.id.List);
            FirebaseDatabase.getInstance().getReference().child("cardList").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    fruits.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.getKey() != null) {
                            if (dataSnapshot.child(ds.getKey()).getValue().toString().compareTo("1") == 0) {
                                fruits.add(new fruit(ds.getKey(), true, 0));
                            } else if (dataSnapshot.child(ds.getKey()).getValue().toString().compareTo("0") == 0) {
                                fruits.add(new fruit(ds.getKey(), false, 0));
                            }
                        }
                    }
                    fruits.add(new fruit("", true, 1));
                    adapter = new fruitAdapter(context, R.layout.card, fruits);
                    adapter.setFragmentCardList(fragmentCardList);
                    listView.setAdapter(adapter);
                    listView.setFooterDividersEnabled(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }


    public void add() {
        FirebaseDatabase.getInstance().getReference().child("cardList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fruits.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey() != null) {
                        if (dataSnapshot.child(ds.getKey()).getValue().toString().compareTo("1") == 0) {
                            fruits.add(new fruit(ds.getKey(), true, 0));
                        } else if (dataSnapshot.child(ds.getKey()).getValue().toString().compareTo("0") == 0) {
                            fruits.add(new fruit(ds.getKey(), false, 0));
                        }
                    }
                }
                fruits.add(new fruit("", true, 2));
                fruits.add(new fruit("", true, 3));
                adapter = new fruitAdapter(context, R.layout.card, fruits);
                adapter.setFragmentCardList(fragmentCardList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cancel() {
        FirebaseDatabase.getInstance().getReference().child("cardList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fruits.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getKey() != null) {
                        if (dataSnapshot.child(ds.getKey()).getValue().toString().compareTo("1") == 0) {
                            fruits.add(new fruit(ds.getKey(), true, 0));
                        } else if (dataSnapshot.child(ds.getKey()).getValue().toString().compareTo("0") == 0) {
                            fruits.add(new fruit(ds.getKey(), false, 0));
                        }
                    }
                }
                fruits.add(new fruit("", true, 1));
                adapter = new fruitAdapter(context, R.layout.card, fruits);
                adapter.setFragmentCardList(fragmentCardList);
                listView.setAdapter(adapter);
                listView.setFooterDividersEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter.newCard = "";
        adapter.activated = false;
    }


    public void checked(int pos, boolean isChecked) {
        if (isChecked) {
            FirebaseDatabase.getInstance().getReference().child("cardList").child(fruits.get(pos).getName()).setValue("1");
        } else {
            FirebaseDatabase.getInstance().getReference().child("cardList").child(fruits.get(pos).getName()).setValue("0");
        }
    }

    public void delete(int pos) {
        FirebaseDatabase.getInstance().getReference().child("cardList").child(fruits.get(pos).getName()).removeValue();
    }

    public void save(boolean activated, String NewCard) {
        if (!NewCard.isEmpty()) {
            if (activated) {
                FirebaseDatabase.getInstance().getReference().child("cardList").child(NewCard.toUpperCase()).setValue("1");
            } else {
                FirebaseDatabase.getInstance().getReference().child("cardList").child(NewCard.toUpperCase()).setValue("0");
            }
        }
        cancel();
    }

    public void showSoftKeyboard(View view) {
        mainActivity.showSoftKeyboard(view);
    }

    public void hideKeyBoard(View view) {
        mainActivity.hideKeyBoard(view);
    }

}