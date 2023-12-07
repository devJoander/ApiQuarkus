package controller.temperaturas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import entities.temperaturas.Temperaturas;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.TemperaturasService;

@Path("temperaturas")
public class TemperaturasController {
    
    private TemperaturasService temperaturasService;
    
    @Inject
    public TemperaturasController(TemperaturasService temperaturasService) {
        this.temperaturasService = temperaturasService;
    }

    @GET
    @Path("saludo")
    // Query params: http://localhost:8080/usuarios/saludo?sms=xd
    public String sms(@QueryParam("sms") String sms ){
        List<String> ss = new ArrayList<>();
        if (sms.isEmpty()){
           return "No hay sms";
        }
        return "Holaaaa "+ sms;
    }

    // @GET
    // @Path("{name}")
    // public String name(@PathParam("name") String name ){
    //     if (name.isEmpty()){
    //        return "No hay sms";
    //     }
    //     return "Holaaaa "+ name;
    // }

    @GET
    @Path("getOne")
    public Temperaturas temperaturas(String name ){ 
        return new Temperaturas("Cuenca", 19, 25);
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON) // indica que el método produce contenido en formato JSON.
    public List<Temperaturas> listaTemperaturas(){ 
        // return Arrays.asList(
        //     new Temperaturas("Guayas", 35, 40),
        //     new Temperaturas("Manabí", 30, 50)) ;
        // return Collections.unmodifiableList(temperaturas);

        return temperaturasService.getAllTemperaturas();
    }

    // private List<Temperaturas> temperaturas = new ArrayList<>();

    @POST
    @Path("new")
    @Produces(MediaType.APPLICATION_JSON)
    public Temperaturas newTemperaturas(Temperaturas temp){
        // temperaturas.add(temp);
        // return temp;
        temperaturasService.newTemperatura(temp); 
        return temp;
    }   

//     @GET
//     @Path("/maxima")
//     @Produces(MediaType.TEXT_PLAIN)
//     public String maxima(){
//         return Integer.toString(temperaturasService.maxima());
//     } 
    @GET
    @Path("/maxima")
    @Produces(MediaType.TEXT_PLAIN)
    public Response maxima(){
        String temMax = Integer.toString(temperaturasService.maxima());
        if(temMax.isEmpty()){
            return Response.status(404).entity("No hay temperaturas").build();
        }else{
            return Response.ok(temMax)
            .header("XD","xd")
            .build();
        }
    } 

    @GET
    @Path("{ciudad}")
    @Produces(MediaType.APPLICATION_JSON)
    public Temperaturas getTemperaturaByCiudad(String ciudad){
      return temperaturasService.getTemperatura(ciudad)
      .orElseThrow(() ->
      new NoSuchElementException("No hay registros con la ciudad " + ciudad)); 
    } 

}