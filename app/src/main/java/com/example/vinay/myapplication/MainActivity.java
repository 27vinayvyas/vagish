package com.example.vinay.myapplication;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    private static final int JOB_ID=101;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    private LocationManager locationManager;
    private LocationListener listener;
    private Button b;
    private static MainActivity instance;

    private MobileServiceClient mClient;

    Item Ite=new Item();
   // private TextView t;
String ss="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance =this;

     Ite.Text="juniyad";
        mClient.getTable(Item.class).insert(Ite, new TableOperationCallback<Ite>() {
            public void onCompleted(Item entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    // Insert succeeded
                } else {
                    // Insert failed
                }
            }
        });



        try {
            mClient = new MobileServiceClient(
                    "https://myapplication6.azurewebsites.net",
                    this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        b = (Button) findViewById(R.id.button);



        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
              //  t.append("\n " + location.getLongitude() + " " + location.getLatitude());
                Toast.makeText(MainActivity.this,"\n " + location.getLongitude() + " " + location.getLatitude(),Toast.LENGTH_LONG).show();
               // ss+="\n " + location.getLongitude() + " " + location.getLatitude();

//                Toast.makeText(MainActivity.this,"grth",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

       // configure_button();


        ComponentName componentName=new ComponentName(this,MjobSchedular.class);

        JobInfo.Builder builder=new JobInfo.Builder(JOB_ID,componentName);

        builder.setPeriodic(5*1000);
       // builder.setPersisted(true);

        jobInfo=builder.build();

        jobScheduler=(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        Button btn1=(Button)findViewById(R.id.btn1);
        Button btn2=(Button)findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(MainActivity.this,"scheduled",Toast.LENGTH_LONG).show();
                jobScheduler.schedule(jobInfo);


            }

        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobScheduler.cancel(JOB_ID);
                Toast.makeText(MainActivity.this,"cancelled",Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    public static MainActivity getInstance(){
        return instance;
    }


    public void configure_button(){

        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
    //    b.setOnClickListener(new View.OnClickListener() {

      //      @Override
        //    public void onClick(View view) {

                // first check for permissions
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                                ,10);
                    }
                    return;
                }

                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 0, 0, listener);
          //  }
        //});
    }


}