package com.bv.wuhuijie.record;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class RecordMainActivity extends Activity {
    private TextView timeTV;
    private TextView recordTV;
    private static final String TAG = "Record";
    int year, month, day, hour, min;
    public String storageFile;
    public FileOutputStream outputStream;
    public FileInputStream inputStream;
    public String recordTime = "记录：";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_main);
        timeTV = (TextView) findViewById(R.id.timeTView);
        recordTV = (TextView) findViewById(R.id.recordTView);
        recordTV.setMovementMethod(ScrollingMovementMethod.getInstance()); //增加支持滚动条
        final Calendar rightNow  = Calendar.getInstance(Locale.getDefault());

        getCurrentTime();
        storageFile = new String(Integer.toString(year) + Integer.toString(month)
                + Integer.toString(day ));
        recordTV.setText(recordTime);

       // File file = new File(this.getFilesDir(), storageFile);

        String string = "Hello world!\n";

        try {
            String ret = "";
            inputStream = openFileInput(storageFile);
            int len = inputStream.available();
            byte[] buf = new byte[len];
            inputStream.read(buf);
           // outputStream.write(string.getBytes());
           // outputStream.write(storageFile.getBytes());
            ret = EncodingUtils.getAsciiString(buf);
            Log.d(TAG, ret);
            recordTime = recordTime + ret;
            recordTV.setText(recordTime);

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_record_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void getCurrentTime(){
        final Calendar rightNow  = Calendar.getInstance(Locale.getDefault());
        year = rightNow.get(Calendar.YEAR);
        month = rightNow.get(Calendar.MONTH);
        if (month <= 11 && month >= 0){
            month = month + 1;
        }
        day = rightNow.get(Calendar.DAY_OF_MONTH);
        hour = rightNow.get(Calendar.HOUR_OF_DAY);
        min = rightNow.get(Calendar.MINUTE);
        timeTV.setText( Integer.toString(year) + "/" + Integer.toString(month) + "/"
                + Integer.toString(day) + "   " + Integer.toString(hour) + ": "
                + Integer.toString(min));

    }

    public void recordToFile(String strTime){
        try {
            outputStream = openFileOutput(storageFile, Context.MODE_APPEND);
            outputStream.write(strTime.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void printLog(View view){
        getCurrentTime();
        String tmpString = Integer.toString(year) + "/" + Integer.toString(month)
                + "/"+Integer.toString(day) + "   " + Integer.toString(hour)
                + ": " + Integer.toString(min);
        recordToFile("\n" + tmpString);
        Log.d(TAG, recordTime);
        recordTime = recordTime + "\n" + tmpString;
        recordTV.setText(recordTime);
       // timeTV.setText( Integer.toString(year) + "/" + Integer.toString(month) + "/"+Integer.toString(day) + "   " + Integer.toString(hour) + ": " + Integer.toString(min));
        /*
        Log.d(TAG, "year:" + year);
        Log.d(TAG, "month:" + month);
        Log.d(TAG, "day:" + day);
        Log.d(TAG, "hour:" + hour);
        */
    }
}
