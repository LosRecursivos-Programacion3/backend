package pucp.edu.pe.pucpconnect.business.impl;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import pucp.edu.pe.pucpconnect.business.ReporteService;
import pucp.edu.pe.pucpconnect.persistence.dao.reportes.ReportesDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Reportes.ReportesDAOImpl;

import java.sql.*;

import java.sql.Connection;
import pucp.edu.pe.pucpconnect.persistence.DBManager;

public class ReporteServiceImpl implements ReporteService {

    private final ReportesDAO reporteDAO;

    public ReporteServiceImpl() {
        this.reporteDAO = new ReportesDAOImpl();
    }

    @Override
    public byte[] generarReporteEventosParticipantes() {
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
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
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            JasperPrint print = reporteDAO.generarReportePorcentajeCarreras(conn);
            return JasperExportManager.exportReportToPdf(print);
        } catch (JRException | RuntimeException e) {
            throw new RuntimeException("Error al generar el reporte de porcentaje por carreras", e);
        } catch (Exception e) {
            throw new RuntimeException("Error de conexión a base de datos", e);
        }
    }
}
