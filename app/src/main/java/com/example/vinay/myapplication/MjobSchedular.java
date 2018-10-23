package com.example.vinay.myapplication;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import com.example.vinay.myapplication.MainActivity;

public class MjobSchedular extends JobService {
   // MainActivity o=new MainActivity();

    private static final boolean TODO = true;
    private MjobExecuter mjobExecuter;
    //private MainActivity mainActivity;
    String ss;


//    MainActivity mainActivity=new MainActivity();

    @Override
    public boolean onStartJob(final JobParameters params) {

          //  mainActivity=(MainActivity)con



          //   mainActivity.configure_button();

        mjobExecuter = new MjobExecuter() {

            @Override
            protected void onPostExecute(String s) {

                MainActivity.getInstance().configure_button();


             //   Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
               //Toast.makeText(MjobSchedular.this,"\n " + location.getLongitude() + " " + location.getLatitude(),Toast.LENGTH_LONG).show();
                jobFinished(params, false);
            }
        };

        mjobExecuter.execute();

        return true;
    }



    @Override
    public boolean onStopJob(JobParameters params) {
        mjobExecuter.cancel(true);
        return true;
    }
}


