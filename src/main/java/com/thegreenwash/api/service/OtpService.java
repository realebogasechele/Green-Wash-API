package com.thegreenwash.api.service;

import com.thegreenwash.api.model.Otp;
import com.thegreenwash.api.repository.OtpRepo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Objects;

@Service
public class OtpService {
    private final OtpRepo otpRepo;

    private final String ACCOUNT_SID ="AC13562ebbac0c1a44800c8645c349409d";
    private final String AUTH_TOKEN = "fcfceba609120d8413e2d2a48dc33d17";
    private final String FROM_NUMBER = "+18647148206";

    @Autowired
    public OtpService(OtpRepo otpRepo) {
        this.otpRepo = otpRepo;
    }

    public void send(String cellNum){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        int min = 100000;
        int max = 999999;
        int otpNumber=(int)(Math.random()*(max-min+1)+min);

        Otp otp = new Otp();
        otp.setClientId(cellNum);
        otp.setOtpNumber(otpNumber);
        otpRepo.save(otp);

            String msg = "Your Green Wash verification code is: " + otpNumber;
            Message.creator(new PhoneNumber(cellNum), new PhoneNumber(FROM_NUMBER), msg)
                    .create();
        }

        public String verifyOtp(Integer otpNumber){
            Otp otp = otpRepo.findByOtpNumber(otpNumber);
            if(!Objects.isNull(otp)){
                otpRepo.delete(otp);
                return "OTP matched!";
            }
            else{
                return "OTP is invalid";
            }
        }
    }
