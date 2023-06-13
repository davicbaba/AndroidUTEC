package sv.edu.farmacias;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import sv.edu.farmacias.Adapters.FarmaciaListViewAdapter;
import sv.edu.farmacias.Adapters.ProductListViewAdapter;
import sv.edu.farmacias.Helper.DatabaseHelper;
import sv.edu.farmacias.Model.Farmacia;
import sv.edu.farmacias.Model.Multimedia;
import sv.edu.farmacias.Model.Producto;
import sv.edu.farmacias.Model.ProductoFarmacia;
import sv.edu.farmacias.Model.UbicacionUsuario;

public class ProductDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        DatabaseHelper db = new DatabaseHelper(getBaseContext());

        UbicacionUsuario ubicacionUsuario = db.getUbicacionUsuario();

        Producto producto = db.GetById(1);

        List<Integer> idsFarmacias = producto.getProductoFarmacia()
                                             .stream()
                                             .map(x -> x.getIdFarmacia())
                                             .collect(Collectors.toList());

        List<Farmacia> farmacias =db.getFarmacias();

        farmacias = farmacias.stream().filter(x -> idsFarmacias.contains(x.getCodigo())).collect(Collectors.toList());

        ListView listView = findViewById(R.id.listafarmacias);
        FarmaciaListViewAdapter customAdapter = new FarmaciaListViewAdapter(this, farmacias,producto,ubicacionUsuario);
        listView.setAdapter(customAdapter);


        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list

        List<Multimedia> medias = producto.getMultimedia();

        Collections.sort(medias, Comparator.comparing(Multimedia::getOrden));

        for (Multimedia multimedia : producto.getMultimedia()) {
            imageList.add(new SlideModel(multimedia.getUrl(), null, null));
        }

        ImageSlider imageSlider = findViewById(R.id.productimagecarrousel);
        imageSlider.setImageList(imageList);


        // Obtén la ActionBar
        ActionBar actionBar = getSupportActionBar();

        // Habilita el botón de retroceso en la ActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                // Realiza la acción deseada aquí, como volver a la Activity anterior
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}