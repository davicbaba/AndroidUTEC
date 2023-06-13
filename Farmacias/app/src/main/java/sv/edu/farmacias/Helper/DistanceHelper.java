package sv.edu.farmacias.Helper;

import sv.edu.farmacias.Model.Farmacia;
import sv.edu.farmacias.Model.UbicacionUsuario;

public class DistanceHelper {

    // Radio de la Tierra en kilómetros
    private static final double RADIO_TIERRA = 6371.0;

    //Ayuda a calcular en base a la formula Haversine
    public double GetDistancia(UbicacionUsuario ubicacionUsuario, Farmacia farmacia)
    {
        double latitudUsuario = Math.toRadians(ubicacionUsuario.getLatitud());  // Nueva York
        double longitudUsuario = Math.toRadians(ubicacionUsuario.getLongitud());
        double latitudFarmacia = Math.toRadians(farmacia.getLatitud());  // Londres
        double longitudFarmacia = Math.toRadians(farmacia.getLongitud());

        return calcularDistancia(latitudUsuario, longitudUsuario, latitudFarmacia, longitudFarmacia);

    }

    public  double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA * c;
    }
}


