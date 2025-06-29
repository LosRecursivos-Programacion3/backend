package pucp.edu.pe.pucpconnect.business.impl;

import pucp.edu.pe.pucpconnect.business.ReporteService;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.dao.Usuarios.InteresDAO;


package pucp.edu.pe.pucpconnect.business.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


@Override
public byte[] generarReporteEventosParticipantesPDF(Connection conn) throws JRException {
    InputStream reporte = getClass().getResourceAsStream("/reportes/eventos_participantes.jasper");
    JasperPrint print = JasperFillManager.fillReport(reporte, null, conn);
    return JasperExportManager.exportReportToPdf(print);
}

@Override
public byte[] generarReporteAlumnosPorCarreraPDF(Connection conn) throws JRException {
    InputStream reporte = getClass().getResourceAsStream("/reportes/alumnos_por_carrera.jasper");
    JasperPrint print = JasperFillManager.fillReport(reporte, null, conn);
    return JasperExportManager.exportReportToPdf(print);
}
