package com.example.permissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.permissions.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //
    ActivityMainBinding binding;
    //
    private static final int REQUEST_CODE_WRITE_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //
        binding.mainBtnCheckPermi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ) {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_STORAGE);

                }
                else {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.granted_permission_writeExternalStorage_R1),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        //

    }
    //


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        boolean Inside_WRITE_EXTERNAL_STORAGE = false;
        if( requestCode == REQUEST_CODE_WRITE_STORAGE &&
                permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) )
        {
            Inside_WRITE_EXTERNAL_STORAGE = true;
        }

        if(Inside_WRITE_EXTERNAL_STORAGE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this,
                        getString(R.string.granted_permission_writeExternalStorage_R2),
                        Toast.LENGTH_SHORT).show();

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // create Alert Dialog to convince the user of the necessary of this permission
                    showingAlertDialogConvinceUser();
                }
                else {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.never_show),
                            Toast.LENGTH_SHORT).show();
                }

            }

        }

    }

    public void showingAlertDialogConvinceUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(R.string.please_permit_it)
                .setTitle(R.string.dialog_title);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_STORAGE);

            }
        });
        builder.setNegativeButton(R.string.no_thanks, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(MainActivity.this,
                        getString(R.string.Cannot_be_done),
                        Toast.LENGTH_SHORT).show();

            }
        });
        builder.show();
    }

}