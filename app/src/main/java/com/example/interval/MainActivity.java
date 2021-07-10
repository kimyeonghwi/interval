package com.example.interval;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListAdapter listAdapter;
    EditText edt_set, edt_min_run, edt_sec_run, edt_min_rest, edt_sec_rest;
    int set, min_run, sec_run, min_rest, sec_rest;
    ImageButton imgBtn, imgBtn2;
    LinearLayout linearLayout;
    boolean first = true;
    String title;
    SQLiteDatabase db;
    SQLiteOpenHelper helper;

    int version =1;
    String db_name = "INTERVAL";
    String tb_name = "interval_tb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);

        imgBtn = findViewById(R.id.imgbtn);
        edt_set = findViewById(R.id.edt_set);
        edt_min_run = findViewById(R.id.edt_min_run);
        edt_sec_run = findViewById(R.id.edt_sec_run);
        edt_min_rest = findViewById(R.id.edt_min_rest);
        edt_sec_rest = findViewById(R.id.edt_sec_rest);
        linearLayout = findViewById(R.id.linear_layout);
        imgBtn2 = findViewById(R.id.imgbtn2);

        edt_set.setFilters(new InputFilter[]{new InputFilterMinMax("1", "20")});
        edt_min_run.setFilters(new InputFilter[]{new InputFilterMinMax("0", "60")});
        edt_sec_run.setFilters(new InputFilter[]{new InputFilterMinMax("0", "60")});
        edt_min_rest.setFilters(new InputFilter[]{new InputFilterMinMax("0", "60")});
        edt_sec_rest.setFilters(new InputFilter[]{new InputFilterMinMax("0", "60")});

        listAdapter = new ListAdapter();
        listView.setAdapter(listAdapter);
        creatDB();

        Button button = findViewById(R.id.button);
        imgBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtget();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog,null);
                builder.setView(layout);
                builder.setTitle("정보입력");
                builder.setIcon(R.drawable.ic_baseline_note_alt_24);

                EditText edt_name = layout.findViewById(R.id.edt_Title);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Item item = listAdapter.list.get(position);
                        sec_run = item.getRun_sec();
                        sec_rest = item.getRest_sec();
                        min_run = item.getRun_min();
                        min_rest = item.getRest_min();
                        set = item.getSet();

                        sendIntent(set,sec_rest,sec_run,min_rest,min_run);

                    }
                });

                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        listAdapter.list.remove(position);
                        listAdapter.notifyDataSetChanged();
                        return true;
                    }
                });

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        title = edt_name.getText().toString();
                        Log.d("hi", title);
                        listAdapter.addItem(new Item(title,set,sec_run,min_run,sec_rest,min_rest));
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Startinterval.class);
                edtget();

                sendIntent(set,sec_rest,sec_run,min_rest,min_run);


            }
        });

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (first) {
                    linearLayout.setVisibility(View.GONE);
                    imgBtn.setImageResource(R.drawable.ic_down);
                    first = false;
                } else {
                    linearLayout.setVisibility(View.VISIBLE);
                    imgBtn.setImageResource(R.drawable.ic_up);
                    first = true;
                }


            }
        });

    }



    public void edtget() {

        if (edt_min_run.getText().toString().isEmpty()) {
            min_run = 0;
        } else {
            min_run = Integer.parseInt(edt_min_run.getText().toString());
        }

        if (edt_sec_run.getText().toString().equals("")) {
            sec_run = 0;
        } else {
            sec_run = Integer.parseInt(edt_sec_run.getText().toString());
        }

        if (edt_min_rest.getText().toString().equals("")) {
            min_rest = 0;
        } else {
            min_rest = Integer.parseInt(edt_min_rest.getText().toString());
        }

        if (edt_sec_rest.getText().toString().equals("")) {
            sec_rest = 0;
        } else {
            sec_rest = Integer.parseInt(edt_sec_rest.getText().toString());
        }

        set = Integer.parseInt(edt_set.getText().toString());
    }
    //디비에 생성주기
    public void creatDB(){
        helper = new DatabaseHelper(this);
        db = helper.getWritableDatabase();
    }

    //디비에 넣어주기
    public void saveDB(String title , int set , int sec_run  , int min_run , int min_rest , int sec_rest ){
        // String sql = "INSERT INTO " + tb_name + "(title , set_cycle, min_run, sec_run ,min_rest,sec_rest) VAlUES ("

    }

    //디비에 삭제하기
    public void deleteDB(String title){

    }

    public void sendIntent (int set, int sec_rest , int sec_run , int min_rest, int min_run){
        Intent  intent = new Intent(getApplicationContext(), Startinterval.class);

        intent.putExtra("set", set);
        intent.putExtra("min_run", min_run);
        intent.putExtra("sec_run", sec_run);
        intent.putExtra("min_rest", min_rest);
        intent.putExtra("sec_rest", sec_rest);

        startActivity(intent);

    }


    class DatabaseHelper extends SQLiteOpenHelper{


        public DatabaseHelper(@Nullable Context context) {
            super(context, db_name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS "+ tb_name +"(title char(10), set_cycle INTEGER ,min_run INTEGER, sec_run INTEGER, min_rest INTEGER , sec_rest INTEGER )");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}