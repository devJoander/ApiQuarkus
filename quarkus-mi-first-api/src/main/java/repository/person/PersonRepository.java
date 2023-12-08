package repository.person;

import entities.person.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person>{
    // PanacheRepository es una interfaz más rica que extiende PanacheRepositoryBase y proporciona operaciones CRUD generadas automáticamente, mientras que PanacheRepositoryBase es una interfaz más básica que te da más control para implementar tus propias consultas personalizadas. 
}
