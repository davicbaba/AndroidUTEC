package sv.edu.farmacias.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import sv.edu.farmacias.Model.Multimedia;
import sv.edu.farmacias.Model.Producto;
import sv.edu.farmacias.Model.ProductoFarmacia;
import sv.edu.farmacias.ProductDetail;
import sv.edu.farmacias.R;
import sv.edu.farmacias.Search;

public class ProductListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Producto> items;

    private double distance;
    public ProductListViewAdapter(Context context, List<Producto> items, double distance) {
        this.context = context;
        this.items = items;
        this.distance = distance;
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

                Intent intent = new Intent(v.getContext(), ProductDetail.class);
                String codigo = String.valueOf(clickedProduct.getCodigo());
                intent.putExtra("idproducto", codigo);
                intent.putExtra("distance", String.valueOf(distance));
                v.getContext().startActivity(intent);

            }
        });

        Producto currentProduct = (Producto) getItem(position);

        ImageView imageView = convertView.findViewById(R.id.imgproducto);
        TextView txtPrecio = convertView.findViewById(R.id.txtPrecio);
        TextView txtNombre = convertView.findViewById(R.id.txtNombreProducto);

        String precio = GetPrecioMasBarato(currentProduct);

        Multimedia multimediaPrincipal = currentProduct.getMultimedia()
                .stream()
                .filter(Multimedia:: getEsPrincipal)
                .findFirst()
                .orElse(null);

     if(multimediaPrincipal != null)
        Picasso.get().load(multimediaPrincipal.getUrl()).into(imageView);

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
