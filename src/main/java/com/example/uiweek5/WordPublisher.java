package com.example.uiweek5;

import org.atmosphere.config.service.Get;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class WordPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    protected Word words;

    public WordPublisher() {
        this.words = new Word();
    }

    @PostMapping(value = "/addBad/{word}")
    public ArrayList<String> addBadWord(@PathVariable("word") String s) {
        words.badWords.add(s);
        return words.badWords;
    }

    @GetMapping(value = "/delBad/{word}")
    public ArrayList<String> deleteBadWord(@PathVariable("word") String s) {
        words.badWords.remove(words.badWords.indexOf(s));
        return words.badWords;
    }

    @PostMapping(value = "/addGood/{word}")
    public ArrayList<String> addGoodWord(@PathVariable("word") String s) {
        words.goodWords.add(s);
        return words.goodWords;
    }

    @GetMapping(value = "/delGood/{word}")
    public ArrayList<String> deleteGoodWord(@PathVariable("word") String s) {
        words.goodWords.remove(words.goodWords.indexOf(s));
        return words.goodWords;
    }

    @PostMapping(value = "/proof/{sentence}")
    public String proofSentence(@PathVariable("sentence") String s) {
        boolean goodCheck = false;
        boolean badCheck = false;
        for (String i : words.goodWords){
            if(s.contains(i)){
                goodCheck = true;
            }
        }
        for (String i : words.badWords){
            if(s.contains(i)){
                badCheck = true;
            }
        }
        if(badCheck && goodCheck){
            rabbitTemplate.convertAndSend("bothWord","", s);
            return "Found good word and bad word";
        }
        else if(goodCheck){
            rabbitTemplate.convertAndSend("goodWord", "good", s);
            return "Found good word";
        }
        else if(badCheck){
            rabbitTemplate.convertAndSend("badWord", "bad", s);
            return "Found bad word";
        }
        else{
            return "none";
        }
    }

    @GetMapping(value = "/getSentence")
    public Sentence getSentences(){
        Object s1 = rabbitTemplate.convertSendAndReceive("getQueue","getQ","");
        return ((Sentence) s1);
    }
}