package com.thegreenwash.api.service;

import com.thegreenwash.api.exception.OtpNotFoundException;
import com.thegreenwash.api.model.Otp;
import com.thegreenwash.api.repository.AdminRepo;
import com.thegreenwash.api.repository.OtpRepo;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Service
public class OtpService {
    private final OtpRepo otpRepo;
    private final AdminRepo adminRepo;
    private final JavaMailSender javaMailSender;

    private final String ACCOUNT_SID = "AC13562ebbac0c1a44800c8645c349409d";
    private final String AUTH_TOKEN = "fcfceba609120d8413e2d2a48dc33d17";
    private final String MESSAGING_SERVICE_SID = "MG01d283bf617db49370825c099117df4f";

    @Autowired
    public OtpService(OtpRepo otpRepo, AdminRepo adminRepo, JavaMailSender javaMailSender) {
        this.otpRepo = otpRepo;
        this.adminRepo = adminRepo;
        this.javaMailSender = javaMailSender;
    }

    public void resendClientCellOtp(String cellNum) {
        Otp otp = otpRepo.findByClientId(cellNum);
        if(!Objects.isNull(otp)) {
            otpRepo.delete(otpRepo.findByClientId(cellNum));
            sendClientCellOtp(cellNum);
        }
    }

    public void resendClientEmailOtp(String email) {
        Otp otp = otpRepo.findByClientId(email);
        if(!Objects.isNull(otp)) {
            otpRepo.delete(otp);
            sendClientEmailOtp(email);
        }
    }

    public void sendClientCellOtp(String cellNum) {
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

    public void sendClientEmailOtp(String email) {
        OffsetDateTime currentTime = OffsetDateTime.now(ZoneId.of("Africa/Harare"));
        int min = 100000;
        int max = 999999;
        int otpNumber = (int) (Math.random() * (max - min + 1) + min);

        String subject = "One-Time Pin";
        String message = "Your Green Wash One-Time Pin is: " + otpNumber;
        try {
            sendEmail(email, subject, message);

            Otp otp = new Otp();
            otp.setClientId(email);
            otp.setOtpNumber(otpNumber);
            otp.setStartTime(currentTime.toString());
            otp.setEndTime(currentTime.plusMinutes(5).toString());
            otpRepo.save(otp);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public String verifyOtp(Integer otpNumber, String time, String id) {
        OffsetDateTime offset = OffsetDateTime.parse(time);
        Otp otp = otpRepo.findByOtpNumber(otpNumber);
        if(otp.getClientId() == id) {
            if (Objects.isNull(otp) == false) {
                if (offset.isBefore(OffsetDateTime.parse(otp.getEndTime()))) {
                    otpRepo.delete(otp);
                    return otp.getClientId();
                } else {
                    otpRepo.delete(otp);
                    return "OTP ran out of time";
                }
            } else {
                return "Invalid OTP";
            }
        }else{
            return "Invalid OTP";
        }
    }

    public void sendAdminCellOtp(String cellNum) {
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

    public void sendAdminEmailOtp(String email) {
        OffsetDateTime currentTime = OffsetDateTime.now(ZoneId.of("Africa/Harare"));
        int min = 100000;
        int max = 999999;
        int otpNumber = (int) (Math.random() * (max - min + 1) + min);

        String subject = "One-Time Pin";
        String message = "Your Green Wash One-Time Pin is: " + otpNumber;
        try {
            sendEmail(email, subject, message);

            Otp otp = new Otp();
            otp.setClientId(email);
            otp.setOtpNumber(otpNumber);
            otp.setStartTime(currentTime.toString());
            otp.setEndTime(currentTime.plusMinutes(5).toString());
            otpRepo.save(otp);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resendAdminCellOtp(String cellNum) {
        Otp otp = otpRepo.findByClientId(cellNum);
        if(!Objects.isNull(otp)) {
            otpRepo.delete(otpRepo.findByClientId(cellNum));
            sendAdminCellOtp(cellNum);
        }
    }

    public void resendAdminEmailOtp(String email) {
        Otp otp = otpRepo.findByClientId(email);
        if(!Objects.isNull(otp)) {
            otpRepo.delete(otp);
            sendAdminEmailOtp(email);
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
