package com.juaracoding.pcmspringboot5.dto.val;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ValVerifyRegisDTO {

    /** field untuk email , masukkan nilai sesuai dengan dokumen RFC 5322 */
    @Pattern(regexp = "^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?![.])[a-zA-Z0-9._%+-]+(?:(?<!\\\\)[.][a-zA-Z0-9-]+)*?)@[a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,64})+$",
            message = "Format tidak valid ex : user_name123@sub.domain.com")
    private String email;


    @Pattern(regexp = "^[0-9]{6}$")
    private String otp;

    public @Pattern(regexp = "^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?![.])[a-zA-Z0-9._%+-]+(?:(?<!\\\\)[.][a-zA-Z0-9-]+)*?)@[a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,64})+$",
            message = "Format tidak valid ex : user_name123@sub.domain.com") String getEmail() {
        return email;
    }

    public void setEmail(@Pattern(regexp = "^(?=.{1,256})(?=.{1,64}@.{1,255}$)(?:(?![.])[a-zA-Z0-9._%+-]+(?:(?<!\\\\)[.][a-zA-Z0-9-]+)*?)@[a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,64})+$",
            message = "Format tidak valid ex : user_name123@sub.domain.com") String email) {
        this.email = email;
    }

    public @Pattern(regexp = "^[0-9]{6}$") String getOtp() {
        return otp;
    }

    public void setOtp(@Pattern(regexp = "^[0-9]{6}$") String otp) {
        this.otp = otp;
    }
}
