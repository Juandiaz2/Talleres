package uniandes.dpoo.aerolinea.modelo.cliente;

import org.json.JSONObject;

public class ClienteCorporativo extends Cliente
{
    public static final String CORPORATIVO = Cliente.CORPORATIVO;

    private String nit;

    public ClienteCorporativo( String nit )
    {
        super();
        this.nit = nit;
    }

    @Override
    public String getIdentificador( )
    {
        return nit;
    }

    @Override
    public String getTipoCliente( )
    {
        return CORPORATIVO;
    
    }
    public JSONObject salvarEnJSON() {
        JSONObject j = new JSONObject();
        j.put("tipoCliente", getTipoCliente());
        j.put("nit", getIdentificador());
        return j;
    }

    public static ClienteCorporativo cargarDesdeJSON(JSONObject j) {
        String nit = j.getString("nit");
        return new ClienteCorporativo(nit);
    }
}
