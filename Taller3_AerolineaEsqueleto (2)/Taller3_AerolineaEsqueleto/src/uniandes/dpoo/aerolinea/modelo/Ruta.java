package uniandes.dpoo.aerolinea.modelo;

public class Ruta
{
    private Aeropuerto origen;
    private Aeropuerto destino;
    private String horaSalida;  
    private String horaLlegada; 

    public Ruta( Aeropuerto origen, Aeropuerto destino, String horaSalida, String horaLlegada )
    {
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
    }

    public Aeropuerto getOrigen( )
    {
        return origen;
    }

    public Aeropuerto getDestino( )
    {
        return destino;
    }

    public String getHoraSalida( )
    {
        return horaSalida;
    }

    public String getHoraLlegada( )
    {
        return horaLlegada;
    }

    public String getCodigoRuta( )
    {
        return origen.getCodigo( ) + "-" + destino.getCodigo( );
    }

    public int getDuracionMinutos( )
    {
        int hs = getHoras( horaSalida );
        int ms = getMinutos( horaSalida );
        int hl = getHoras( horaLlegada );
        int ml = getMinutos( horaLlegada );

        int t1 = hs * 60 + ms;
        int t2 = hl * 60 + ml;
        int d = t2 - t1;
        if( d < 0 ) d += 24*60;
        return d;
    }

    public static int getMinutos( String horaCompleta )
    {
        int minutos = Integer.parseInt( horaCompleta ) % 100;
        return minutos;
    }

    public static int getMinutos( String horaCompleta )
    {
        int minutos = Integer.parseInt( horaCompleta ) % 100;
        return minutos;
    }

    public static int getHoras( String horaCompleta )
    {
        int horas = Integer.parseInt( horaCompleta ) / 100;
        return horas;
    }

}