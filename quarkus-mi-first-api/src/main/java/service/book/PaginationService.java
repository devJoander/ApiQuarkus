package service.book;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import entities.book.PaginatedResponse;

@ApplicationScoped
public class PaginationService {

    public <E> PaginatedResponse<E> paginate(int currentPage, int totalPages, PanacheQuery<E> query) {
        return new PaginatedResponse<>(query.page().index + 1, query.pageCount(), query.list());
    }
 
}
