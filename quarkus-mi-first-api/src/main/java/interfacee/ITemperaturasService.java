package interfacee;

import java.util.List;
import java.util.Optional;

import entities.temperaturas.Temperaturas;


public interface  ITemperaturasService {

    Temperaturas newTemperatura(Temperaturas temperaturas);

    Integer maxima();
    
    List<Temperaturas> getAllTemperaturas();

    boolean listTempIsEmpty();
    
    Optional<Temperaturas> getTemperatura(String ciudad);
}
