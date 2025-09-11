package uniandes.dpoo.aerolinea.persistencia;

@SuppressWarnings("serial")
public class TipoInvalidoException extends Exception
{
    public TipoInvalidoException( String tipoArchivo )
    {
        super( "La cadena '" + tipoArchivo + "' no corresponde a un tipo válido de archivo para la persistencia" );
    }

}
