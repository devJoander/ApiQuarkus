package repository;

import entities.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped // se utiliza para marcar una clase como un componente de ámbito de aplicación
public class BookRepository implements PanacheRepository<Book> {


    
}
