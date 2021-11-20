package com.thegreenwashapi.service.web;

import com.thegreenwashapi.exception.QueryNotFoundException;
import com.thegreenwashapi.model.web.Query;
import com.thegreenwashapi.repository.web.QueryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QueryService {

    private final QueryRepo queryRepo;
    private final JavaMailSender javaMailSender;

    @Autowired
    public QueryService(QueryRepo queryRepo, JavaMailSender javaMailSender) {
        this.queryRepo = queryRepo;
        this.javaMailSender = javaMailSender;
    }

    public String addQuery(Query query){
        try {
            Query temp = queryRepo.findByCellNoAndNewQuery(query.getCellNo(), true);
            if(Objects.isNull(temp)) {
                query.setNewQuery(true);
                queryRepo.save(query);
                return "Query Added!";
            }else{
                return "Your Query is still being processed. You cannot submit a new one until " +
                        "your current one is processed.";
            }
        }catch(Exception ex){
            return "Query Not Added!";
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
            return "Could not process request";
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
        message.setFrom("teamrapsza@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
