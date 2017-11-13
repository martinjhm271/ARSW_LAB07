/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.model.game;

import edu.eci.arsw.collabhangman.services.GameServicesException;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 *
 * @author 2105409
 */
public class HangmanRedisGame extends HangmanGame {

    public int identificadorPartida;
    public StringRedisTemplate template;

    public HangmanRedisGame(String word, StringRedisTemplate srt, Integer id) {
        super(word);
        template = srt;
        identificadorPartida = id;
    }

    /**
     * @throws edu.eci.arsw.collabhangman.services.GameServicesException
     * @pre gameFinished==false
     * @param l new letter
     * @return the secret word with all the characters 'l' revealed
     */
    @Override
    public String addLetter(char l) throws GameServicesException {
        String value = (String) template.opsForHash().get("game:" + identificadorPartida, "word");
        String value2 = (String) template.opsForHash().get("game:" + identificadorPartida, "currentWord");
        if (value!=null) {
            char[] myNameChars = value2.toCharArray();
            for (int i = 0; i < value.length(); i++) {
                if (value.charAt(i) == l && i < myNameChars.length) {
                    myNameChars[i] = l;
                }
            }
            value2 = String.valueOf(myNameChars);
            template.opsForHash().put("game:" + identificadorPartida, "currentWord", value2);
        } else {
            throw new GameServicesException("No existe dicha partida!!");
        }

        return value2;
    }

    @Override
    public synchronized boolean tryWord(String playerName, String s) throws GameServicesException {
        String value = (String) template.opsForHash().get("game:" + identificadorPartida, "word");
        if (value!=null) {
            if (s.toLowerCase().equals(value)) {
                template.opsForHash().put("game:" + identificadorPartida, "winner", playerName);
                template.opsForHash().put("game:" + identificadorPartida, "state", "true");
                template.opsForHash().put("game:" + identificadorPartida, "currentWord", value);
                return true;
            }
        } else {
            throw new GameServicesException("No existe dicha partida!!");
        }
        return false;
    }

    @Override
    public boolean gameFinished() throws GameServicesException {
        String value2 = (String) template.opsForHash().get("game:" + identificadorPartida, "state");
        if(value2==null){
            throw new GameServicesException("No existe dicha partida!!");
        }
        return !value2.equals("false");
    }

    /**
     * @throws edu.eci.arsw.collabhangman.services.GameServicesException
     * @pre gameFinished=true;
     * @return winner's name
     */
    @Override
    public String getWinnerName() throws GameServicesException {
        String value2 = (String) template.opsForHash().get("game:" + identificadorPartida, "winner");
        if(value2==null){
            throw new GameServicesException("No existe dicha partida!!");
        }
        return value2;
    }

    @Override
    public String getCurrentGuessedWord() throws GameServicesException {
        String value2 = (String) template.opsForHash().get("game:" + identificadorPartida, "currentWord");
        if(value2==null){
            throw new GameServicesException("No existe dicha partida!!");
        }
        return value2;
    }

}
