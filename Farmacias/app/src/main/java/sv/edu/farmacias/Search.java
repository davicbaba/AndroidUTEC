package sv.edu.farmacias;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import sv.edu.farmacias.Adapters.ProductListViewAdapter;
import sv.edu.farmacias.Helper.DatabaseHelper;
import sv.edu.farmacias.Model.Multimedia;
import sv.edu.farmacias.Model.Producto;
import sv.edu.farmacias.Model.ProductoFarmacia;
import sv.edu.farmacias.Model.UbicacionUsuario;

public class Search extends AppCompatActivity {

    private DatabaseHelper _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        _db = new DatabaseHelper(getBaseContext());

        UbicacionUsuario ubicacion = _db.getUbicacionUsuario();

        if(ubicacion == null){
            Intent intent = new Intent(this, MainActivity.class);
            Toast.makeText(getBaseContext(), "Se requiere que brindes la ubicacion para poder acceder a la busqueda de productos.", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            return;
        }

        //List<Farmacia> farmacias = _db.obtenerFarmaciasEnRango(ubicacion.getLatitud(), ubicacion.getLongitud(), 100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        // Habilita el botón de retroceso
        if (getSupportActionBar() != null) { // Valida que la ActionBar no sea nula
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        List<Producto> productos = new ArrayList<Producto>();
        productos.add(new Producto(1,
                                   "ACETAMINOFEN BAYER 500MG X 100 TABLETAS",
                                   new ArrayList<ProductoFarmacia>(),
                                   new ArrayList<Multimedia>()));
        productos.add(new Producto(2,"ACETAMINOFEN FORTE X 16 TABLETAS", new ArrayList<ProductoFarmacia>(), new ArrayList<Multimedia>()));
        productos.add(new Producto(3,"ACETOSIL INFANTIL JARABE FRASCO 60ML(Acetaminofen)",  new ArrayList<ProductoFarmacia>(), new ArrayList<Multimedia>()));

        ListView listView = findViewById(R.id.lst_productos);
        ProductListViewAdapter customAdapter = new ProductListViewAdapter(this, productos);
        listView.setAdapter(customAdapter);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}