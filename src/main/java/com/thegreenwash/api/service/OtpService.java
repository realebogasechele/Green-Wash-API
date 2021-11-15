package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.OtpNotFoundException;
import com.thegreenwash.api.model.Otp;
import com.thegreenwash.api.repository.AdminRepo;
import com.thegreenwash.api.repository.OtpRepo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

@Service
public class OtpService {
    private final OtpRepo otpRepo;
    private final AdminRepo adminRepo;

    private final String ACCOUNT_SID = "AC13562ebbac0c1a44800c8645c349409d";
    private final String AUTH_TOKEN = "fcfceba609120d8413e2d2a48dc33d17";
    private final String MESSAGING_SERVICE_SID = "MG01d283bf617db49370825c099117df4f";

    @Autowired
    public OtpService(OtpRepo otpRepo, AdminRepo adminRepo) {
        this.otpRepo = otpRepo;
        this.adminRepo = adminRepo;
    }

    public void resendOtp(String cellNum) {
        otpRepo.delete(otpRepo.findByClientId(cellNum).orElseThrow(() -> new OtpNotFoundException("Otp does not exist!")));
        send(cellNum);
    }

    public void send(String cellNum) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            OffsetDateTime currentDate = OffsetDateTime.now(ZoneId.of("Africa/Harare"));
            int min = 100000;
            int max = 999999;
            int otpNumber = (int) (Math.random() * (max - min + 1) + min);

            Otp otp = new Otp();
            otp.setClientId(cellNum);
            otp.setOtpNumber(otpNumber);
            otp.setStartTime(currentDate.toString());
            otp.setEndTime(currentDate.plusMinutes(5).toString());
            otpRepo.save(otp);

            cellNum = cellNum.substring(0, 1).replace("0", "+27") + cellNum.substring(1);

            String msg = "Your Green Wash verification code is: " + otpNumber;
            Message.creator(new PhoneNumber(cellNum), MESSAGING_SERVICE_SID, msg)
                    .create();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String verifyOtp(Integer otpNumber, String time) {
        OffsetDateTime offset = OffsetDateTime.parse(time);
        Otp otp = otpRepo.findByOtpNumber(otpNumber);
        if (!Objects.isNull(otp)) {
            if (offset.isBefore(OffsetDateTime.parse(otp.getEndTime()))) {
                otpRepo.delete(otp);
                return otp.getClientId();
            } else {
                otpRepo.delete(otp);
                return "OTP ran out of time";
            }
        } else {
            return "OTP is invalid";
        }
    }

    public void sendAdminOtp(String cellNum) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            OffsetDateTime currentDate = OffsetDateTime.now(ZoneId.of("Africa/Harare"));
            int min = 100000;
            int max = 999999;
            int otpNumber = (int) (Math.random() * (max - min + 1) + min);

            Otp otp = new Otp();
            otp.setClientId(adminRepo.findByCellNum(cellNum).getAdminId());
            otp.setOtpNumber(otpNumber);
            otp.setStartTime(currentDate.toString());
            otp.setEndTime(currentDate.plusMinutes(5).toString());
            otpRepo.save(otp);

            cellNum = cellNum.substring(0, 1).replace("0", "+27") + cellNum.substring(1);

            String msg = "Your Green Wash verification code is: " + otpNumber;
            Message.creator(new PhoneNumber(cellNum), MESSAGING_SERVICE_SID, msg)
                    .create();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
