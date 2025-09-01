package com.juaracoding.pcmspringboot5.service;

import com.juaracoding.pcmspringboot5.config.JwtConfig;
import com.juaracoding.pcmspringboot5.config.OtherConfig;
import com.juaracoding.pcmspringboot5.dto.val.ValLoginDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValRegisDTO;
import com.juaracoding.pcmspringboot5.dto.val.ValVerifyRegisDTO;
import com.juaracoding.pcmspringboot5.model.User;
import com.juaracoding.pcmspringboot5.repo.UserRepo;
import com.juaracoding.pcmspringboot5.security.BcryptImpl;
import com.juaracoding.pcmspringboot5.security.Crypto;
import com.juaracoding.pcmspringboot5.security.JwtUtility;
import com.juaracoding.pcmspringboot5.utils.GlobalResponse;
import com.juaracoding.pcmspringboot5.utils.SendMailOTP;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class AppDetailServices implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    Random rand ;

    @Autowired
    JwtUtility jwtUtility;

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

    //021-030
    public ResponseEntity<Object> login(User user, HttpServletRequest request){

        Optional<User> op = null;
        User userDB = null;
        try{
            if(user==null){
                return new GlobalResponse().tidakDapatDiproses("TRN00FV021");
            }
            op = userRepo.findByUsernameAndIsRegistered(user.getUsername(),true);
            if(op.isEmpty()){
                return new GlobalResponse().userTidakTerdaftar("TRN00FV022");
            }
            userDB = op.get();
            String strCombine = user.getUsername()+user.getPassword();
            System.out.println(strCombine);
            System.out.println(userDB.getPassword());
            if(!BcryptImpl.verifyHash(strCombine,userDB.getPassword())){
                return new GlobalResponse().usernameAtauPasswordSalah("TRN00FV023");
            }

        }catch(Exception e){
            return new GlobalResponse().internalServerError("TRN00FE011");
        }
        Map<String,Object> payload = new HashMap<>();
        payload.put("id",userDB.getId());
        payload.put("hp",userDB.getNoHp());
        payload.put("em",userDB.getEmail());
        payload.put("naleng",userDB.getNamaLengkap());

        String token = jwtUtility.doGenerateToken(payload,userDB.getUsername());
        if(JwtConfig.getTokenEncryptEnable().equals("y")){
            token = Crypto.performEncrypt(token);
        }

        Map<String,Object> data = new HashMap<>();
        data.put("menu","SEBENTAR !!");
        data.put("token",token);
        return new GlobalResponse().loginBerhasil(data);
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
    public User mapToEntity(ValLoginDTO valLoginDTO){
        User user = new User();
        user.setPassword(valLoginDTO.getPassword());
        user.setUsername(valLoginDTO.getUsername());
        return user;
    }

    public User mapToEntity(ValVerifyRegisDTO valVerifyRegisDTO){
        User user = new User();
        user.setOtp(valVerifyRegisDTO.getOtp());
        user.setEmail(valVerifyRegisDTO.getEmail());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> op = userRepo.findByUsernameAndIsRegistered(username,true);
        if(!op.isPresent()){
            throw new UsernameNotFoundException(username);
        }
        User user = op.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),user.getAuthorities());
    }
}
