public class ReporteServiceImpl implements ReporteService {
    @Override
    public byte[] generarReporteUsuarios() throws Exception {
        // 1. Cargar el archivo .jasper o .jrxml
        InputStream reporte = getClass().getResourceAsStream("/reportes/usuarios.jasper");

        // 2. Pasar parámetros (si los hay)
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("titulo", "Lista de usuarios");

        // 3. Llenar el reporte (puede ser desde una conexión JDBC o colección de JavaBeans)
        Connection conn = dataSource.getConnection(); // o usar JRBeanCollectionDataSource
        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, conn);

        // 4. Exportar a PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
