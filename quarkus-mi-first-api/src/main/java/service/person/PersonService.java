package service.person;

import java.util.List;

import entities.person.Person;
import exception.Mensaje;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import repository.person.PersonRepository;

@ApplicationScoped
@Transactional
public class PersonService {

    @Inject
    PersonRepository personRepository;

    public List<Person> getAllPersons() {
        try {
            List<Person> persons = personRepository.listAll();

            if (persons.isEmpty()) {
                throw new IllegalArgumentException("Lista de personas vacía");
            }

            return persons;
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage().toString(), e.getCause());
        }
    }

    public void create(Person person) {
        try {
            if (person == null) {
                throw new IllegalArgumentException("Ingresa un objeto");
            } else if (person.getName().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede ser vacío");
            } else if (person.getLastName().isEmpty()) {
                throw new IllegalArgumentException("El apellido no puede ser vacío");
            } else if (person.getAge() <= 0) {
                throw new IllegalArgumentException("La edad no puede ser vacío");
            }
            personRepository.persist(person);
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage().toString(), e.getCause());
        }

    }

}
