/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.collabhangman.model.game;

import org.springframework.data.redis.core.StringRedisTemplate;


/**
 *
 * @author 2105409
 */


public class HangmanRedisGame extends HangmanGame{
    
    
    public int identificadorPartida;
    public StringRedisTemplate template;
    
   
    
    public HangmanRedisGame(String word,StringRedisTemplate srt,Integer id) {
        super(word);
        template=srt;
        identificadorPartida=id;
    }
    
    
  
    
    /**
     * @pre gameFinished==false
     * @param l new letter
     * @return the secret word with all the characters 'l' revealed
     */
    @Override
    public String addLetter(char l){                
        String value=(String)template.opsForHash().get("game:"+identificadorPartida, word);    
        for (int i=0;i<value.length();i++){
            if (value.charAt(i)==l){
                guessedWord[i]=l;
                template.opsForHash().put("game:"+identificadorPartida, guessedWord,"currentword");
            }            
        }    
        return new String(guessedWord);
    }
    
    @Override
    public synchronized boolean tryWord(String playerName,String s){
        String value=(String)template.opsForHash().get("game:"+identificadorPartida, word);    
        if (s.toLowerCase().equals(value)){
            winner=playerName;
            template.opsForHash().put("game:"+identificadorPartida, winner,"winner");
            gameFinished=true;
            template.opsForHash().put("game:"+identificadorPartida, gameFinished,"state");
            guessedWord=word.toCharArray();
            template.opsForHash().put("game:"+identificadorPartida, guessedWord,"currentword");
            return true;
        }
        return false;
    }
    
    @Override
    public boolean gameFinished(){
        return gameFinished;
    }
    
    /**
     * @pre gameFinished=true;
     * @return winner's name
     */
    @Override
    public String getWinnerName(){
        return winner;
    }
    
    @Override
    public String getCurrentGuessedWord(){
        System.out.println("LLEGO "+new String(guessedWord));
        return new String(guessedWord);
    }    
    
}
