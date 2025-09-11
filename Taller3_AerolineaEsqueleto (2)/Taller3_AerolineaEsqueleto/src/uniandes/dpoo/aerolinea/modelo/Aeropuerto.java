package uniandes.dpoo.aerolinea.modelo;

import java.util.HashSet;
import java.util.Set;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;

public class Aeropuerto
{
    private static Set<String> codigos = new HashSet<String>( );

    private String nombre;
    private String codigo;
    private double latitud;
    private double longitud;

    public Aeropuerto( String nombre, String codigo, double latitud, double longitud ) throws AeropuertoDuplicadoException
    {
        if( codigos.contains( codigo ) )
            throw new AeropuertoDuplicadoException( codigo );
        this.nombre = nombre;
        this.codigo = codigo;
        this.latitud = latitud;
        this.longitud = longitud;
        codigos.add( codigo );
    }

    public String getNombre( )
    {
        return nombre;
    }

    public String getCodigo( )
    {
        return codigo;
    }

    public double getLatitud( )
    {
        return latitud;
    }

    public double getLongitud( )
    {
        return longitud;
    }

    public static int calcularDistancia( Aeropuerto aeropuerto1, Aeropuerto aeropuerto2 )
    {
        
        double latAeropuerto1 = Math.toRadians( aeropuerto1.getLatitud( ) );
        double lonAeropuerto1 = Math.toRadians( aeropuerto1.getLongitud( ) );
        double latAeropuerto2 = Math.toRadians( aeropuerto2.getLatitud( ) );
        double lonAeropuerto2 = Math.toRadians( aeropuerto2.getLongitud( ) );

        double deltaX = ( lonAeropuerto2 - lonAeropuerto1 ) * Math.cos( ( latAeropuerto1 + latAeropuerto2 ) / 2 );
        double deltaY = ( latAeropuerto2 - latAeropuerto1 );

        double distancia = Math.sqrt( deltaX * deltaX + deltaY * deltaY ) * RADIO_TERRESTRE;

        return ( int )Math.round( distancia );
    }

}
