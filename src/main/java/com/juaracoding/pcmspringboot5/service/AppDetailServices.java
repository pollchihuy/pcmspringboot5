package com.juaracoding.pcmspringboot5.service;

import com.juaracoding.pcmspringboot5.config.OtherConfig;
import com.juaracoding.pcmspringboot5.dto.val.ValRegisDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValUserDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValVerifyRegisDTO;
import com.juaracoding.pcmspringboot5.model.User;
import com.juaracoding.pcmspringboot5.repo.UserRepo;
import com.juaracoding.pcmspringboot5.security.BcryptImpl;
import com.juaracoding.pcmspringboot5.utils.GlobalFunction;
import com.juaracoding.pcmspringboot5.utils.GlobalResponse;
import com.juaracoding.pcmspringboot5.utils.SendMailOTP;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


/**
 * Platform Code : TRN
 * Modul Code : 00
 */
@Service
@Transactional
public class AppDetailServices {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    Random rand ;

    //001-010
    public ResponseEntity<Object> regis(User user, HttpServletRequest request){

        Integer otp = 0;
        try{
            if(user==null){
                return new GlobalResponse().tidakDapatDiproses("TRN00FV001");
            }
            otp = rand.nextInt(111111,999999);
            user.setOtp(BcryptImpl.hash(String.valueOf(otp)));
            userRepo.save(user);
        }catch(Exception e){
            return new GlobalResponse().internalServerError("TRN00FE001");
        }
        if(OtherConfig.getSmtpEnable().equals("y")){
            new SendMailOTP().verifyRegisOTP(
                    "Verifikasi Registrasi",
                    user.getNamaLengkap(),
                    user.getEmail(),
                    String.valueOf(rand.nextInt(111111,999999)),
                    "ver_regis.html"
            );
        }
        Map<String,Object> data = new HashMap<>();
        if(OtherConfig.getEnableAutomationTesting().equals("y")){
            data.put("otp",otp);
        }
        data.put("email",user.getEmail());

        return new GlobalResponse().otpTerkirim(data);
    }

    //011-020
    public ResponseEntity<Object> verifyRegis(User user, HttpServletRequest request){

        Optional<User> op = null;
        try{
            if(user==null){
                return new GlobalResponse().tidakDapatDiproses("TRN00FV011");
            }
            op = userRepo.findByEmail(user.getEmail());
            if(op.isEmpty()){
                return new GlobalResponse().tidakDapatDiproses("TRN00FV012");
            }
            User userDB = op.get();
            if(!BcryptImpl.verifyHash(user.getOtp(),userDB.getOtp())){
                return new GlobalResponse().otpSalah("TRN00FV013");
            }
            userDB.setRegistered(true);
            userDB.setModifiedBy(userDB.getId());
            userDB.setOtp(BcryptImpl.hash(String.valueOf(rand.nextInt(10))));
        }catch(Exception e){
            return new GlobalResponse().internalServerError("TRN00FE011");
        }
        return new GlobalResponse().regisBerhasil();
    }

    public User mapToEntity(ValRegisDTO valRegisDTO){
        User user = new User();
        user.setNamaLengkap(valRegisDTO.getNamaLengkap());
        user.setPassword(BcryptImpl.hash(valRegisDTO.getUsername()+valRegisDTO.getPassword()));
        user.setUsername(valRegisDTO.getUsername());
        user.setNoHp(valRegisDTO.getNoHp());
        user.setTanggalLahir(valRegisDTO.getTanggalLahir());
        user.setEmail(valRegisDTO.getEmail());
        return user;
    }

    public User mapToEntity(ValVerifyRegisDTO valVerifyRegisDTO){
        User user = new User();
        user.setOtp(valVerifyRegisDTO.getOtp());
        user.setEmail(valVerifyRegisDTO.getEmail());
        return user;
    }
}
