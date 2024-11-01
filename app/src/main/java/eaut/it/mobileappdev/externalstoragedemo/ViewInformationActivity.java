package eaut.it.mobileappdev.externalstoragedemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;

public class ViewInformationActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_information);

        textView = findViewById(R.id.textView_get_saved_data);
    }

    public void showPublicData(View view) {
        // Accessing the saved data from the downloads folder
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File file = new File(folder, Constants.EXTERNAL_STORAGE_FILENAME);
        String data = getdata(file);
        if (data != null) {
            textView.setText(data);
        } else {
            textView.setText(Constants.NO_DATA);
        }
    }

    public void showPrivateData(View view) {
        File folder = getExternalFilesDir(Constants.INTERNAL_DIRECTORY);
        File file = new File(folder, Constants.INTERNAL_STORAGE_FILENAME);
        String data = getdata(file);
        if (data != null) {
            textView.setText(data);
        } else {
            textView.setText(Constants.NO_DATA);
        }
    }

    public void back(View view) {
        Intent intent = new Intent(ViewInformationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private String getdata(File myfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuffer buffer = new StringBuffer();
            while ((i = fileInputStream.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}