package dich.luong.dong.com.call;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dich.luong.dong.com.call.model.MessageModel;
import dich.luong.dong.com.call.utils.DBManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageViewHistory;
    private EditText editTextPhone;
    private EditText editTextMessage;
    private Button buttonCall;
    private Button buttonMessage;
    private String phone;
    private String message;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initData();
    }

    private void initData() {
        dbManager = new DBManager(this);
    }

    private void initViews() {
        imageViewHistory = findViewById(R.id.img_history);
        editTextPhone = findViewById(R.id.edt_phone);
        editTextMessage = findViewById(R.id.edt_message);
        buttonCall = findViewById(R.id.btn_phone);
        buttonMessage = findViewById(R.id.btn_message);
        imageViewHistory.setOnClickListener(this);
        buttonCall.setOnClickListener(this);
        buttonMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_history:
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_phone:
                phone = editTextPhone.getText().toString();
                if (Build.VERSION.SDK_INT < 23) {
                    phoneCall();
                } else {

                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        phoneCall();
                    } else {
                        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                        //Asking request Permissions
                        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
                    }
                }
                break;
            case R.id.btn_message:
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

                    makeSend();
                } else {
                    final String[] PERMISSIONS_STORAGE = {Manifest.permission.SEND_SMS};
                    //Asking request Permissions
                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 10);
                }


                break;
        }
    }

    private void makeSend() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            phone = editTextPhone.getText().toString();
            message = editTextMessage.getText().toString();

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, message, null, null);
                dbManager.addSave(new MessageModel("Phone: "+phone, getCurrentTimeUsingCalendar()));
                Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "SMS faild, please try again later!",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "You don't assign per" +
                    "mission.", Toast.LENGTH_SHORT).show();
        }
    }

    public String getCurrentTimeUsingCalendar() {

        Calendar cal = Calendar.getInstance();

        Date date = cal.getTime();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        String formattedDate = dateFormat.format(date);

        return formattedDate;

    }

    private void phoneCall() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phone));
            dbManager.addSave(new MessageModel("Phone: "+phone, getCurrentTimeUsingCalendar()));
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 9:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    phoneCall();
                } else {
                    Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
                }
                break;
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeSend();
                } else {
                    Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
