package entities.person;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
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
    private Integer id;

    @Column(nullable = false, name = "name")
    @Size(message = "El name debe tener entre 3 y 20 caracteres", min = 3, max = 20)
    private String name;

    @Column(nullable = false, name = "last_name")
    @Size(message = "El last_name debe tener entre 3 y 20 caracteres", min = 3, max = 20)
    private String lastName;

    @Column(nullable = false, name = "age")
    private Integer age;

}
