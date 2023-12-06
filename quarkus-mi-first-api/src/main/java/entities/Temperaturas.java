package entities;


public class Temperaturas {
    
    String ciudad;

    Integer minima;

    Integer maxima;

    public Temperaturas(){}

    public Temperaturas(String ciudad, Integer minima, Integer maxima) {
        this.ciudad = ciudad;
        this.minima = minima;
        this.maxima = maxima;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getMinima() {
        return minima;
    }

    public void setMinima(Integer minima) {
        this.minima = minima;
    }

    public Integer getMaxima() {
        return maxima;
    }

    public void setMaxima(Integer maxima) {
        this.maxima = maxima;
    }

    
    
}
