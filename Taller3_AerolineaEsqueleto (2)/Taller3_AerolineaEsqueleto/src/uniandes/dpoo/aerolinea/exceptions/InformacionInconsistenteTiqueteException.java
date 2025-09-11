package uniandes.dpoo.aerolinea.exceptions;

@SuppressWarnings("serial")
public class InformacionInconsistenteTiqueteException extends InformacionInconsistenteException
{
    public InformacionInconsistenteTiqueteException( String tipoElemento, String identificadorElemento )
    {
        this( tipoElemento, identificadorElemento, true );
    }

    public InformacionInconsistenteTiqueteException( String tipoElemento, String identificadorElemento, boolean sentido )
    {
        super( "Dentro del conjunto de " + tipoElemento + ( sentido ? " NO" : " YA" ) + " existe un objeto con el identificador " + identificadorElemento );
    }
}
