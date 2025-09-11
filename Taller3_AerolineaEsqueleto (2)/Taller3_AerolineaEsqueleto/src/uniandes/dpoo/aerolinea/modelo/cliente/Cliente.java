package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public abstract class Cliente
{
    public static final String CORPORATIVO = "Corporativo";
    public static final String NATURAL = "Natural";

    private List<Tiquete> tiquetes;

    public Cliente( )
    {
        tiquetes = new ArrayList<Tiquete>( );
    }

    public abstract String getIdentificador( );

    public abstract String getTipoCliente( );

    public void agregarTiquete( Tiquete t )
    {
        tiquetes.add( t );
    }

    public Collection<Tiquete> getTiquetes( )
    {
        return tiquetes;
    }
}