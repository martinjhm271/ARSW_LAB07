/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.persistence;

import edu.eci.arsw.collabhangman.model.game.entities.User;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


/**
 *
 * @author hcadavid
 */
public interface UsersRepository extends MongoRepository<User, Integer>{

    public User findById(Integer id) throws PersistenceException;   
    
    @Query("{\"scores\": {\"$elemMatch\": {\"puntaje\": {\"$gte\": ?0}}}}")
    public Set<User> getAllUsers(Integer puntaje) throws PersistenceException;
}
