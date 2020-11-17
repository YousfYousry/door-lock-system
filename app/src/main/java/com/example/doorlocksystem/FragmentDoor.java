package com.example.doorlocksystem;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.doorlocksystem.MainActivity.isNum;

public class FragmentDoor extends Fragment {


    ListView History;
    ArrayList<cardH> cards = new ArrayList<>();
    ImageView DoorImage;
    boolean doorOpened = false;
    Context context;
    ImageButton DoorB;
    TextView Clear;

    public FragmentDoor setContext(Context context) {
        this.context = context;
        return this;
    }

    public void closeDoor() {
        doorOpened = false;
        DoorImage.setImageResource(R.drawable.door_closed);
    }

    public void openDoor() {
        doorOpened = true;
        DoorImage.setImageResource(R.drawable.door_opened);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.door_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getView() != null) {

            DoorB = getView().findViewById(R.id.DoorB);
            Clear = getView().findViewById(R.id.Clear);
            History = getView().findViewById(R.id.History);
            DoorImage = getView().findViewById(R.id.DoorB);

            DoorB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (doorOpened) {
                        closeDoor();
                        FirebaseDatabase.getInstance().getReference().child("Door").setValue("0");
                    } else {
                        openDoor();
                        FirebaseDatabase.getInstance().getReference().child("Door").setValue("1");
                    }
                }
            });

            Clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference().child("history").removeValue();
                }
            });

            FirebaseDatabase.getInstance().getReference().child("history").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cards.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.getKey() != null) {
                            if (isNum(ds.getKey())) {
                                Date date = new java.util.Date((long) (Integer.parseInt(ds.getKey()) + 43200) * 1000L);
                                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                                String time = sdf.format(date);

                                String cardId = "", Direction = "", Result = "";
                                if (dataSnapshot.child(ds.getKey()).child("cardId").getValue() != null)
                                    cardId = dataSnapshot.child(ds.getKey()).child("cardId").getValue().toString();
                                if (dataSnapshot.child(ds.getKey()).child("direction").getValue() != null)
                                    Direction = dataSnapshot.child(ds.getKey()).child("direction").getValue().toString();
                                if (dataSnapshot.child(ds.getKey()).child("result").getValue() != null)
                                    Result = dataSnapshot.child(ds.getKey()).child("result").getValue().toString();
                                cards.add(new cardH(time, cardId, Direction, Result));
                            }
                        }
                    }
                    historyAdapter historyAdapter = new historyAdapter(context, R.layout.history, cards);
                    History.setAdapter(historyAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            FirebaseDatabase.getInstance().getReference().child("Door").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue().toString().compareTo("1") == 0) {
                        openDoor();
                    } else if (dataSnapshot.getValue().toString().compareTo("0") == 0) {
                        closeDoor();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }
}