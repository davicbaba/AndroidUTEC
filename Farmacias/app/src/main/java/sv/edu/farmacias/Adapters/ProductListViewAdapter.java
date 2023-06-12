package sv.edu.farmacias.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import sv.edu.farmacias.Model.Producto;
import sv.edu.farmacias.Model.ProductoFarmacia;
import sv.edu.farmacias.R;

public class ProductListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Producto> items;

    public ProductListViewAdapter(Context context, List<Producto> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto clickedProduct = items.get(position);

                Toast.makeText(context, clickedProduct.getNombre(), Toast.LENGTH_SHORT).show();

            }
        });

        Producto currentProduct = (Producto) getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imgproducto);
        TextView txtPrecio = convertView.findViewById(R.id.txtPrecio);
        TextView txtNombre = convertView.findViewById(R.id.txtNombreProducto);



        String precio = GetPrecioMasBarato(currentProduct);


        Picasso.get().load("https://fasani.b-cdn.net/productos/ecommerce/A104009.png").into(imageView);

        //imageView.setImageResource(currentProduct.getImage());
        txtPrecio.setText(precio);
        txtNombre.setText(currentProduct.getNombre());
        return convertView;
    }

    private String GetPrecioMasBarato(Producto currentProduct){
        Optional<ProductoFarmacia> masBarato = currentProduct.getProductoFarmacia().stream().min(Comparator.comparing(ProductoFarmacia::getPrecioActual));

        if(masBarato.isPresent() == false)
            return null;

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        double precioMasBarato = masBarato.get().getPrecioActual();

        return formatter.format(precioMasBarato);

    }



}