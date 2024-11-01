package eaut.it.mobileappdev.externalstoragedemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    private EditText editText;
    private boolean writable = false;
    private boolean readOnly = false;
    private boolean notReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText_data);

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // External Storage có sẵn và có thể ghi
            writable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // External Storage chỉ đọc
            readOnly = true;
        } else {
            // External Storage không sẵn sàng
            notReady = true;
        }
    }

    public void savePublicly(View view) {
        if (!writable) {
            Toast.makeText(this, "cannot write to external storage", Toast.LENGTH_LONG).show();
            return;
        }
        // Requesting Permission to access External Storage
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_PERMISSION_CODE);

        String editTextData = editText.getText().toString();

        //get folder to write
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, Constants.EXTERNAL_STORAGE_FILENAME);
        writeTextData(file, editTextData);
        editText.setText("");
    }

    public void savePrivately(View view) {
        String editTextData = editText.getText().toString();

        //path to folder inside Android/data/data/app_package_name/
        File folder = getExternalFilesDir(Constants.INTERNAL_DIRECTORY);

        //create file in folder
        File file = new File(folder, Constants.INTERNAL_STORAGE_FILENAME);
        writeTextData(file, editTextData);
        editText.setText("");
    }

    public void viewInformation(View view) {
        Intent intent = new Intent(MainActivity.this, ViewInformationActivity.class);
        startActivity(intent);
    }

    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}