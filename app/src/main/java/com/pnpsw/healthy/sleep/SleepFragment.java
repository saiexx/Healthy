package com.pnpsw.healthy.sleep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.pnpsw.healthy.R;



import java.util.ArrayList;

public class SleepFragment extends Fragment {

    SQLiteDatabase myDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE,null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS sleep (id INTEGER PRIMARY KEY AUTOINCREMENT, date VARCHAR(10), toBedTime VARCHAR(5), awakeTime VARCHAR(5))");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initBackBtn();
        initAddBtn();
        getAndShowData();
    }

    private void initBackBtn() {
        Button backBtn = getView().findViewById(R.id.sleep_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }
    private void initAddBtn() {
        Button addBtn = getView().findViewById(R.id.sleep_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new AddSleepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void getAndShowData() {
        Cursor cursor = myDB.rawQuery("select id, date, toBedTime, awakeTime from sleep",null);
        final ArrayList<Sleep> sleepList = new ArrayList<>();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            String toBedTime = cursor.getString(2);
            String awakeTime = cursor.getString(3);
            Sleep sleep = new Sleep();
            sleep.setId(id);
            sleep.setDate(date);
            sleep.setToBedTime(toBedTime);
            sleep.setAwakeTime(awakeTime);
            sleepList.add(sleep);
        }
        cursor.close();


        ListView sleepListView = getView().findViewById(R.id.sleep_list_view);
        SleepAdapter sleepAdapter = new SleepAdapter(getActivity(),R.layout.fragment_sleep_list_item,sleepList);
        sleepListView.setAdapter(sleepAdapter);
        sleepListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("sleep object", sleepList.get(pos));
                Fragment addSleepFragment = new AddSleepFragment();
                addSleepFragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.main_view, addSleepFragment).addToBackStack(null).commit();
            }
        });

    }




}
