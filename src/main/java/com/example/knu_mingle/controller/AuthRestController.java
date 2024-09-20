package com.example.knu_mingle.controller;


import com.example.knu_mingle.domain.User;

import com.example.knu_mingle.service.AuthService;
import com.example.knu_mingle.service.MailManager;
import com.example.knu_mingle.service.SHA256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    AuthService authService;



    MailManager mailManager;



    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        authService.CreateUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/duplicate")
    public boolean EmailDuplicate(@RequestParam String email) {
        return authService.DuplicateEmail(email);
    }

    @PostMapping("/sendMail") //
    @ResponseBody  //AJAX후 값을 리턴하기위해 작성
    public String SendMail(String email) throws Exception {
        UUID uuid = UUID.randomUUID(); // 랜덤한 UUID 생성
        String key = uuid.toString().substring(0, 7); // UUID 문자열 중 7자리만 사용하여 인증번호 생성
        String sub ="인증번호 입력을 위한 메일 전송";
        String con = "인증 번호 : "+key;
        mailManager.send(email,sub,con);
        key = SHA256Util.getEncrypt(key, email);
        return key;
    }
    @PostMapping("/checkMail") //
    @ResponseBody
    public boolean CheckMail(String key, String insertKey,String email) throws Exception {
        insertKey = SHA256Util.getEncrypt(insertKey, email);

        if(key.equals(insertKey)) {
            return true;
        }
        return false;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            authService.DeleteUser(userId);

            return ResponseEntity.ok("회원 탈퇴가 성공적으로 처리되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원 탈퇴 중 오류가 발생했습니다.");
        }
    }










}
