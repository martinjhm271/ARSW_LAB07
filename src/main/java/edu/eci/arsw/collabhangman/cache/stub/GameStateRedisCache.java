/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.cache.stub;

import edu.eci.arsw.collabhangman.cache.GameStateCache;
import edu.eci.arsw.collabhangman.model.game.HangmanGame;
import edu.eci.arsw.collabhangman.model.game.HangmanRedisGame;
import edu.eci.arsw.collabhangman.services.GameCreationException;
import edu.eci.arsw.collabhangman.services.GameServicesException;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2105409
 */


@Service
public class GameStateRedisCache implements GameStateCache{
    
        
    public GameStateRedisCache(){
    
    }
    
    @Autowired
    private StringRedisTemplate template;  
    
    
    
    @Override
    public void createGame(int id, String word) throws GameCreationException {
        String value=(String)template.opsForHash().get("game:"+id, word); 
        if (!value.equals("nil")){
            System.out.println("NOS CAGAMOS");
            throw new GameCreationException("The game "+id+" already exist.");
        }
        else{
            System.out.println("CREO NUEVO HAGMAN "+id+"  "+word);
            template.opsForHash().put("game:"+id, word,"word");
            template.opsForHash().put("game:"+id, "","currentword");
            template.opsForHash().put("game:"+id, "","state");
            template.opsForHash().put("game:"+id, "","winner");
        }
    }

    @Override
    public HangmanRedisGame getGame(int gameid) throws GameServicesException {
        return new HangmanRedisGame("", template, gameid);
    }

    public StringRedisTemplate getTemplate() {
        return template;
    }

    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }
    
    
}
