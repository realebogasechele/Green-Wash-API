package com.thegreenwashapi.service.web;

import com.thegreenwashapi.model.web.Testimonial;
import com.thegreenwashapi.repository.web.TestimonialRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class TestimonialService {
    private final TestimonialRepo testimonialRepo;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public TestimonialService(TestimonialRepo testimonialRepo, MongoTemplate mongoTemplate) {
        this.testimonialRepo = testimonialRepo;
        this.mongoTemplate = mongoTemplate;
    }

    public String addTestimonial(@NotNull Testimonial testimonial){
        Testimonial temp = testimonialRepo.findByCellNum(testimonial.getCellNum());
        if(Objects.isNull(temp)) {
            testimonialRepo.save(testimonial);
            return "Thank You For Your Submission.";
        }else{
            return "User with this Cell Number already submitted a testimonial.";
        }
    }

    public List<Testimonial> getTestimonials(){
        Query query = new Query();
        query.addCriteria(Criteria.where("rating").gte(4));
        List<Testimonial> testimonials = mongoTemplate.find(query,Testimonial.class);
        return testimonials;
    }
}
