package pucp.edu.pe.pucpconnect.business;

import net.sf.jasperreports.engine.JRException;

public interface ReporteService {
    public byte[] generaReporte(String template_path) throws JRException;
    
    
}
