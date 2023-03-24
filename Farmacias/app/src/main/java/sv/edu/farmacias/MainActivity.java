package sv.edu.farmacias;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listado;
    List<String> nombre;
    List<String> precio;
    int [] img;
// esta es parte de Karla :)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listado=findViewById(R.id.lvsMedicinas);

        nombre = new ArrayList<String>();
        nombre.add("Acetaminofen 100 tabletas 500mg");
        nombre.add("Acetaminofen paracetamol tabletas 500mg");
        nombre.add("Acetaminofen 100 tabletas 50mg");
        nombre.add("Acetaminofen paracetamol jarabe sabor frutas - via oral 120ml  ");

        precio = new ArrayList<String>();
        precio.add("El precio es de 1.50");
        precio.add("El precio es de 3.50");
        precio.add("El precio es de 1.17");
        precio.add("El precio es de 2.50");


        int[] pictures = {
               R.drawable.acetaminofen,
               R.drawable.acetaminofen2,
               R.drawable.acetaminofen3,
               R.drawable.acetaminofen4,
        };

        AdaptadorMedi adap = new AdaptadorMedi(this,R.layout.elementos,nombre,precio,pictures);

        listado.setAdapter(adap);
        //llenamos el adaptador

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //VARIABLES DISTINTAS
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Selecciono "+nombre.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }
}