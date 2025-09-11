package uniandes.dpoo.aerolinea.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class Vuelo
{
    private String fecha;       
    private Ruta ruta;
    private Avion avion;

    private List<Tiquete> tiquetes;

    public Vuelo( String fecha, Ruta ruta, Avion avion )
    {
        this.fecha = fecha;
        this.ruta = ruta;
        this.avion = avion;
        this.tiquetes = new ArrayList<Tiquete>( );
    }

    public String getFecha( )
    {
        return fecha;
    }

    public Ruta getRuta( )
    {
        return ruta;
    }

    public Avion getAvion( )
    {
        return avion;
    }

    public Collection<Tiquete> getTiquetes( )
    {
        return tiquetes;
    }

    public void agregarTiquete( Tiquete t )
    {
        this.tiquetes.add( t );
    }

    public int getCuposOcupados( )
    {
        return tiquetes.size( );
    }

    public int getCapacidad( )
    {
        return avion.getCapacidad( );
    }
}