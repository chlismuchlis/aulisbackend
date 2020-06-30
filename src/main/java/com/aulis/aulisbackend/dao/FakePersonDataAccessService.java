package com.aulis.aulisbackend.dao;

import com.aulis.aulisbackend.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("FakeDao")
public class FakePersonDataAccessService implements PersonDao{

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person){
        DB.add(new Person(id, person.getName()));
        return 1;
    }
    /*POSTMAN
    POST
    localhost:8080/api/v1/person
    --body raw json--
    {
        "name" : "SUep"
    }
    */

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if(personMaybe.isEmpty()){
            return 0;
        }
        DB.remove(personMaybe.get());
        return 1;
    }
    /*POSTMAN
    DELETE
    localhost:8080/api/v1/person/b40d6bd3-38a0-4fea-ba92-b100bdca1696
    {
    }
    */

    @Override
    public int updatePersonById(UUID id,Person update) {
        return selectPersonById(id)
                .map(person -> {
                    int indexOfPersonToDelete = DB.indexOf(person);
                    if(indexOfPersonToDelete >= 0){
                        DB.set(indexOfPersonToDelete, new Person(id, update.getName()));
                        return 1;
                    }
                    return 0;
                }).orElse(null);
    }
    /*POSTMAN
    PUT
    localhost:8080/api/v1/person/b917dd49-c73e-45f7-8b67-f719bdcebd08
    {
        "name" : "Mahmuddin Muhlis"
    }
    */

}






