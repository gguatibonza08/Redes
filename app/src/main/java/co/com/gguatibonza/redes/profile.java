package co.com.gguatibonza.redes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class profile extends Fragment {
    private Usuario user;
    private TextView nombre, correo, fecha;


    public profile() {
        // Required empty public constructor
    }

    public static profile newInstance() {
        profile fragment = new profile();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user = (Usuario) bundle.getSerializable("usuario");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nombre = view.findViewById(R.id.nombreUsuario);
        correo = view.findViewById(R.id.correoUsuario);
        fecha = view.findViewById(R.id.fechaUsuario);
        nombre.setText(user.getNombre());
        correo.setText(user.getCorreo());
        fecha.setText(user.getFechaRegistro());
        return view;
    }

}
