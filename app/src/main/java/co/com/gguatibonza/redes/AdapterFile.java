package co.com.gguatibonza.redes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterFile extends RecyclerView.Adapter<AdapterFile.ViewHolderDatos> implements View.OnClickListener {

    private ArrayList<Archivo> listaFile;
    private View.OnClickListener listener;

    public AdapterFile(ArrayList<Archivo> listaFile) {
        this.listaFile = listaFile;
    }

    @Override
    public AdapterFile.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fileitem, null, false);
        myView.setOnClickListener(this);
        return new ViewHolderDatos(myView);
    }

    @Override
    public void onBindViewHolder(AdapterFile.ViewHolderDatos holder, int position) {
        holder.titulo.setText(listaFile.get(position).getNombre());
        switch (listaFile.get(position).getExtension()) {
            case ".pdf":
                holder.logo.setImageResource(R.drawable.pdf);
                break;
            case ".docx":
                holder.logo.setImageResource(R.drawable.doc);
                break;
            case ".xlsx":
                holder.logo.setImageResource(R.drawable.xls);
                break;
            case ".xls":
                holder.logo.setImageResource(R.drawable.xls);
                break;
            case ".ppt":
                holder.logo.setImageResource(R.drawable.ppt);
                break;
            case ".mp3":
                holder.logo.setImageResource(R.drawable.mp3);
                break;
            case ".txt":
                holder.logo.setImageResource(R.drawable.txt);
                break;
            case ".png":
                holder.logo.setImageResource(R.drawable.png);
                break;
            case ".jpg":
                holder.logo.setImageResource(R.drawable.jpg);
                break;
            case ".jpeg":
                holder.logo.setImageResource(R.drawable.jpg);
                break;
            case ".rar":
                holder.logo.setImageResource(R.drawable.zip);
                break;
            case ".zip":
                holder.logo.setImageResource(R.drawable.zip);
                break;
            default:
                holder.logo.setImageResource(R.drawable.file);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listaFile.size();
    }

    public void setOnclick(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);

        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        ImageView logo;
        TextView titulo;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            titulo = itemView.findViewById(R.id.tituloArchivo);

        }
    }
}