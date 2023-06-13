package sv.edu.farmacias.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import sv.edu.farmacias.Model.Farmacia;
import sv.edu.farmacias.Model.Multimedia;
import sv.edu.farmacias.Model.Producto;
import sv.edu.farmacias.Model.ProductoFarmacia;
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
                                                .append("    id INTEGER PRIMARY KEY , ")
                                                .append("    nombre TEXT NOT NULL, ")
                                                .append("    telefono TEXT NOT NULL, ")
                                                .append("    latitud REAL NOT NULL, ")
                                                .append("    longitud REAL NOT NULL")
                                                .append(")");


        // Ejecutar las consultas para crear el índice espacial y cargar la extensión Spatialite
        //db.execSQL("SELECT CreateSpatialIndex('Farmacia', 'latitud', 'longitud')");
       // db.execSQL("SELECT load_extension('mod_spatialite')");

        StringBuilder createTableProducto = new StringBuilder()
                                                .append("CREATE TABLE Producto (")
                                                .append("    codigo INTEGER PRIMARY KEY, ")
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
        db.execSQL("insert into producto(codigo,nombre) values(1,'ACETAMINOFEN BAYER 500MG X 100 TABLETAS')");
        db.execSQL("insert into producto(codigo,nombre) values(2,'ACETAMINOFEN FORTE X 16 TABLETAS')");
        db.execSQL("insert into producto(codigo,nombre) values(3,'ACETOSIL INFANTIL JARABE FRASCO 60ML(Acetaminofen)')");
        db.execSQL("insert into producto(codigo,nombre) values(4,'HIBUPROFENO')");


        List<Farmacia> farmacias = new ArrayList<Farmacia>();
        farmacias.add(new Farmacia(1,"San Nicolas Mall San Gabriel","2555-5555", 13.7936244,-89.2309555 ));

        InsertarFarmacias(db, farmacias);

        db.execSQL("insert into Multimedia(Url, esPrincipal, orden,idProducto) values ('https://fasani.b-cdn.net/productos/ecommerce/A109323.jpg?class=Medium', 1 , 0, 1)");
        db.execSQL("insert into ProductoFarmacia(idProducto, idFarmacia, disponiblidad,precioActual, precioNormal) values (1,1,1,6.80,8.00)");


        db.execSQL("insert into ProductoFarmacia(idProducto,idFarmacia,disponiblidad,precioActual,precioNormal) values(2,1,1,10,10)");
    }

    private void InsertarProducto(SQLiteDatabase db,List<Producto> productos) {

        // Recorrer la lista de farmacias
        for (Producto producto : productos) {
            // Crear un nuevo mapa de valores, donde los nombres de las columnas son las claves
            ContentValues values = new ContentValues();
            values.put("codigo", producto.getCodigo());
            values.put("nombre", producto.getNombre());

            // Insertar la nueva fila, el valor de retorno es el valor de la clave primaria
            long newRowId = db.insert("producto", null, values);
        }
    }


    private void InsertarMultimedia(SQLiteDatabase db,List<Multimedia> multimedias) {

        // Recorrer la lista de farmacias
        for (Multimedia multimedia : multimedias) {
            // Crear un nuevo mapa de valores, donde los nombres de las columnas son las claves
            ContentValues values = new ContentValues();
            values.put("Url", multimedia.getUrl());
            values.put("esPrincipal", multimedia.getEsPrincipal());
            values.put("orden", multimedia.getOrden());
            values.put("idProducto", multimedia.getIdProducto());

            // Insertar la nueva fila, el valor de retorno es el valor de la clave primaria
            long newRowId = db.insert("Multimedia", null, values);
        }
    }

    private void InsertarProductoFarmacia(SQLiteDatabase db,List<ProductoFarmacia> productoFarmacias) {

        // Recorrer la lista de farmacias
        for (ProductoFarmacia productoFarmacia : productoFarmacias) {
            // Crear un nuevo mapa de valores, donde los nombres de las columnas son las claves
            ContentValues values = new ContentValues();
            values.put("idProducto", productoFarmacia.getIdProducto());
            values.put("idFarmacia", productoFarmacia.getIdFarmacia());
            values.put("disponiblidad", productoFarmacia.getDisponibilidad());
            values.put("precioActual", productoFarmacia.getPrecioActual());
            values.put("precioNormal", productoFarmacia.getPrecioNormal());


            // Insertar la nueva fila, el valor de retorno es el valor de la clave primaria
            long newRowId = db.insert("ProductoFarmacia", null, values);
        }
    }


    private void InsertarFarmacias(SQLiteDatabase db,List<Farmacia> farmacias) {

        // Recorrer la lista de farmacias
        for (Farmacia farmacia : farmacias) {
            // Crear un nuevo mapa de valores, donde los nombres de las columnas son las claves
            ContentValues values = new ContentValues();
            values.put("id", farmacia.getCodigo());
            values.put("nombre", farmacia.getNombre());
            values.put("telefono", farmacia.getTelefono());
            values.put("latitud", farmacia.getLatitud());
            values.put("longitud", farmacia.getLongitud());

            // Insertar la nueva fila, el valor de retorno es el valor de la clave primaria
            long newRowId = db.insert("Farmacia", null, values);
        }

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

    public List<Farmacia> obtenerFarmaciasEnRango(double latitud, double longitud, double distanciaEnKilometros) {
        // Convertir la distancia a grados
        double distanciaEnGrados = distanciaEnKilometros / 111;

        // Crear una lista para almacenar los resultados
        List<Farmacia> farmacias = new ArrayList<>();

        // Obtener la base de datos
        SQLiteDatabase db = getReadableDatabase();

        // Definir la consulta SQL
        String query = "SELECT * FROM Farmacia WHERE latitud BETWEEN ? AND ? AND longitud BETWEEN ? AND ?";

        // Definir los parámetros de la consulta
        String[] params = new String[] {
                String.valueOf(latitud - distanciaEnGrados),
                String.valueOf(latitud + distanciaEnGrados),
                String.valueOf(longitud - distanciaEnGrados),
                String.valueOf(longitud + distanciaEnGrados)
        };

        // Realizar la consulta
        Cursor cursor = db.rawQuery(query, params);

        // Procesar los resultados
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
            String telefono = cursor.getString(cursor.getColumnIndex("telefono"));
            double latitudFarmacia = cursor.getDouble(cursor.getColumnIndex("latitud"));
            double longitudFarmacia = cursor.getDouble(cursor.getColumnIndex("longitud"));

            // Crear un objeto Farmacia y añadirlo a la lista
            Farmacia farmacia = new Farmacia(id, nombre,telefono ,latitudFarmacia, longitudFarmacia);
            farmacias.add(farmacia);
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        return farmacias;
    }

    public List<Producto> GetProducts(String search,String idsFarmacias) {
        List<Producto> productos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener las farmacias dentro del rango especificado
        String query = "SELECT producto.codigo, producto.nombre " +
                "FROM producto " +
                "INNER JOIN ProductoFarmacia ON producto.codigo = ProductoFarmacia.idProducto " +
                "WHERE producto.nombre LIKE '%"+search+"%' AND ProductoFarmacia.disponiblidad > 0 " +
                " AND ProductoFarmacia.idFarmacia IN ("+idsFarmacias+") " +
                " GROUP BY producto.codigo, producto.nombre";

        String[] selectionArgs = {};
        Cursor cursor = db.rawQuery(query,selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                int codigo = cursor.getInt(cursor.getColumnIndex("codigo"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));

                Producto producto = new
                        Producto(codigo, nombre,new ArrayList<ProductoFarmacia>(),new ArrayList<Multimedia>());

                productos.add(producto);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productos;
    }

    public Producto GetById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Producto WHERE codigo=?", new String[]{String.valueOf(id)});

        if (cursor != null)
            cursor.moveToFirst();

        Producto producto = new Producto(
                cursor.getInt(0), // id del producto
                cursor.getString(1), // nombre del producto
                new ArrayList<>(), // productoFarmacia
                new ArrayList<>() // multimedia
        );
        cursor.close();

        List<Multimedia> multimedias = GetMultimediaProductos(id);

        List<ProductoFarmacia> productoFarmacias = GetProductoFarmacia(id);
        producto.setMultimedia(multimedias);
        producto.setProductoFarmacia(productoFarmacias);
        return producto;
    }


    public List<Multimedia> GetMultimediaProductos(int idProducto) {
        List<Multimedia>  multimedias = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener las farmacias dentro del rango especificado
        String query = "SELECT * from Multimedia where idProducto ="+idProducto+" ";

        String[] selectionArgs = {};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                int codigo = cursor.getInt(cursor.getColumnIndex("codigo"));
                String url = cursor.getString(cursor.getColumnIndex("Url"));
                int principal = cursor.getInt(cursor.getColumnIndex("esPrincipal"));
                boolean esPrincipal = principal == 1 ? true : false;
                int orden =cursor.getInt(cursor.getColumnIndex("orden"));

                Multimedia multimedia = new
                        Multimedia(codigo,url,esPrincipal,orden,idProducto);

                multimedias.add(multimedia);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return multimedias;
    }

    public List<ProductoFarmacia> GetProductoFarmacia(int idProducto) {
        List<ProductoFarmacia>  productosFarmacia = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener las farmacias dentro del rango especificado
        String query = "SELECT * from ProductoFarmacia where idProducto ="+idProducto+" ";

        String[] selectionArgs = {};
        Cursor cursor = db.rawQuery(query,selectionArgs);
        if (cursor.moveToFirst()) {
            do {
                int idFarmacia = cursor.getInt(cursor.getColumnIndex("idFarmacia"));
                int disponiblidad = cursor.getInt(cursor.getColumnIndex("disponiblidad"));
                double precioActual = cursor.getDouble(cursor.getColumnIndex("precioActual"));
                double precioNormal = cursor.getDouble(cursor.getColumnIndex("precioNormal"));

                ProductoFarmacia productoFarmacia = new ProductoFarmacia(idProducto,idFarmacia, disponiblidad, precioActual, precioNormal);

                productosFarmacia.add(productoFarmacia);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();


        return productosFarmacia;
    }

    public List<Farmacia> getFarmacias() {
        List<Farmacia> farmacias = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta para obtener las farmacias dentro del rango especificado
         String query = "SELECT * from Farmacia";

        String[] selectionArgs = {};
        Cursor cursor = db.rawQuery(query,selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String telefono = cursor.getString(cursor.getColumnIndex("telefono"));
                double latitudFarmacia = cursor.getDouble(cursor.getColumnIndex("latitud"));
                double longitudFarmacia = cursor.getDouble(cursor.getColumnIndex("longitud"));

                // Crear un objeto Farmacia y añadirlo a la lista
                Farmacia farmacia = new Farmacia(id, nombre,telefono ,latitudFarmacia, longitudFarmacia);
                farmacias.add(farmacia);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return farmacias;
    }

}
