package pucp.edu.pe.pucpconnect.business.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import pucp.edu.pe.pucpconnect.business.ReporteService;
import pucp.edu.pe.pucpconnect.persistence.dao.reportes.ReportesDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Reportes.ReportesDAOImpl;

import java.sql.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import pucp.edu.pe.pucpconnect.persistence.DBManager;

public class ReporteServiceImpl implements ReporteService {

    private final ReportesDAO reporteDAO;

    public ReporteServiceImpl() {
        this.reporteDAO = new ReportesDAOImpl();
    }
    
        @Override
        public byte[] generaReporte(String template_path) throws JRException{
            byte[] file;

        // Compilación del archivo jrxml de Jasper Reports
        JasperReport report;
        report = JasperCompileManager.compileReport(template_path);
        // Obtener conexión a la base de datos
        Connection conn = DBManager.getInstance().obtenerConexion();
        HashMap<String, Object> parameters = new HashMap<>();
        JasperPrint print = JasperFillManager.fillReport(report, parameters, conn);
        file = JasperExportManager.exportReportToPdf(print);

        return file;
      }

  
  private String getFileResource(String fileName){ 
        String filePath = getClass().getClassLoader().getResource(fileName).getPath();
        filePath = filePath.replace("%20", " ");
        return filePath;
    }
    
    
    
    
    
}
