package repository.person;

import entities.person.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped // Se utiliza para indicar que una instancia de la clase debe vivir y estar disponible durante toda la duración de la aplicación.
// La instancia de esa clase se crea cuando la aplicación se inicia y se destruye cuando la aplicación se apaga.
// La instancia de la clase anotada con @ApplicationScoped es compartida por todos los componentes que la necesiten en el ámbito de toda la aplicación.
public class PersonRepository implements PanacheRepositoryBase<Person, Long>{
    // PanacheRepository es una interfaz más rica que extiende PanacheRepositoryBase y proporciona operaciones CRUD generadas automáticamente, mientras que PanacheRepositoryBase es una interfaz más básica que te da más control para implementar tus propias consultas personalizadas. 
}
