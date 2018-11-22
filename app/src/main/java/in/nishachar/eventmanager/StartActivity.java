package in.nishachar.eventmanager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;

public class StartActivity extends AppCompatActivity {
    public final static String NAME = "name";

    private AppCompatSpinner spinner;
    private AppCompatTextView overview;
    private AppCompatTextView expectation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        spinner = findViewById(R.id.spinner);
        overview = findViewById(R.id.overview);
        expectation = findViewById(R.id.expectation);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 8001);

        findViewById(R.id.nextFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMainActivity();
            }
        });

        findViewById(R.id.contactButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeCall();
            }
        });

        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegisterActivity();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1) {
                    expectation.setText("Expectation Of Two");
                    overview.setText("Overview Of Two");
                } else if(position == 2) {
                    expectation.setText("Expectation Of Three");
                    overview.setText("Overview Of Three");
                } else if(position == 0) {
                    expectation.setText("Expectation Of One");
                    overview.setText("Overview Of One");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void placeCall(){
        new AlertDialog.Builder(this)
                .setTitle("Place Call?")
                .setMessage("Do you want to call 3334444")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = "tel:3334444";
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                        if (!checkCallPermission())
                            startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void callMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(NAME, spinner.getSelectedItem().toString());

        // options need to be passed when starting the activity
        startActivity(intent);
    }

    private void callRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private Boolean checkCallPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED;
    }
}
