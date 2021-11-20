package com.thegreenwashapi.repository;

import com.thegreenwashapi.model.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OtpRepo extends MongoRepository<Otp, String> {
    Otp findByOtpNumber(Integer otp);

    Otp findByClientId(String clientId);
}
