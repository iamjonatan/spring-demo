package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakePersonDao")
public class FakePersonDataAccessObject implements PersonDao{

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> getAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream().filter( p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return selectPersonById(id).map(p ->{
            int indexPerson = DB.indexOf(p);
            if(indexPerson >= 0) {
                DB.set(indexPerson, new Person(id, person.getName()));
                return 1;
            }
            return 0;
        }).orElse( 0);
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = selectPersonById(id);
        if(person.isEmpty()) {
            return 0;
        } else {
            DB.remove(person.get());
            return 1;
        }
    }
}
