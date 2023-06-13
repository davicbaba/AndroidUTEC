package sv.edu.farmacias;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import android.Manifest;
import android.widget.EditText;
import android.widget.Toast;

import sv.edu.farmacias.Helper.DatabaseHelper;
import sv.edu.farmacias.Model.UbicacionUsuario;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION_CODE = 101;
    private FusedLocationProviderClient mFusedLocationClient;

    private DatabaseHelper _db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        _db = new DatabaseHelper(getBaseContext());

        requestLocationPermission();
    }
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Se necesita permiso de ubicación")
                    .setMessage("Esta aplicación necesita el permiso de ubicación para funcionar correctamente.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION_CODE);
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {

                            double lat = location.getLatitude();
                            double lon = location.getLongitude();
                            _db.crearUbicacionUsuario(new UbicacionUsuario(lat, lon, 0));
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                // El permiso fue rechazado
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    public void OnClickSearch(View view){
        Intent intent = new Intent(this, Search.class);

        EditText search = findViewById(R.id.editTextTextPersonName4);
        EditText distancia = findViewById(R.id.txtDistanciaBuscar);

        if (TextUtils.isEmpty(distancia.getText())) {
            distancia.setError("Este campo es requerido"); // establece un mensaje de error
            return;
        }

        intent.putExtra("search", search.getText().toString());
        intent.putExtra("distance", distancia.getText().toString());
        startActivity(intent);
    }

}