package com.example.demoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText maindata, skipcount;
    private Button submit;
    private String substring = "", result = "", main = "", finalres = "";
    private Boolean cancel = false;
    private View focusView = null;
    private int n;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maindata = findViewById(R.id.maindata);
        skipcount = findViewById(R.id.skipcount);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try
        {
            if(v == submit)
            {
                substring ="";
                result = "";
                main = "";
                finalres = "";
                if(TextUtils.isEmpty(maindata.getText().toString()))
                {
                    maindata.setError(getString(R.string.error_field_required));
                    focusView = maindata;
                    cancel = true;
                }
                else if(TextUtils.isEmpty(skipcount.getText().toString()))
                {
                    skipcount.setError(getString(R.string.error_field_required));
                    focusView = skipcount;
                    cancel = true;
                }
                else
                {
                    main = maindata.getText().toString().trim();
                    n = Integer.parseInt(skipcount.getText().toString().trim());

                    reversingData();
                }
            }
        }
        catch(Exception ex)
        {
            Log.e("error",ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void reversingData()
    {
        String[] split = main.split("\\.");
        for(int i = 0; i<= split.length-1;i++)
        {
            substring = split[i];
            int count = 0;

            char ch[]= new char[substring.length()];
            for(int k=0;k<substring.length();k++)
            {
                ch[k]= substring.charAt(k);
                if( ((k>0)&&(ch[k]!=' ')&&(ch[k-1]==' ')) || ((ch[0]!=' ')&&(k==0)) )
                {
                    count++;
                }
            }
            Log.e("condition", count+"::"+n);
            if(count>n)
            {
                Log.e("substring", substring);
                Pattern pattern = Pattern.compile("\\s");
                String[] temp = pattern.split(substring);

                for (int m = temp.length - (n+1); m >=0; m--) {
                    Log.e("temp[m] 1st", temp[m]);
                    finalres = finalres.concat(temp[m]+" ");
                    Log.e("finalres 1st", finalres);
                }
                for (int x = count-n; x <count; x++) {
                    Log.e("temp[x] 2d", temp[x]);
                    finalres = finalres.concat(temp[x]+" ");
                    Log.e("finalres 2d", finalres);
                }
                finalres = finalres.substring(0, finalres.length() - 1).concat(".");
            }
            else
            {
                finalres = finalres.concat(substring).concat(".");
            }

            //System.out.println(finalres);
        }
        alertConfirm(finalres);
    }

    private void alertConfirm(String finalres)
    {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.app_name);

        builder.setMessage(finalres);
        builder.setIcon(R.drawable.yellow_alert_icon);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //builder.setIcon(R.drawable.success);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        dialog.show();
    }
}