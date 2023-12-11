package service.person;

import java.util.List;
import entities.person.Person;
import exception.Mensaje;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repository.person.PersonRepository;

@ApplicationScoped
@Transactional
public class PersonService {

    @Inject
    PersonRepository personRepository;
    Mensaje mensaje = new Mensaje();
    public List<Person> getAll() {
        try {
            List<Person> persons = personRepository.listAll();

            if (persons.isEmpty()) {
                return null;
            }

            return persons;
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage().toString(), e.getCause());
        }
    }

    public Person create(Person person) {
        try {
            if (person == null) {
                mensaje = new Mensaje("Ingresa un objeto", mensaje.getCausa().toString());

                throw new IllegalArgumentException(mensaje.getCausa());
            } else if (person.getName().isEmpty()) {
                mensaje = new Mensaje("El name no puede ser vacío", mensaje.getCausa().toString());
                throw new IllegalArgumentException(mensaje.getCausa());
            } else if (person.getLastName().isEmpty()) {
                mensaje = new Mensaje("El last_name no puede ser vacío",mensaje.getCausa().toString());
                throw new IllegalArgumentException(mensaje);
            } else if (person.getAge() <= 0) {
                mensaje = new Mensaje("The age can´t be empty", mensaje.getCausa().toString());
                throw new IllegalArgumentException(mensaje);
            }
            person.setStatus("A");
            personRepository.persist(person);
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage().toString(), e.getCause());
        }
        return person;
    }

    public Person update(Long id, Person updatedPerson) {
        try {
            if (updatedPerson == null) {
                throw new IllegalArgumentException("Ingresa un objeto");
            } else if (updatedPerson.getName().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede ser vacío");
            } else if (updatedPerson.getLastName().isEmpty()) {
                throw new IllegalArgumentException("El apellido no puede ser vacío");
            } else if (updatedPerson.getAge() <= 0) {
                throw new IllegalArgumentException("La edad no puede ser vacía");
            }

            Person existingPerson = personRepository.findById(id);
            if (existingPerson == null) {
                throw new IllegalArgumentException("No se encontró ninguna persona con el ID proporcionado: " + id);
            }

            existingPerson.setName(updatedPerson.getName());
            existingPerson.setLastName(updatedPerson.getLastName());
            existingPerson.setAge(updatedPerson.getAge());
            existingPerson.setStatus("A");

            // Utiliza persist o persistAndFlush para actualizar la entidad
            personRepository.persist(existingPerson);

            return existingPerson;
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage().toString(), e.getCause());
        }
    }
    public void delete(Long id) {
        try {
             
            Person existingPerson = personRepository.findById(id);
            if (existingPerson == null) {
               
            }
            existingPerson.setStatus("I");
            personRepository.persist(existingPerson);

         } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage().toString(), e.getCause());
        }
    }

     public Person getById(Long id) {
        try {
             
            Person existingPerson = personRepository.findById(id);
            if (existingPerson == null) {
                return null;
            }
            return existingPerson;

         } catch (RuntimeException ex) {
            throw new IllegalArgumentException(ex.getMessage().toString(), ex.getCause());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: " + e.getMessage().toString(), e.getCause());
        }
    }
}
