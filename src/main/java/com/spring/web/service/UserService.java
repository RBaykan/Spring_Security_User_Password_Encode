package com.spring.web.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.web.dto.CreateUser;
import com.spring.web.dto.LoginUser;
import com.spring.web.dto.UserDTO;


@Service
public interface UserService {
	List<UserDTO> getAllUser();
	UserDTO registerNewUser(CreateUser user);
	String confirmRegistration(String token);
	String login(LoginUser user);
	String reSendToken(String email);


}
