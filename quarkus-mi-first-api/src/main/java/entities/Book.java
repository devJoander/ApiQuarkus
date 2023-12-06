package entities;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
 
@Entity
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
// public class Book extends PanacheEntity {

    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public int numPage;

    @Column(nullable = false)
    public LocalDate pubDate;
    
    @Column(nullable = false)
    public String descripcion;

    @CreationTimestamp
    public LocalDate createDate;

    @UpdateTimestamp
    public LocalDate updateDate;

    public Book(){}

    public Book(String title, int numPage, LocalDate pubDate, String descripcion) {
        this.title = title;
        this.numPage = numPage;
        this.pubDate = pubDate;
        this.descripcion = descripcion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumPage() {
        return numPage;
    }

    public void setNumPage(int numPage) {
        this.numPage = numPage;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    
}
