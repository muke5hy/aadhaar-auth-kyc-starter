package com.tw.aadhar.controller;

import com.tw.aadhar.resource.AuthRequestData;
import com.tw.aadhar.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AuthController {

  @Autowired
  private AuthService authService;

  @RequestMapping(method = POST, value = "/authenticate")
  public ResponseEntity<Boolean> authenticate(@RequestBody AuthRequestData authRequestData) {
    return new ResponseEntity<>(authService.authenticate(authRequestData), HttpStatus.OK);
  }

}
