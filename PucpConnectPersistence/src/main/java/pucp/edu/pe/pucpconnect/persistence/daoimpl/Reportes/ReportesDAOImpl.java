package pucp.edu.pe.pucpconnect.persistence.daoimpl.Reportes;

import net.sf.jasperreports.engine.*;
import pucp.edu.pe.pucpconnect.persistence.dao.reportes.ReportesDAO;

import java.io.InputStream;
import java.sql.Connection;

public class ReportesDAOImpl implements ReportesDAO {

    @Override
    public JasperPrint generarReporteEventosParticipantes(Connection conn) throws JRException {
        InputStream reporte = getClass().getResourceAsStream("/reportes/eventos_participantes.jasper");
        return JasperFillManager.fillReport(reporte, null, conn);
    }

    @Override
    public JasperPrint generarReportePorcentajeCarreras(Connection conn) throws JRException {
        InputStream reporte = getClass().getResourceAsStream("/reportes/porcentaje_carreras.jasper");
        return JasperFillManager.fillReport(reporte, null, conn);
    }
}
