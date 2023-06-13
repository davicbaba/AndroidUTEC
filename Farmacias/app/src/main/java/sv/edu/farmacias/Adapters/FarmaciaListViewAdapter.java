package sv.edu.farmacias.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import sv.edu.farmacias.Helper.DistanceHelper;
import sv.edu.farmacias.Model.Farmacia;
import sv.edu.farmacias.Model.Producto;
import sv.edu.farmacias.Model.ProductoFarmacia;
import sv.edu.farmacias.Model.UbicacionUsuario;
import sv.edu.farmacias.R;

public class FarmaciaListViewAdapter  extends BaseAdapter
{
    private Context context;
    private List<Farmacia> items;

    private Producto producto;

    private UbicacionUsuario ubicacionUsuario;
    public FarmaciaListViewAdapter(Context context, List<Farmacia> items, Producto producto,UbicacionUsuario ubicacionUsuario) {
        this.context = context;
        this.items = items;
        this.producto = producto;
        this.ubicacionUsuario = ubicacionUsuario;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.farmacia_list_item, parent, false);
        }

        Farmacia farmacia = (Farmacia) getItem(position);

        TextView txtPrecio = convertView.findViewById(R.id.txtPrecioProductoEnFarmacia);
        TextView txtTelefonoFarmacia  = convertView.findViewById(R.id.txtTelefonoFarmacia);
        TextView txtNombreFarmacia = convertView.findViewById(R.id.txtNombreFarmacia);
        TextView txtDisponible = convertView.findViewById(R.id.txtDisponible);
        TextView txtDistancia = convertView.findViewById(R.id.txtDistanciakm);
        Button btnVerUbicacion = convertView.findViewById(R.id.btnverubicacion);

        btnVerUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define las coordenadas de destino
                double latitude = farmacia.getLatitud();
                double longitude = farmacia.getLongitud();

                // Crea la URI de la geolocalización
                String geoUri = "geo:" + latitude + "," + longitude;

                // Crea un intent para visualizar la geolocalización
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));

                // Verifica que haya una actividad que pueda manejar el intent
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    // Lanza la actividad
                    context.startActivity(intent);
                }
            }
        });


        Optional<ProductoFarmacia> precioFarmacia = producto.getProductoFarmacia().stream().filter(x -> x.getIdFarmacia() == farmacia.getCodigo()).findFirst();

        if(precioFarmacia.isPresent() == false || precioFarmacia.get().getDisponibilidad() <=0)
        {
            txtDisponible.setText("Agotado");
            txtDisponible.setTextColor(ContextCompat.getColor(context, R.color.red));

        }else{
            txtDisponible.setText("Disponible");
            txtDisponible.setTextColor(ContextCompat.getColor(context, R.color.green));
        }


        if(precioFarmacia.isPresent() == false)
        {
            txtDistancia.setText("0KM");
        }else{
            DistanceHelper helper = new DistanceHelper();

            double distancia = helper.GetDistancia(ubicacionUsuario,farmacia);

            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String formatedDistance = decimalFormat.format(distancia);


            txtDistancia.setText(formatedDistance + "KM");
            txtDisponible.setTextColor(ContextCompat.getColor(context, R.color.green));
        }


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

    private String GetDisponibilidad(Producto currentProduct, Farmacia farmacia){
        Optional<ProductoFarmacia> precioFarmacia = currentProduct.getProductoFarmacia().stream().filter(x -> x.getIdFarmacia() == farmacia.getCodigo()).findFirst();

        if(precioFarmacia.isPresent() == false)
            return null;

        if(precioFarmacia.get().getDisponibilidad() <=0)
            return "Disponible";

        return "Agotado";

    }
}
