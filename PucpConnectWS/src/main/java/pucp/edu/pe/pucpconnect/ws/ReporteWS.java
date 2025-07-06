package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;

import java.io.FileNotFoundException;

import java.util.Map;

import pucp.edu.pe.pucpconnect.business.AlumnoService;
import pucp.edu.pe.pucpconnect.business.ReporteService;
import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;
import pucp.edu.pe.pucpconnect.business.impl.ReporteServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;

/**
 * Web service para exponer reportes Jasper al frontend.
 */
    @WebService(serviceName = "ReporteWS")
public class ReporteWS {

    private final ReporteService reporteService;
    
    public ReporteWS() {
        reporteService = new ReporteServiceImpl();
    }

    @WebMethod(operationName = "generarReportePorcentajeAlumnosPorCarrera")
    public byte[] generarReportePorcentajeAlumnosPorCarrera() {
        try {
            return reporteService.generarReportePorcentajeCarreras();
        } catch (Exception e) {
            throw new WebServiceException("Error generando reporte de alumnos por carrera: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "generarReporteEventosConParticipantes")
    public byte[] generarReporteEventosConParticipantes() throws FileNotFoundException {
        byte[] file = null;

        String template_path = ReporteWS.class.getResource("/eventos_participantes.jrxml").getPath();
        template_path = template_path.replace("%20", " ");

        try {
            file = reporteService.generaReporte(template_path);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return file;
    }

    @WebMethod(operationName = "ReporteCantAlumnosPorCarrera")
    public byte[] ReporteCantAlumnosPorCarrera() throws FileNotFoundException {
        byte[] file = null;

        String template_path = ReporteWS.class.getResource("/porcentaje_carreras.jrxml").getPath();
        template_path = template_path.replace("%20", " ");

        try {
            file = reporteService.generaReporte(template_path);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return file;
    }
    
    @WebMethod(operationName = "ReporteUsuarios")
    public byte[] ReporteUsuarios() throws FileNotFoundException {
        byte[] file = null;

        String template_path = ReporteWS.class.getResource("/usuarios_intereses.jrxml").getPath();
        template_path = template_path.replace("%20", " ");

        try {
            file = reporteService.generaReporte(template_path);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return file;
    }
    @WebMethod(operationName = "AlumnosParaEstadistica")
    public  Map<String, Integer> AlumnosParaEstadistica(){
        BaseDAO<Alumno>AlumnoDao = new AlumnoDAOImpl();
        AlumnoService alumnoService = new AlumnoServiceImpl(AlumnoDao);
        return alumnoService.obtenerEstadisticasCarreras();
    }


}