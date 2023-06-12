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

import sv.edu.farmacias.Model.Farmacia;
import sv.edu.farmacias.Model.Producto;
import sv.edu.farmacias.Model.ProductoFarmacia;
import sv.edu.farmacias.R;

public class FarmaciaListViewAdapter  extends BaseAdapter
{
    private Context context;
    private List<Farmacia> items;

    private Producto producto;

    public FarmaciaListViewAdapter(Context context, List<Farmacia> items, Producto producto) {
        this.context = context;
        this.items = items;
        this.producto = producto;
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

        Farmacia farmacia = (Farmacia) getItem(position);

        TextView txtPrecio = convertView.findViewById(R.id.txtPrecioProductoEnFarmacia);
        TextView txtTelefonoFarmacia  = convertView.findViewById(R.id.txtTelefonoFarmacia);
        TextView txtNombreFarmacia = convertView.findViewById(R.id.txtNombreFarmacia);

        String precio = GetPrecioEnFarmacia(producto, farmacia);


        txtPrecio.setText(precio);
        txtTelefonoFarmacia.setText(farmacia.getTelefono());
        txtNombreFarmacia.setText(farmacia.getNombre());

        return convertView;
    }

    private String GetPrecioEnFarmacia(Producto currentProduct, Farmacia farmacia){
        Optional<ProductoFarmacia> precioFarmacia = currentProduct.getProductoFarmacia().stream().filter(x -> x.getIdFarmacia() == farmacia.getCodigo()).findFirst();

        if(precioFarmacia.isPresent() == false)
            return null;

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        double precioMasBarato = precioFarmacia.get().getPrecioActual();

        return formatter.format(precioMasBarato);

    }
}
