package com.thegreenwash.api.repository;

import com.thegreenwash.api.model.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OtpRepo extends MongoRepository<Otp, String> {
    Otp findByOtpNumber(Integer otp);
}
