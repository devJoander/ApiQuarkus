package entities.person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data // Genera automáticamente métodos toString, equals, hashCode,
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, name = "name")
    @Size(message = "El name debe tener entre 3 y 20 caracteres", min = 3, max = 20)
    private String name;

    @Column(nullable = false, name = "last_name")
    @Size(message = "El last_name debe tener entre 3 y 20 caracteres", min = 3, max = 20)
    private String lastName;

    @Column(nullable = false, name = "age")
    private Long age;

    @NotBlank(message = "El campo status no puede ser vacío")
    @Size(max = 1, message = "El campo status debe tener un máximo de 1 caracter")
    @Column(name = "status")
    @Pattern(regexp = "[AI]", message = "El campo status solo puede tener los valores: A Activo, I Inactivo")
    private String status;

}
