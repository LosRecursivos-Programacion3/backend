package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;


import java.io.FileNotFoundException;



import pucp.edu.pe.pucpconnect.business.ReporteService;

import pucp.edu.pe.pucpconnect.business.impl.ReporteServiceImpl;


/**
 * Web service para exponer reportes Jasper al frontend.
 */
@WebService(serviceName = "ReporteWS")
public class ReporteWS {

    private final ReporteService reporteService;

    public ReporteWS() {
        reporteService = new ReporteServiceImpl();
    }

    /**
     * Genera el reporte de eventos con sus participantes.
     */
    /**
     * Genera el reporte de porcentaje de alumnos por carrera.
     */
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

    

}
