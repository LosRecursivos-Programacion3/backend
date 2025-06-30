package pucp.edu.pe.pucpconnect.business.reportes.impl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import pucp.edu.pe.pucpconnect.business.reportes.ReporteService;
import pucp.edu.pe.pucpconnect.persistence.reportes.ReporteDAO;
import pucp.edu.pe.pucpconnect.persistence.reportes.impl.ReporteDAOImpl;
import pucp.edu.pe.pucpconnect.util.MySQLConnection;

import java.sql.Connection;

public class ReporteServiceImpl implements ReporteService {

    private final ReporteDAO reporteDAO;

    public ReporteServiceImpl() {
        this.reporteDAO = new ReporteDAOImpl();
    }

    @Override
    public byte[] generarReporteEventosParticipantes() {
        try (Connection conn = MySQLConnection.getConnection()) {
            JasperPrint print = reporteDAO.generarReporteEventosParticipantes(conn);
            return JasperExportManager.exportReportToPdf(print);
        } catch (JRException | RuntimeException e) {
            throw new RuntimeException("Error al generar el reporte de eventos y participantes", e);
        } catch (Exception e) {
            throw new RuntimeException("Error de conexión a base de datos", e);
        }
    }

    @Override
    public byte[] generarReportePorcentajeCarreras() {
        try (Connection conn = MySQLConnection.getConnection()) {
            JasperPrint print = reporteDAO.generarReportePorcentajeCarreras(conn);
            return JasperExportManager.exportReportToPdf(print);
        } catch (JRException | RuntimeException e) {
            throw new RuntimeException("Error al generar el reporte de porcentaje por carreras", e);
        } catch (Exception e) {
            throw new RuntimeException("Error de conexión a base de datos", e);
        }
    }
}
