package sv.edu.farmacias;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;
import java.util.List;
import sv.edu.farmacias.Adapters.ProductListViewAdapter;
import sv.edu.farmacias.Helper.DatabaseHelper;
import sv.edu.farmacias.Model.Farmacia;
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

        Intent intent = getIntent();
        String search = intent.getStringExtra("search");

        _db = new DatabaseHelper(getBaseContext());

        UbicacionUsuario ubicacion = _db.getUbicacionUsuario();

        if(ubicacion == null){
            Intent intentMain = new Intent(this, MainActivity.class);
            Toast.makeText(getBaseContext(), "Se requiere que brindes la ubicacion para poder acceder a la busqueda de productos.", Toast.LENGTH_SHORT).show();
            startActivity(intentMain);
        }

        CargarDatos(search,GetIdsFarmaciasByUbicacion());
    }

    private String GetIdsFarmaciasByUbicacion()
    {
        UbicacionUsuario ubicacion = _db.getUbicacionUsuario();
        String ids = "";

        List<Farmacia> farmacias =
                _db.obtenerFarmaciasEnRango(ubicacion.getLatitud(), ubicacion.getLongitud(), 100);

        for (Farmacia objeto : farmacias) {

              if(ids =="")
                ids  = "'" +objeto.getCodigo().toString() +"'";

            if(ids !="")
                ids  = ids + ",'"+  objeto.getCodigo().toString()+"'";
        }

        return ids;
    }

    private void  CargarDatos(String searchdata, String idsFarmacias)
    {
        _db = new DatabaseHelper(getBaseContext());
        List<Producto> productos = _db.GetProducts(searchdata,idsFarmacias);
        ListView listView = findViewById(R.id.lst_productos);
        ProductListViewAdapter customAdapter = new ProductListViewAdapter(this, productos);
        listView.setAdapter(customAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Intent intent = getIntent();
        String search = intent.getStringExtra("search");

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        if(search != null)
        {
            menuItem.expandActionView();
            searchView.setQuery(search, false);
            searchView.requestFocus();
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String idsFarmacias =  GetIdsFarmaciasByUbicacion();
                CargarDatos(query,idsFarmacias);
                return  true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                String idsFarmacias =  GetIdsFarmaciasByUbicacion();
                CargarDatos(newText,idsFarmacias);
                return true;
            }
        });

        // Habilita el botón de retroceso
        if (getSupportActionBar() != null) { // Valida que la ActionBar no sea nula
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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