package pucp.edu.pe.pucpconnect.persistence.reportes.impl;

import net.sf.jasperreports.engine.*;
import pucp.edu.pe.pucpconnect.persistence.reportes.ReporteDAO;

import java.io.InputStream;
import java.sql.Connection;

public class ReporteDAOImpl implements ReporteDAO {

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
