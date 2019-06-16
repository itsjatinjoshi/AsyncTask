package com.example.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText etNrTimes;
    Button btnSubmit;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNrTimes= findViewById(R.id.etNrTimes);
        btnSubmit= findViewById(R.id.btnSubmit);
        tvResult= findViewById(R.id.tvResult);
        tvResult.setVisibility(View.INVISIBLE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             int nrOfTimes= Integer.parseInt(etNrTimes.getText().toString().trim());
             new BackgroundExwcution().execute(nrOfTimes);
            }
        });
    }

    public class BackgroundExwcution extends AsyncTask<Integer, Integer, String>
    {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          dialog = new ProgressDialog(MainActivity.this);
          dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
           dialog.setMax(Integer.parseInt(etNrTimes.getText().toString().trim()));
           dialog.show();
        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.setProgress(values[0]);

        }

        @Override
        protected String doInBackground(Integer... params) {

            int ones=0, twos=0, threes=0, fours=0, fives=0, sixs=0, randomNumer;

            Random random= new Random();
            String result;
            double currentProgess=0;
            double previousProgress=0;

            for (int i=0; i< params[0]; i++){
                currentProgess=(double) i/params[0];
                if(currentProgess-previousProgress>=0.02){
                    publishProgress(i);
                    previousProgress=currentProgess;
                }


                randomNumer= random.nextInt(6)+1;
                switch (randomNumer){
                    case 1: ones++;
                        break;
                    case 2: twos++;
                        break;
                    case 3: threes++;
                        break;
                    case 4: fours++;
                        break;
                    case 5: fives++;
                        break;
                    default:sixs++;
                }
            }

            result= "Result: \n1: " +ones +"\n2: " + twos + "\n3: "+ threes + "\n4: "+ fours + "\n5: " + fives + "\n6: "
                    +sixs;

            return result;

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            tvResult.setText(s);
            tvResult.setVisibility(View.VISIBLE);

            Toast.makeText(MainActivity.this, "Process Done!", Toast.LENGTH_SHORT).show();
        }

    }


}
