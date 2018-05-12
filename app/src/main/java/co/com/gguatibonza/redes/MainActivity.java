package co.com.gguatibonza.redes;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button ingresar, button, download, okhttp;
    private DownloadManager downloadManager;
    private Usuario user;
    private EditText usuario, contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = findViewById(R.id.loginUsuario);
        user = new Usuario();
        contrasena = findViewById(R.id.loginContrasena);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }
        }

        ingresar = findViewById(R.id.loginIngresar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View vista = view;
                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.0.100:8000/api/usuarios").newBuilder();
                urlBuilder.addQueryParameter("cuentaUsuario", usuario.getText().toString());
                urlBuilder.addQueryParameter("contrasenaUsuario", contrasena.getText().toString());
                String url = urlBuilder.build().toString();
                Log.e("url", url);

                OkHttpClient cliente = new OkHttpClient();

                Request request = new Request.Builder().url(url).build();

                cliente.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String rta = response.body().string();
                            Log.e("rta", rta);
                            if (response.code() == 404) {
                                user = null;
                            } else {
                                user = new Gson().fromJson(rta, Usuario.class);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (user == null) {
                                        Snackbar.make(vista, "Usuario no encontrado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Intent i = new Intent(getApplicationContext(), Home.class);
                                        i.putExtra("usuario", user);
                                        startActivity(i);
                                        finish();
                                    }

                                }
                            });

                        }
                    }
                });
            }
        });
    }
}



