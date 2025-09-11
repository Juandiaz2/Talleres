package uniandes.dpoo.aerolinea.persistencia;

import java.io.IOException;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;

public interface IPersistenciaTiquetes
{
    
    public void cargarTiquetes( String archivo, Aerolinea aerolinea ) throws IOException, InformacionInconsistenteException;

    public void salvarTiquetes( String archivo, Aerolinea aerolinea ) throws IOException;

}
