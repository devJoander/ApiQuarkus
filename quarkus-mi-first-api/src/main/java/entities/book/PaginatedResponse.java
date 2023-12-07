package entities.book;

import java.util.List;

public class PaginatedResponse<E> {
    private int page;
    private int totalPages;
    private List<E> data;
    public PaginatedResponse(int page, int totalPages, List<E> data) {
        this.page = page;
        this.totalPages = totalPages;
        this.data = data;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    public List<E> getData() {
        return data;
    }
    public void setData(List<E> data) {
        this.data = data;
    }
    
    
}
