package com.example.uiweek5;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
public class SentenceConsumer {
    protected Sentence sentences;

    public SentenceConsumer(){
        sentences = new Sentence();
    }

    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String s){
        sentences.goodSentences.add(s);
        System.out.println("In addGoodSentence Method : " + sentences.goodSentences);
    }

    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String s){
        sentences.badSentences.add(s);
        System.out.println("In badGoodSentence Method : " + sentences.badSentences);
    }

    @RabbitListener(queues = "GetQueue")
    public Sentence getSentences(){
        return this.sentences;
    }
}
