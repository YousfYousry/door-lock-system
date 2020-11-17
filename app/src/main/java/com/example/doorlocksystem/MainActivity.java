package com.example.doorlocksystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    MainActivity mainActivity = this;


    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.Door);
            FragmentDoor fragmentDoor = new FragmentDoor();
            fragmentDoor.setContext(context);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragmentDoor).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.Door:
                            FragmentDoor fragmentDoor = new FragmentDoor();
                            fragmentDoor.setContext(context);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    fragmentDoor).commit();
                            break;
                        case R.id.CardList:
                            FragmentCardList fragmentCardList = new FragmentCardList();
                            fragmentCardList.setMainActivity(mainActivity);
                            fragmentCardList.setContext(context);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    fragmentCardList).commit();
                            break;
                    }
                    return true;
                }
            };

//    public void save(boolean activated, String NewCard) {
//        if (!NewCard.isEmpty()) {
//            if (activated) {
//                FirebaseDatabase.getInstance().getReference().child("cardList").child(NewCard.toUpperCase()).setValue("1");
//            } else {
//                FirebaseDatabase.getInstance().getReference().child("cardList").child(NewCard.toUpperCase()).setValue("0");
//            }
//        }
//        cancel();
//    }
//    public void add() {
//        FirebaseDatabase.getInstance().getReference().child("cardList").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                fruits.clear();
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    if (ds.getKey() != null) {
//                        if (dataSnapshot.child(ds.getKey()).getValue().toString().compareTo("1") == 0) {
//                            fruits.add(new fruit(ds.getKey(), true, 0));
//                        } else if (dataSnapshot.child(ds.getKey()).getValue().toString().compareTo("0") == 0) {
//                            fruits.add(new fruit(ds.getKey(), false, 0));
//                        }
//                    }
//                }
//                fruits.add(new fruit("", true, 2));
//                fruits.add(new fruit("", true, 3));
//                adapter = new fruitAdapter(getApplicationContext(), R.layout.card, fruits);
//                adapter.setMainActivity(mainActivity);
//                listView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//    public void cancel() {
//        FirebaseDatabase.getInstance().getReference().child("cardList").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                fruits.clear();
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    if (ds.getKey() != null) {
//                        if (dataSnapshot.child(ds.getKey()).getValue().toString().compareTo("1") == 0) {
//                            fruits.add(new fruit(ds.getKey(), true, 0));
//                        } else if (dataSnapshot.child(ds.getKey()).getValue().toString().compareTo("0") == 0) {
//                            fruits.add(new fruit(ds.getKey(), false, 0));
//                        }
//                    }
//                }
//                fruits.add(new fruit("", true, 1));
//                adapter = new fruitAdapter(getApplicationContext(), R.layout.card, fruits);
//                adapter.setMainActivity(mainActivity);
//                listView.setAdapter(adapter);
//                listView.setFooterDividersEnabled(false);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        adapter.newCard = "";
//        adapter.activated = false;
//    }
//    public void checked(int pos, boolean isChecked) {
//        if (isChecked) {
//            FirebaseDatabase.getInstance().getReference().child("cardList").child(fruits.get(pos).getName()).setValue("1");
//        } else {
//            FirebaseDatabase.getInstance().getReference().child("cardList").child(fruits.get(pos).getName()).setValue("0");
//        }
//    }
//    public void delete(int pos) {
//        FirebaseDatabase.getInstance().getReference().child("cardList").child(fruits.get(pos).getName()).removeValue();
//    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void hideKeyBoard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isNum(String strNum) {
        boolean ret = true;
        try {

            Integer.parseInt(strNum);

        } catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }

}
