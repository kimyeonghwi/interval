package com.example.interval;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

                intent.putExtra("set", set);
                intent.putExtra("min_run", min_run);
                intent.putExtra("sec_run", sec_run);
                intent.putExtra("min_rest", min_rest);
                intent.putExtra("sec_rest", sec_rest);

                startActivity(intent);
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
}