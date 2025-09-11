package uniandes.dpoo.aerolinea.exceptions;

import uniandes.dpoo.aerolinea.modelo.Vuelo;

@SuppressWarnings("serial")
public class VueloSobrevendidoException extends Exception
{

    public VueloSobrevendidoException( Vuelo vuelo )
    {
        super( "El vuelo " + vuelo.getRuta( ).getCodigoRuta( ) + " del " + vuelo.getFecha( ) + " no tiene cupo" );
    }

}
