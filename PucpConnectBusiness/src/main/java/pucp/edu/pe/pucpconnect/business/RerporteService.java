package pucp.edu.pe.pucpconnect.business;

import java.util.List;

public interface ReporteService {
    byte[] generarReporteEventosParticipantesPDF(Connection conn) throws JRException;
    byte[] generarReporteAlumnosPorCarreraPDF(Connection conn) throws JRException;
}
