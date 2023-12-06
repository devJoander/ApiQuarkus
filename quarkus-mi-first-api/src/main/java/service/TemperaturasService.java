package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import entities.Temperaturas;
import interfacee.ITemperaturasService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TemperaturasService implements ITemperaturasService {
    
    private List<Temperaturas> listTemp = new ArrayList<>();

    @Override
    public Temperaturas newTemperatura(Temperaturas temperaturas) {
            if(temperaturas == null){
                throw new UnsupportedOperationException("Ingresa la temperatura");
            }
            listTemp.add(temperaturas);
            return temperaturas;
        }

    @Override
    public Integer maxima() {
        if (!listTempIsEmpty()){
            throw new UnsupportedOperationException("Lista vacía");
        }
        return listTemp.stream().mapToInt(Temperaturas::getMaxima).max().getAsInt();
    }

    @Override
    public List<Temperaturas> getAllTemperaturas() {
          return Collections.unmodifiableList(listTemp);
    }

    @Override
    public boolean listTempIsEmpty() {
        if (listTemp.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public Optional<Temperaturas> getTemperatura(String ciudad) {
        return listTemp.stream()
        .filter(t -> t.getCiudad().equals(ciudad)).findAny();
     }


    // public Temperaturas inserTemperaturas(Temperaturas t){
    //      listTemp.add(t);
    //      return t;
    //  }

    // public List<Temperaturas> listaDeTemperaturas(){
    //     return Collections.unmodifiableList(listTemp);
    // }

    // public int maiximaTemp(){
    //     return listTemp.stream().mapToInt(Temperaturas::getMaxima).max().getAsInt();
    // }
}
