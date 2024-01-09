package controller.person;

import java.util.List;
import entities.person.Person;
import exception.Mensaje;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import service.person.PersonService;

@Path("person")
public class PersonController {

    @Inject
    PersonService personService;

    @Path("all")
    @GET
    public Response getAll() {
        try {
            List<Person> persons = personService.getAll();
            return Response.ok(persons).entity(new Mensaje("Error interno del servidor:" , "")).build();
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
    public Response insertPerson(@Valid Person person) {
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

    @Path("{id}")
    @PUT()
    public Response update(@Valid Long id, Person person) {
        try {
            personService.update(id, person);
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

    @Path("{id}")
    @DELETE()
    public Response delete(Long id) {
        try {
            personService.delete(id);
            return Response.status(Response.Status.OK).build();
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

    @Path("{id}")
    @GET()
    public Response getById(Long id) {
        try {
            return Response.status(Response.Status.OK).build();
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
