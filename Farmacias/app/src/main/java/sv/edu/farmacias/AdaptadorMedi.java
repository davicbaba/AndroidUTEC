package sv.edu.farmacias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorMedi extends BaseAdapter {
    Context contexto;
    int layout;
    List<String> nombre;
    List<String> precio;

    int[]imagenes;

    public AdaptadorMedi(Context contexto) {
        this.contexto = contexto;
    }

    public AdaptadorMedi(Context contexto, int layout, List<String> nombre, List<String> precio, int[] imagenes) {
        this.contexto = contexto;
        this.layout = layout;
        this.nombre = nombre;
        this.precio = precio;
        this.imagenes = imagenes;
    }
    public int getCount() {
        return nombre.size();
    }

    @Override public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        LayoutInflater layoutInflater = LayoutInflater.from(contexto);
        v = layoutInflater.inflate(R.layout.elementos,null);

        TextView txtnombre = v.findViewById(R.id.textView);
        txtnombre.setText(nombre.get(i));

        //DESCRIPCION
        TextView txtprecio = v.findViewById(R.id.textView2);
        txtprecio.setText(precio.get(i));

        //fotos
        ImageView img = v.findViewById(R.id.imageView);
        img.setImageResource(imagenes[i]);
        return v;
    }


}
