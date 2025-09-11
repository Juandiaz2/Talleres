
package uniandes.dpoo.aerolinea.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.exceptions.VueloSobrevendidoException;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;
import uniandes.dpoo.aerolinea.persistencia.IPersistenciaAerolinea;
import uniandes.dpoo.aerolinea.persistencia.IPersistenciaTiquetes;
import uniandes.dpoo.aerolinea.persistencia.TipoInvalidoException;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public class Aerolinea
{
    private List<Avion> aviones;
    private Map<String, Ruta> rutas;
    private List<Vuelo> vuelos;
    private Map<String, Cliente> clientes;

    public Aerolinea( )
    {
        aviones = new LinkedList<Avion>( );
        rutas = new HashMap<String, Ruta>( );
        vuelos = new LinkedList<Vuelo>( );
        clientes = new HashMap<String, Cliente>( );
    }

    public void agregarRuta( Ruta ruta )
    {
        this.rutas.put( ruta.getCodigoRuta( ), ruta );
    }

    public void agregarAvion( Avion avion )
    {
        this.aviones.add( avion );
    }

    public void agregarCliente( Cliente cliente )
    {
        this.clientes.put( cliente.getIdentificador( ), cliente );
    }

    public boolean existeCliente( String identificadorCliente )
    {
        return this.clientes.containsKey( identificadorCliente );
    }

    public Cliente getCliente( String identificadorCliente )
    {
        return this.clientes.get( identificadorCliente );
    }

    public Collection<Avion> getAviones( )
    {
        return aviones;
    }

    public Collection<Ruta> getRutas( )
    {
        return rutas.values( );
    }

    public Ruta getRuta( String codigoRuta )
    {
        return rutas.get( codigoRuta );
    }

    public Collection<Vuelo> getVuelos( )
    {
        return vuelos;
    }

    public Vuelo getVuelo( String codigoRuta, String fechaVuelo )
    {
        for( Vuelo v : vuelos )
        {
            if( v.getFecha().equals( fechaVuelo ) && v.getRuta().getCodigoRuta().equals( codigoRuta ) )
                return v;
        }
        return null;
    }

    public Collection<Cliente> getClientes( )
    {
        return clientes.values( );
    }

    public Collection<Tiquete> getTiquetes( )
    {
        List<Tiquete> todos = new ArrayList<Tiquete>( );
        for( Vuelo v : vuelos )
            todos.addAll( v.getTiquetes( ) );
        return todos;
    }

    public void cargarAerolinea( String tipoArchivo, String archivo ) throws IOException, TipoInvalidoException
    {
        IPersistenciaAerolinea persistencia = CentralPersistencia.getPersistenciaAerolinea( tipoArchivo );
        persistencia.cargarAerolinea( archivo, this );
    }

    public void salvarAerolinea( String tipoArchivo, String archivo ) throws IOException, TipoInvalidoException
    {
        IPersistenciaAerolinea persistencia = CentralPersistencia.getPersistenciaAerolinea( tipoArchivo );
        persistencia.salvarAerolinea( archivo, this );
    }

    public void cargarTiquetes( String archivo ) throws IOException, InformacionInconsistenteException
    {
        IPersistenciaTiquetes p = CentralPersistencia.getPersistenciaTiquetes( CentralPersistencia.JSON );
        p.cargarTiquetes( archivo, this );
    }

    public void salvarTiquetes( String archivo ) throws IOException, TipoInvalidoException
    {
        IPersistenciaTiquetes p = CentralPersistencia.getPersistenciaTiquetes( CentralPersistencia.JSON );
        p.salvarTiquetes( archivo, this );
    }

    public void programarVuelo( String fecha, String codigoRuta, String nombreAvion ) throws InformacionInconsistenteException
    {
        Ruta ruta = getRuta( codigoRuta );
        if( ruta == null ) throw new InformacionInconsistenteException( "Ruta inexistente: " + codigoRuta );
        Avion avionSel = null;
        for( Avion a : aviones )
        {
            if( a.getNombre().equals( nombreAvion ) )
            { avionSel = a; break; }
        }
        if( avionSel == null ) throw new InformacionInconsistenteException( "Avión inexistente: " + nombreAvion );

        String hsNueva = ruta.getHoraSalida();
        String hlNueva = ruta.getHoraLlegada();
        int sNueva = Integer.parseInt(hsNueva);
        int lNueva = Integer.parseInt(hlNueva);
        for( Vuelo v : vuelos )
        {
            if( v.getFecha().equals( fecha ) && v.getAvion().getNombre().equals( nombreAvion ) )
            {
                String hs = v.getRuta().getHoraSalida();
                String hl = v.getRuta().getHoraLlegada();
                int s = Integer.parseInt(hs);
                int l = Integer.parseInt(hl);
                boolean overlap = !(l <= sNueva || lNueva <= s);
                if( overlap ) throw new InformacionInconsistenteException( "El avión ya está asignado en otro vuelo que se traslapa en la fecha " + fecha );
            }
        }
        Vuelo nuevo = new Vuelo( fecha, ruta, avionSel );
        this.vuelos.add( nuevo );
    }

    public int venderTiquetes( String identificadorCliente, String fecha, String codigoRuta, int cantidad ) throws VueloSobrevendidoException, InformacionInconsistenteException
    {
        Cliente cliente = getCliente( identificadorCliente );
        if( cliente == null ) throw new InformacionInconsistenteException("Cliente inexistente: " + identificadorCliente);
        Vuelo vuelo = getVuelo( codigoRuta, fecha );
        if( vuelo == null ) throw new InformacionInconsistenteException("Vuelo inexistente para ruta " + codigoRuta + " y fecha " + fecha);
        int disponibles = vuelo.getCapacidad() - vuelo.getCuposOcupados();
        if( cantidad > disponibles ) throw new VueloSobrevendidoException( vuelo );

        int mes = 0;
        try {
            String[] partes = fecha.split("-");
            if( partes.length >= 2 ) mes = Integer.parseInt(partes[1]);
        } catch( Exception ign ) { mes = 0; }
        boolean temporadaBaja = (mes>=9 && mes<=11);
        int tarifaBase;
        if( Cliente.NATURAL.equals( cliente.getTipoCliente() ) ) {
            tarifaBase = temporadaBaja ? 100000 : 120000;
        } else {
            tarifaBase = temporadaBaja ? 80000 : 100000;
        }

        int total = 0;
        for( int i=0; i<cantidad; i++ ) {
            Tiquete t = GeneradorTiquetes.generarTiquete( vuelo, cliente, tarifaBase );
            vuelo.agregarTiquete( t );
            cliente.agregarTiquete( t );
            total += tarifaBase;
        }
        return total;
    }

    public void registrarVueloRealizado( String fecha, String codigoRuta ) throws InformacionInconsistenteException
    {
        Vuelo v = getVuelo( codigoRuta, fecha );
        if( v == null ) throw new InformacionInconsistenteException("Vuelo inexistente para ruta " + codigoRuta + " y fecha " + fecha);
        for( Tiquete t : v.getTiquetes() ) {
            if( !t.esUsado() ) t.marcarComoUsado();
        }
    }

    public String consultarSaldoPendienteCliente( String identificadorCliente )
    {
        Cliente c = getCliente( identificadorCliente );
        if( c == null ) return "0";
        int suma = 0;
        for( Tiquete t : c.getTiquetes() ) {
            if( !t.esUsado() ) suma += t.getTarifa();
        }
        return Integer.toString( suma );
    }
}
