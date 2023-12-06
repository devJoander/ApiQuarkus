package controller;

import java.util.List;
import java.util.NoSuchElementException;

import entities.Book;
import exception.CustomException;
import exception.Mensaje;
import io.netty.handler.codec.http2.Http2FrameLogger.Direction;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.BookRepository;

@Path("book")
@Transactional
public class BookController {

    @Inject
    private BookRepository bookRepository;

    // @GET
    // public Response getAll() {
    //     try {
    //         List<Book> books = bookRepository.listAll();
    //         // List<Book> books =Book.listAll(); // Al utilizar PanacheEntity no es
    //         // necesario el repository, con esto puedes tener los metodos CRUD a tu
    //         // disposición
    //         if (books.isEmpty()) {
    //             throw new IllegalArgumentException("La lista de libros está vacía");
    //         }

    //         return Response.ok(books).build();

    //     } catch (IllegalArgumentException e) {
    //         return Response.status(Response.Status.BAD_REQUEST)
    //                 .entity(new Mensaje(e.getMessage(), e.getCause().toString()))
    //                 .type(MediaType.APPLICATION_JSON)
    //                 .build();

    //     } catch (Exception e) {
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
    //                 .entity(new Mensaje("Error interno del servidor: " + e.getMessage(), e.getCause().toString()))
    //                 .type(MediaType.APPLICATION_JSON)
    //                 .build();
    //     }
    // }

    @GET
    public List<Book> getAll(@QueryParam("q")String query) {
        try {
             List<Book> books = bookRepository.listAll();
             if (books.isEmpty()) {
                throw new IllegalArgumentException("La lista de libros está vacía");
            }else  if(query == null){
                return bookRepository.listAll(Sort.by("pubDate", Sort.Direction.Descending));
            }else {
                String filter = "%" + query + "%";
                return bookRepository.list("title ILIKE ?1 OR description ILIKE ?2", filter, filter);
            }  

        }  catch (IllegalArgumentException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, e.getMessage(), e.getCause().toString());

        } catch (Exception e) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR,
                    "Error interno del servidor: " + e.getMessage(), e.getCause().toString());
        }
    }
    @POST
    public Book insertBook(Book book) {
        try {
            if (book == null) {
                throw new IllegalArgumentException("El libro enviado es nulo");
            }

            bookRepository.persist(book);

            return book; // Devuelve directamente el libro insertado

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage().toString());

        } catch (Exception e) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR,
                    "Error interno del servidor: " + e.getMessage(), e.getCause().toString());
        }
    }

    @Path("{id}")
    @GET
    public Book searchBookById(@PathParam("id") Long id) {
        try {

            Book book = bookRepository.findById(id);
            if (book != null) {
                return book;
            }
            throw new NoSuchElementException("No se encontró un registro con el ID proporcionado: " + id);
        } catch (IllegalArgumentException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, e.getMessage(), e.getCause().toString());

        } catch (Exception e) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR,
                    "Error interno del servidor: " + e.getMessage(), e.getCause().toString());
        }
    }

    @DELETE
    @Path("{id}")
    public void deleteBook(@PathParam("id") Long id){
        try {
            Book book = bookRepository.findById(id);
            if (book != null) {
                bookRepository.deleteById(id);
                throw new NoSuchElementException("Se elimino el libro con el id: " + id);
            }
            throw new NoSuchElementException("No se encontró un registro con el ID proporcionado: " + id);
        }catch (IllegalArgumentException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, e.getMessage(), e.getCause().toString());

        } catch (Exception e) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR,
                    "Error interno del servidor: " + e.getMessage(), e.getCause().toString());
        }
    }

    @PUT
    @Path("{id}")
    public Response updateBook(@PathParam("id") Long id, Book book){
        
        try {
            Book bookUpdated = bookRepository.findById(id);
            if (bookUpdated == null) {
                throw new NoSuchElementException("No se encontró un registro con el ID proporcionado: " + id);
             }
             bookUpdated.setTitle(book.getTitle());
             bookUpdated.setNumPage(book.getNumPage());
             bookUpdated.setPubDate(book.getPubDate());
             bookUpdated.setDescripcion(book.getDescripcion());
            return Response.ok(bookUpdated).build();
            
        } catch (IllegalArgumentException ex) {
            // Si hay un error de argumento ilegal, retorna una respuesta 400 Bad Request
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (NoSuchElementException ex) {
            // Si no se encuentra el libro, retorna una respuesta 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception e) {
            // Si hay un error no controlado, retorna una respuesta 500 Internal Server Error
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
