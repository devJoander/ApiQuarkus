package controller.person;

import java.util.List;
import entities.person.Person;
import exception.CustomException;
import exception.Mensaje;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import service.person.PersonService;

@Path("Person")
public class PersonController {

    @Inject
    PersonService personService;

    @Path("all")
    @GET
    public Response getAllPersons() {
        try {
            List<Person> persons = personService.getAllPersons();
            if (persons.isEmpty()) {
                Mensaje mensaje = new Mensaje("Lista de libros vacía ", null);
                return Response.status(Response.Status.OK).entity(mensaje).build();
            }
            return Response.ok(persons).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Mensaje(e.getMessage(), e.getCause().toString()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Mensaje("Error interno del servidor: " + e.getMessage(), e.getCause().toString()))
                    .build();
        }
    }

    @POST
    public Response insertPerson(Person person) {
        try {
            personService.create(person);
            return Response.status(Response.Status.OK).entity(person).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Mensaje(e.getMessage(), e.getCause().toString()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Mensaje("Error interno del servidor: " + e.getMessage(), e.getCause().toString()))
                    .build();
        }
    }
}
