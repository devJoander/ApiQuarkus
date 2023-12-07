package controller.book;

import java.util.List;
import java.util.NoSuchElementException;

import entities.book.Book;
import entities.book.PaginatedResponse;
import exception.CustomException;
import exception.Mensaje;
import io.netty.handler.codec.http2.Http2FrameLogger.Direction;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.inject.build.compatible.spi.Validation;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repository.book.BookRepository;
import service.book.PaginationService;

@Path("book")
@Transactional
public class BookController {

    @Inject
    private BookRepository bookRepository;

    @Inject
    PaginationService paginationService;

    @GET
    public Response getAll() {
        try {
            List<Book> books = bookRepository.listAll();
            // List<Book> books =Book.listAll(); // Al utilizar PanacheEntity no es
            // necesario el repository, con esto puedes tener los metodos CRUD a tu
            // disposición
            if (books.isEmpty()) {
                Mensaje mensaje = new Mensaje("Lista de libros vacía ", null);
                return Response.status(Response.Status.OK).entity(mensaje).build();
            }

            return Response.ok(books).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new Mensaje(e.getMessage(), e.getCause().toString()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new Mensaje("Error interno del servidor: " + e.getMessage(), e.getCause().toString()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("all")
    public Response getAllWithFilter(@Valid @QueryParam("q") String query) {
        try {
            List<Book> books;
            if (query == null) {
                books = bookRepository.listAll(Sort.by("pubDate", Sort.Direction.Descending));
            } else {
                String filter = "%" + query + "%"; // El % indica que el valor del filtro se aplica en cualquier parte
                                                   // del title y no solo en el principio
                books = bookRepository.list("title ILIKE ?1 OR descripcion ILIKE ?2", filter, filter);
            }

            if (books.isEmpty()) {
                Mensaje mensaje = new Mensaje("Lista de libros vacía", null);
                return Response.status(Response.Status.OK).entity(mensaje).build();
            } else {
                return Response.status(Response.Status.OK).entity(books).build();
            }
        } catch (IllegalArgumentException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, e.getMessage().toString(), e.getCause().toString());
        } catch (Exception e) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR,
                    "Error interno del servidor: " + e.getMessage(), e.getCause().toString());
        }
    }

    @GET
    @Path("/alls")
    public PaginatedResponse<Book> getAllBooks(@QueryParam("page") @DefaultValue("1") int page) {
        Page p = new Page(page - 1, 5);
        var query = bookRepository.findAll();
        query.page(p);
        return paginationService.paginate(page, query.pageCount(), query);

    }

    @GET
    @Path("alls")
    public Response getAllWithPage(@QueryParam("page") @DefaultValue("1") Integer page) {
        try {
            List<Book> books;

            Page p = new Page(page, 5);
            books = bookRepository.findAll(Sort.descending("id")).page(p).list();

            if (books.isEmpty()) {
                Mensaje mensaje = new Mensaje("Lista de libros vacía", null);
                return Response.status(Response.Status.OK).entity(mensaje).build();
            } else {
                return Response.status(Response.Status.OK).entity(books).build();
            }
        } catch (IllegalArgumentException e) {
            throw new CustomException(Response.Status.BAD_REQUEST, e.getMessage().toString(), e.getCause().toString());
        } catch (Exception e) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR,
                    "Error interno del servidor: " + e.getMessage(), e.getCause().toString());
        }
    }

    @POST
    public Response insertBook(Book book) {
        try {
            if (book == null) {
                Mensaje mensaje = new Mensaje("No se pudo insertar el libro", null);
                return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
            } else if (book.getTitle().isEmpty()) {
                Mensaje mensaje = new Mensaje("El titulo no puede ser vacío", null);
                return Response.status(Response.Status.BAD_REQUEST).entity(mensaje).build();
            } else if (book.getDescripcion().isEmpty()) {
                Mensaje mensaje = new Mensaje("La descripción no puede ser vacía", null);
                return Response.status(Response.Status.BAD_REQUEST).entity(mensaje).build();
            } else if (book.getNumPage() <= 0) {
                Mensaje mensaje = new Mensaje("El número de páginas debe ser un valor positivo", null);
                return Response.status(Response.Status.BAD_REQUEST).entity(mensaje).build();
            }

            bookRepository.persist(book);
            return Response.status(Response.Status.OK).entity(book).build();

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage().toString());

        } catch (Exception e) {
            throw new CustomException(Response.Status.INTERNAL_SERVER_ERROR,
                    "Error interno del servidor: " + e.getMessage(), e.getCause().toString());
        }
    }

    @Path("{id}")
    @GET
    public Response searchBookById(@PathParam("id") Long id) {
        try {
            Book book = bookRepository.findById(id);
            if (book != null) {
                Mensaje mensaje = new Mensaje("Libro encontrado con el ID: " + id, null);
                return Response.status(Response.Status.OK).entity(mensaje).build();
            } else {
                Mensaje mensaje = new Mensaje("No se encontró un libro con el ID proporcionado: " + id, null);
                return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
            }
        } catch (IllegalArgumentException e) {
            Mensaje mensaje = new Mensaje("Error de solicitud inválida", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(mensaje).build();
        } catch (Exception e) {
            Mensaje mensaje = new Mensaje("Error interno del servidor", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mensaje).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        try {
            Book book = bookRepository.findById(id);
            if (book != null) {
                bookRepository.deleteById(id);
                Mensaje mensaje = new Mensaje("Libro eliminado exitosamente con el ID: " + id, null);
                return Response.status(Response.Status.OK).entity(mensaje).build();
            } else {
                Mensaje mensaje = new Mensaje("No se encontró un libro con el ID proporcionado: " + id, null);
                return Response.status(Response.Status.NOT_FOUND).entity(mensaje).build();
            }
        } catch (IllegalArgumentException e) {
            Mensaje mensaje = new Mensaje("Error de solicitud inválida", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(mensaje).build();
        } catch (Exception e) {
            Mensaje mensaje = new Mensaje("Error interno del servidor", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mensaje).build();
        }
    }

    @PUT
    @Path("{id}")
    public Response updateBook(@PathParam("id") Long id, Book book) {

        try {
            Book bookUpdated = bookRepository.findById(id);
            if (bookUpdated == null) {
                Mensaje mensaje = new Mensaje("No se encontró un libro con el ID proporcionado: " + id, null);
                return Response.status(Response.Status.OK).entity(mensaje).build();
            }
            bookUpdated.setTitle(book.getTitle());
            bookUpdated.setNumPage(book.getNumPage());
            bookUpdated.setPubDate(book.getPubDate());
            bookUpdated.setDescripcion(book.getDescripcion());
            if (book.getTitle().isEmpty()) {
                Mensaje mensaje = new Mensaje("El titulo no puede ser vacío", null);
                return Response.status(Response.Status.BAD_REQUEST).entity(mensaje).build();
            } else if (book.getDescripcion().isEmpty()) {
                Mensaje mensaje = new Mensaje("La descripción no puede ser vacía", null);
                return Response.status(Response.Status.BAD_REQUEST).entity(mensaje).build();
            } else if (book.getNumPage() <= 0) {
                Mensaje mensaje = new Mensaje("El número de páginas debe ser un valor positivo", null);
                return Response.status(Response.Status.BAD_REQUEST).entity(mensaje).build();
            }
            return Response.ok(bookUpdated).build();

        } catch (IllegalArgumentException ex) {
            // Si hay un error de argumento ilegal, retorna una respuesta 400 Bad Request
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        } catch (NoSuchElementException ex) {
            // Si no se encuentra el libro, retorna una respuesta 404 Not Found
            return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        } catch (Exception e) {
            // Si hay un error no controlado, retorna una respuesta 500 Internal Server
            // Error
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
