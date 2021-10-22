package isu.library.model.service.user;

import isu.library.model.entity.Person;
import isu.library.model.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Optional<Person> findPersonByUsername(String username) {
        return personRepository.findPersonByUsername(username);
    }

    @Override
    public Iterable<Person> findPersonByUsernameNotNull() {
        return personRepository.findPersonByUsernameNotNull();
    }

    @Override
    public void deleteById(Integer id) {
        if (id != null) {
            personRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Person> findPersonById(Integer id) {
        Optional<Person> person = personRepository.findPersonById(id);
        return person;
    }

    @Override
    public void updatePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public Optional<Person> findPersonById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public Iterable<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Iterable<Person> findPersonsByRole(String role) {
        return personRepository.findPersonByRole(role);
    }


}
