package pucp.edu.pe.pucpconnect.ws;

public class MensajeDTO {
    private int emisorId;
    private int receptorId;
    private String contenido;
    private String timestamp;

    public MensajeDTO() {}

    public MensajeDTO(int emisorId, int receptorId, String contenido, String timestamp) {
        this.emisorId   = emisorId;
        this.receptorId = receptorId;
        this.contenido  = contenido;
        this.timestamp  = timestamp;
    }

    public int getEmisorId() { return emisorId; }
    public void setEmisorId(int emisorId) { this.emisorId = emisorId; }

    public int getReceptorId() { return receptorId; }
    public void setReceptorId(int receptorId) { this.receptorId = receptorId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
