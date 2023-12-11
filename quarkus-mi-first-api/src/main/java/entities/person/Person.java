package entities.person;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class Person {

    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "name")
    @Size(message = "El name debe tener entre 3 y 20 caracteres", min = 3, max = 20)
    private String name;

    @Column(nullable = false, name = "last_name")
    @Size(message = "El last_name debe tener entre 3 y 20 caracteres", min = 3, max = 20)
    private String lastName;

    @Column(nullable = false, name = "age")
    private Integer age;

    @NotBlank(message = "El campo status no puede estar vacío")
    @Size(max = 1, message = "El campo status debe tener un máximo de 1 caracter")
    @Column(name = "status")
    @Pattern(regexp = "[AI]", message = "El campo status solo puede tener los valores: A Activo, I Inactivo")
    private String status;

}
