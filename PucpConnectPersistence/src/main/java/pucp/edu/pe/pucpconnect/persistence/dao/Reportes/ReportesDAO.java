package pucp.edu.pe.pucpconnect.persistence.reportes;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import java.sql.Connection;

public interface ReporteDAO {
    JasperPrint generarReporteEventosParticipantes(Connection conn) throws JRException;
    JasperPrint generarReportePorcentajeCarreras(Connection conn) throws JRException;
}
