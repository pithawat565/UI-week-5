package com.example.uiweek5;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route(value = "index2")
public class MyView2 extends VerticalLayout {
    private HorizontalLayout container;
    private VerticalLayout containerLeft, containerRight;
    private Button addGood, addBad, addSentence, showSentences;
    private TextField word, sentence;
    private TextArea goodSentence, badSentence;
    private ComboBox<ArrayList> goodWord, badWord;

    public MyView2(){
        containerLeft = new VerticalLayout();
        word = new TextField("Add Word");
        word.setWidth("550px");

        addGood = new Button("Add Good Word");
        addBad = new Button("Add Bad Word");
        addGood.setWidth("550px");
        addBad.setWidth("550px");

        goodWord = new ComboBox<ArrayList>("Good Words");
        goodWord.setWidth("550px");
        badWord = new ComboBox<ArrayList>("Bad Words");
        badWord.setWidth("550px");

        containerLeft.add(word, addGood, addBad, goodWord, badWord);

        containerRight = new VerticalLayout();
        sentence = new TextField("Add Sentence");
        sentence.setWidth("550px");

        addSentence = new Button("Add sentence");
        addSentence.setWidth("550px");

        goodSentence = new TextArea("Good Sentences");
        goodSentence.setWidth("550px");
        badSentence = new TextArea("Bad Sentences");
        badSentence.setWidth("550px");

        showSentences = new Button("Show Sentence");
        showSentences.setWidth("550px");

        containerRight.add(sentence, addSentence, goodSentence, badSentence, showSentences);

        container = new HorizontalLayout();
        container.add(containerLeft, containerRight);
        this.add(container);

        addGood.addClickListener(event -> {
           String word = this.word.getValue();

           ArrayList out = WebClient.create()
                   .post()
                   .uri("http://localhost:8080/addGood/"+word)
                   .retrieve()
                   .bodyToMono(ArrayList.class)
                   .block();

            goodWord.setItems(out);
        });

        addBad.addClickListener(event -> {
            String word = this.word.getValue();

            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addBad/"+word)
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();

            badWord.setItems(out);
        });

        addSentence.addClickListener(event -> {
            String sentence = this.sentence.getValue();

            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proof/"+sentence)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            new Notification(out).open();
        });

        showSentences.addClickListener(event -> {
            String tempGood = "";
            String tempBad = "";
            Sentence out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();
            for (String text : out.goodSentences){
                tempGood += text + "\n";
            }
            for (String text : out.badSentences){
                tempBad += text + "\n";
            }
            goodSentence.setValue(tempGood);
            badSentence.setValue(tempBad);
        });
    }
}
