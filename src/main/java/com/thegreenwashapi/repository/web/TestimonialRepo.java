package com.thegreenwashapi.repository.web;

import com.thegreenwashapi.model.web.Testimonial;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestimonialRepo extends MongoRepository<Testimonial, String> {
    Testimonial findByCellNum(String cellNum);
}
