package com.thegreenwash.api.service.web;

import com.thegreenwash.api.exception.QueryNotFoundException;
import com.thegreenwash.api.model.web.Query;
import com.thegreenwash.api.repository.web.QueryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryService {
    @Autowired
    private final QueryRepo queryRepo;
    private final JavaMailSender javaMailSender;

    public QueryService(QueryRepo queryRepo, JavaMailSender javaMailSender) {
        this.queryRepo = queryRepo;
        this.javaMailSender = javaMailSender;
    }

    public String addQuery(Query query){
        try {
            query.setNewQuery(true);
            queryRepo.save(query);
            return "Query Added!";
        }catch(Exception ex){
            return "Error: Query Not Added!";
        }
    }
    public String answerQuery(Query query){
        try{
            queryRepo.findById(query.getId()).orElseThrow(()-> new QueryNotFoundException("Not Found!"));
            sendEmail(query.getEmail(), query.getResponseTitle(), query.getResponse());
            query.setNewQuery(false);
            queryRepo.save(query);
            return "Query Replied to successfully!";
        }
        catch(Exception ex){
            return "Error: could not process request";
        }
    }

    public List<Query> findAllNewQueries(){
        List<Query> response = new ArrayList<>();
        try {
            response = queryRepo.findAllByNewQuery(true);
            return response;
        }catch (Exception ex){
            return response;
        }
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("admin@thegreenwash.co.za");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
