package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import pucp.edu.pe.pucpconnect.business.ReporteService;
import pucp.edu.pe.pucpconnect.business.impl.ReporteServiceImpl;
import pucp.edu.pe.pucpconnect.persistence.dao.reportes.ReportesDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Reportes.ReportesDAOImpl;

/**
 * Web service para exponer reportes Jasper al frontend.
 */
@WebService(serviceName = "ReporteWS")
public class ReporteWS {

    private final ReporteService reporteService;

    public ReporteWS() {
        ReportesDAO reporteDAO = new ReportesDAOImpl();
        reporteService = new ReporteServiceImpl();
    }

    /**
     * Genera el reporte de eventos con sus participantes.
     */
    @WebMethod(operationName = "generarReporteEventosConParticipantes")
    public byte[] generarReporteEventosConParticipantes() {
        try {
            return reporteService.generarReporteEventosParticipantes();
        } catch (Exception e) {
            throw new WebServiceException("Error generando reporte de eventos: " + e.getMessage(), e);
        }
    }

    /**
     * Genera el reporte de porcentaje de alumnos por carrera.
     */
    @WebMethod(operationName = "generarReportePorcentajeAlumnosPorCarrera")
    public byte[] generarReportePorcentajeAlumnosPorCarrera() {
        try {
            return reporteService.generarReportePorcentajeCarreras();
        } catch (Exception e) {
            throw new WebServiceException("Error generando reporte de alumnos por carrera: " + e.getMessage(), e);
        }
    }
}
