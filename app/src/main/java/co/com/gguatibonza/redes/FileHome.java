package co.com.gguatibonza.redes;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FileHome extends Fragment {
    private Usuario user;
    private ImageButton refresh;
    private ArrayList<Archivo> archivos;
    private RecyclerView recycler;
    private AdapterFile adapter;
    private LinearLayout layout;
    private Button eliminar, descargar;
    private String id;

    public FileHome() {
        // Required empty public constructor
    }

    public static FileHome newInstance() {
        FileHome fragment = new FileHome();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user = (Usuario) bundle.getSerializable("usuario");
        }
        cargar();
    }

    private void cargar() {
        archivos = new ArrayList<>();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.0.100:8000/api/archivos").newBuilder();
        urlBuilder.addQueryParameter("idUsuario", user.getId());
        String url = urlBuilder.build().toString();


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String rta = response.body().string();
                    Log.e("rta", rta);
                    Type listType = new TypeToken<ArrayList<Archivo>>() {
                    }.getType();
                    archivos = new Gson().fromJson(rta, listType);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter = new AdapterFile(archivos);
                            recycler.setAdapter(adapter);
                            adapter.setOnclick(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    layout.setVisibility(View.VISIBLE);
                                    id = archivos.get(recycler.getChildAdapterPosition(view)).getId();
                                }
                            });
                        }
                    });

                }
            }


        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_home, container, false);
        recycler = view.findViewById(R.id.archivos);
        refresh = view.findViewById(R.id.refres);
        layout = view.findViewById(R.id.layout);
        descargar = view.findViewById(R.id.descargar);
        eliminar = view.findViewById(R.id.eliminar);


        descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.100:8000/api/archivos/" + id));
                startActivity(browserIntent);
                layout.setVisibility(View.INVISIBLE);

            }
        });


        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout.setVisibility(View.INVISIBLE);
                final View vista = view;
                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://192.168.0.100:8000/api/archivos/" + id).newBuilder();
                String url = urlBuilder.build().toString();
                OkHttpClient cliente = new OkHttpClient();

                Request request = new Request.Builder().url(url)
                        .delete()
                        .build();

                cliente.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String rta = response.body().string();

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    cargar();
                                }
                            });

                        }
                    }
                });


            }
        });


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargar();
            }
        });
        return view;
    }


}
