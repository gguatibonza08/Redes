package co.com.gguatibonza.redes;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Home extends AppCompatActivity {
    private FloatingActionButton add;
    private Bundle bundle;
    private Usuario user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            bundle.putSerializable("usuario", user);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment File = FileHome.newInstance();
                    File.setArguments(bundle);
                    transaction.replace(R.id.frameHome, File).commit();
                    return true;
                case R.id.navigation_profile:
                    Fragment Profile = profile.newInstance();
                    Profile.setArguments(bundle);
                    transaction.replace(R.id.frameHome, Profile).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = (Usuario) getIntent().getSerializableExtra("usuario");
        bundle = new Bundle();
        bundle.putSerializable("usuario", user);
        add = findViewById(R.id.addFile);

        Fragment file = FileHome.newInstance();
        file.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameHome, file).commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }
        }

        enable_button();
    }

    private void enable_button() {

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialFilePicker()
                        .withActivity(Home.this)
                        .withRequestCode(10)
                        .start();

            }
        });
    }

    ProgressDialog progress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {

            progress = new ProgressDialog(Home.this);
            progress.setTitle("Uploading");
            progress.setMessage("Please wait...");
            progress.show();

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    java.io.File f = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    String content_type = getMimeType(f.getPath());

                    String file_path = f.getAbsolutePath();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.0.100:8000/api/archivos").newBuilder();
                    String url = urlBuilder.build().toString();
                    Log.e("file", url.toString());


                    OkHttpClient client = new OkHttpClient();
                    RequestBody file_body = RequestBody.create(MediaType.parse(content_type), f);

                    RequestBody request_body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("idUsuario", user.getId())
                            .addFormDataPart("type", content_type)
                            .addFormDataPart("file", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                            .build();

                    Request request = new Request.Builder()
                            .url(url)
                            .post(request_body)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();

                        if (!response.isSuccessful()) {
                            throw new IOException("Error : " + response);
                        }

                        progress.dismiss();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });

            t.start();


        }
    }

    private String getMimeType(String path) {

        String extension = MimeTypeMap.getFileExtensionFromUrl(path);

        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}