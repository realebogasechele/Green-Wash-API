package com.thegreenwashapi.controller;

import com.thegreenwashapi.model.web.Query;
import com.thegreenwashapi.model.web.Testimonial;
import com.thegreenwashapi.service.web.QueryService;
import com.thegreenwashapi.service.web.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/web")
public class WebController {

    private final QueryService queryService;
    private final TestimonialService testimonialService;

    @Autowired
    public WebController(QueryService queryService, TestimonialService testimonialService) {
        this.queryService = queryService;
        this.testimonialService = testimonialService;
    }
    @PostMapping("/sendMail/{to}/{subject}/{message}")
    public ResponseEntity<String> sendEmail(@PathVariable("to") String to,
                                            @PathVariable("subject") String subject,
                                            @PathVariable("message") String message){
        queryService.sendEmail(to, subject, message);
        return new ResponseEntity<>("sent", HttpStatus.OK);
    }

    //Query Related
    @PostMapping("/query/add")
    public ResponseEntity<String> addQuery(@RequestBody Query query){
        String response = queryService.addQuery(query);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/query/get/all")
    public ResponseEntity<List<Query>> getAllQueries(){
        List<Query> queries = queryService.findAllNewQueries();
        return new ResponseEntity<>(queries, HttpStatus.OK);
    }

    @PostMapping("/query/respond")
    public ResponseEntity<String> queryResponse(@RequestBody Query query){
        String response = queryService.answerQuery(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Testimonial Related
    @PostMapping("/testimonial/add")
    public ResponseEntity<String> addTestimonial(@RequestBody Testimonial testimonial){
        String response = testimonialService.addTestimonial(testimonial);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/testimonial/get/all")
    public ResponseEntity<List<Testimonial>> getTestimonials(){
        List<Testimonial> response = testimonialService.getTestimonials();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
