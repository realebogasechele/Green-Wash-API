package com.thegreenwash.api.repository.web;

import com.thegreenwash.api.model.web.Testimonial;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestimonialRepo extends MongoRepository<Testimonial, String> {
}
