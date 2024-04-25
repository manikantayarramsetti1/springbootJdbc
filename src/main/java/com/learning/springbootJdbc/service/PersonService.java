package com.learning.springbootJdbc.service;

import com.learning.springbootJdbc.dao.PersonDao;
import com.learning.springbootJdbc.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonDao personDao;

    public void addPerson(Person person) {
        personDao.insertPerson(person);
    }

    public Person getPerson(int id) {
        return personDao.selectPerson(id);
    }

    public List<Person> getAllPersons() {
        return personDao.selectAllPersons();
    }

    public boolean updatePerson(Person person) {
        return personDao.updatePerson(person);
    }

    public boolean deletePerson(int id) {
        return personDao.deletePerson(id);
    }
}
