package exception;

public class Mensaje extends RuntimeException{

    public String mensaje;
    public String causa;

    public Mensaje(){}

    public Mensaje(String mensaje, String causa) {
        this.mensaje = mensaje;
        this.causa = causa;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    
     

}


