package uniandes.dpoo.aerolinea.tiquetes;

import java.util.HashSet;
import java.util.Set;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class GeneradorTiquetes
{

    private static Set<String> usados = new HashSet<String>( );

    public static void registrarTiquete( Tiquete tiquete )
    {
        usados.add( tiquete.getCodigo( ) );
    }

    private static Set<String> codigos = new HashSet<String>( );

    public static Tiquete generarTiquete( Vuelo vuelo, Cliente cliente, int tarifa )
    {
        int numero = ( int ) ( Math.random( ) * 10e7 );
        String codigo = "" + numero;
        while( codigos.contains( codigo ) )
        {
            numero = ( int ) ( Math.random( ) * 10e7 );
            codigo = "" + numero;
        }

        while( codigo.length( ) < 7 )
            codigo = "0" + codigo;

        return new Tiquete( codigo, vuelo, cliente, tarifa );
    }

    public static void registrarTiquete( Tiquete unTiquete )
    {
        usados.add( tiquete.getCodigo( ) );
    }

    public static boolean validarTiquete( String codigoTiquete )
    {
        return usados.contains( codigoTiquete );
    }
}
