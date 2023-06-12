package sv.edu.farmacias;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sv.edu.farmacias.Adapters.FarmaciaListViewAdapter;
import sv.edu.farmacias.Adapters.ProductListViewAdapter;
import sv.edu.farmacias.Helper.DatabaseHelper;
import sv.edu.farmacias.Model.Farmacia;
import sv.edu.farmacias.Model.Multimedia;
import sv.edu.farmacias.Model.Producto;
import sv.edu.farmacias.Model.ProductoFarmacia;

public class ProductDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        DatabaseHelper db = new DatabaseHelper(getBaseContext());

        Producto producto = db.GetById(1);

        List<Integer> idsFarmacias = producto.getProductoFarmacia()
                                             .stream()
                                             .map(x -> x.getIdFarmacia())
                                             .collect(Collectors.toList());

        List<Farmacia> farmacias =db.getFarmacias();

        farmacias = farmacias.stream().filter(x -> idsFarmacias.contains(x.getCodigo())).collect(Collectors.toList());

        ListView listView = findViewById(R.id.listafarmacias);
        FarmaciaListViewAdapter customAdapter = new FarmaciaListViewAdapter(this, farmacias,producto);
        listView.setAdapter(customAdapter);
    }
}