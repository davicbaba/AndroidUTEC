package sv.edu.farmacias.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sv.edu.farmacias.Model.Farmacia;
import sv.edu.farmacias.Model.UbicacionUsuario;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Farmacias";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder createTableFarmacia = new StringBuilder()
                                                .append("CREATE TABLE Farmacia (")
                                                .append("    id INTEGER PRIMARY KEY AUTOINCREMENT, ")
                                                .append("    nombre TEXT NOT NULL, ")
                                                .append("    telefono TEXT NOT NULL, ")
                                                .append("    latitud REAL NOT NULL, ")
                                                .append("    longitud REAL NOT NULL")
                                                .append(")");


        // Ejecutar las consultas para crear el índice espacial y cargar la extensión Spatialite
        db.execSQL("SELECT CreateSpatialIndex('Farmacia', 'latitud', 'longitud')");
        db.execSQL("SELECT load_extension('mod_spatialite')");

        StringBuilder createTableProducto = new StringBuilder()
                                                .append("CREATE TABLE Producto (")
                                                .append("    codigo INTEGER PRIMARY KEY AUTOINCREMENT, ")
                                                .append("    nombre TEXT NOT NULL ")
                                                .append(")");

        StringBuilder createTableProductoFarmacia = new StringBuilder()
                .append("CREATE TABLE ProductoFarmacia (")
                .append("    idProducto INTEGER, ")
                .append("    idFarmacia INTEGER, ")
                .append("    disponiblidad INTEGER, ")
                .append("    precioActual REAL, ")
                .append("    precioNormal REAL, ")
                .append("    PRIMARY KEY (idProducto, idFarmacia)")
                .append("    FOREIGN KEY (idProducto) REFERENCES Producto(codigo)")
                .append("    FOREIGN KEY (idFarmacia) REFERENCES Farmacia(id)")
                .append(")");

        StringBuilder createTableMultimedia = new StringBuilder()
                .append("CREATE TABLE Multimedia (")
                .append("    codigo INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append("    Url TEXT NOT NULL, ")
                .append("    esPrincipal BOOLEAN, ")
                .append("    orden INTEGER, ")
                .append("    idProducto INTEGER, ")
                .append("    FOREIGN KEY (idProducto) REFERENCES Producto(codigo)")
                .append(")");

        StringBuilder createTableUbicacionUsuario = new StringBuilder()
                .append("CREATE TABLE UbicacionUsuario (")
                .append("    id INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append("    latitud REAL NOT NULL, ")
                .append("    longitud REAL NOT NULL")
                .append(")");

        db.execSQL(createTableFarmacia.toString());
        db.execSQL(createTableProducto.toString());
        db.execSQL(createTableProductoFarmacia.toString());
        db.execSQL(createTableMultimedia.toString());
        db.execSQL(createTableUbicacionUsuario.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public UbicacionUsuario getUbicacionUsuario() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM UbicacionUsuario LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        UbicacionUsuario ubicacionUsuario = null;
        if (cursor.moveToFirst()) {
            double latitud = cursor.getDouble(cursor.getColumnIndex("latitud"));
            double longitud = cursor.getDouble(cursor.getColumnIndex("longitud"));
            Integer codigo = cursor.getInt(cursor.getColumnIndex("id"));

            ubicacionUsuario = new UbicacionUsuario(latitud, longitud, codigo);
        }

        cursor.close();
        db.close();

        return ubicacionUsuario;
    }

    public void crearUbicacionUsuario(UbicacionUsuario ubicacionUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Eliminar registros existentes
        db.delete("UbicacionUsuario", null, null);

        ContentValues values = new ContentValues();
        values.put("latitud", ubicacionUsuario.getLatitud());
        values.put("longitud", ubicacionUsuario.getLongitud());

        db.insert("UbicacionUsuario", null, values);
        db.close();
    }
    public void eliminarUbicacionUsuario() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("UbicacionUsuario", null, null);
        db.close();
    }

    public void actualizarUbicacionUsuario(UbicacionUsuario ubicacionUsuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("latitud", ubicacionUsuario.getLatitud());
        values.put("longitud", ubicacionUsuario.getLongitud());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(ubicacionUsuario.getCodigo())};

        db.update("UbicacionUsuario", values, whereClause, whereArgs);
        db.close();
    }

    public List<Farmacia> obtenerFarmaciasEnRango(double latitud, double longitud, double radio) {
        List<Farmacia> farmacias = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener las farmacias dentro del rango especificado
        String query = "SELECT id, nombre, telefono, latitud, longitud " +
                "FROM Farmacia " +
                "WHERE STDistance(STPointFromText('POINT(' || ? || ' ' || ? || ')', 4326), geom) <= ?";

        String[] selectionArgs = {String.valueOf(longitud), String.valueOf(latitud), String.valueOf(radio)};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String telefono = cursor.getString(cursor.getColumnIndex("telefono"));
                double farmaciaLatitud = cursor.getDouble(cursor.getColumnIndex("latitud"));
                double farmaciaLongitud = cursor.getDouble(cursor.getColumnIndex("longitud"));

                Farmacia farmacia = new Farmacia(id, nombre, telefono, farmaciaLatitud, farmaciaLongitud);
                farmacias.add(farmacia);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return farmacias;
    }


}
