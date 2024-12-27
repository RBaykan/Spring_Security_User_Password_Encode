package com.spring.web.service.Impl;




import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.web.dto.CreateUser;
import com.spring.web.dto.LoginUser;
import com.spring.web.dto.UserDTO;
import com.spring.web.event.OnRegistrationCompleteEvent;
import com.spring.web.exceptions.EmailAlReady;
import com.spring.web.exceptions.UserAlReady;
import com.spring.web.exceptions.UserNotFound;
import com.spring.web.mapper.UserMapper;
import com.spring.web.model.User;
import com.spring.web.model.VerificationToken;
import com.spring.web.repository.UserRepository;
import com.spring.web.service.UserService;
import com.spring.web.service.VerificationTokenService;

import jakarta.transaction.Transactional;


@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final VerificationTokenService verificationTokenService;
	private final ApplicationEventPublisher publisher;
	private final PasswordEncoder encoder;
	
	public UserServiceImpl(UserRepository userRepository, 
			VerificationTokenService verifiticationTokenService,
			ApplicationEventPublisher publisher,
			PasswordEncoder encoder) {
		
		this.userRepository = userRepository;
		this.verificationTokenService = verifiticationTokenService;
		this.publisher = publisher;
		this.encoder = encoder;
	}
	
	

	@Override
	public List<UserDTO> getAllUser() {
		
		List<User> users = userRepository.findAll();
		return users.stream().map(
				(user) -> UserMapper.ModeltoDto(user)
				).collect(Collectors.toList());
	}


	@Override
	public UserDTO registerNewUser(CreateUser user){
	
		
		User newUser = new User();
		
		try {
		
		newUser.setEmail(user.getEmail());
		newUser.setPassword(encoder.encode(user.getPassword()));
		newUser.setUsername(user.getUsername());
		newUser.setFirstname(user.getFirstname());
		newUser.setLastname(user.getLastname());
		
		
		
		userRepository.save(newUser);
		
		
		
		publisher.publishEvent(new OnRegistrationCompleteEvent(newUser, "http://localhost:8080/api/user/registrationConfirm"));
		

		
		
		
		}catch(Exception e){
			
			if(e instanceof DataIntegrityViolationException ) {
				final String errMes = e.getMessage();
				
				if(errMes.contains("user")) {
					System.err.println("user benzerliği");
					throw new UserAlReady();
				}
				

				if(errMes.contains("email")) {
					System.err.println("email benzerliği");
					throw new EmailAlReady();
					
				}
			}
			
			
		}
		
		return UserMapper.ModeltoDto(newUser);
		
	}
	

	

	
	
		
	
	
	@Override
	@Transactional
	public String confirmRegistration(String token){
		
		
		VerificationToken t = verificationTokenService.getToken(token);
		
		if(!verificationTokenService.verificationToken(t)) {
			return "Token is not valid";
		}
		
		
		User user = t.getUser();
		user.setEnabled(true);
		userRepository.save(user);
		
		return "Accounnt is enabled";
	}



	@Override
	@Transactional
	public String reSendToken(String email) {
		
	
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFound());
	
		boolean test = true;
		
		if(user.isEnabled() && !test) {
			
			return "hesap zaten aktif";
		}
		
		List<VerificationToken> oldTokens = user.getTokens();
		System.err.println("hatayı fordan önce verdi, oldtokens'ten sonra verdi");
		for(var token : oldTokens ) {
			token.setValid(false);
		
		}
		
		
		userRepository.save(user);
		publisher.publishEvent(new OnRegistrationCompleteEvent(user, "http://localhost:8080/api/user/registrationConfirm"));
		
		
		
		return "Token yeniden gönderildi";
	}



	@Override
	public String login(LoginUser user) {
		User loginUser = userRepository.findByUsername(user.getUsername())
				.orElseThrow(() -> new UserAlReady());
		
		if(encoder.matches(user.getPassword(), loginUser.getPassword())) {
			return "Giriş başarılı";
		}
		
		return "Giriş başarısız";
	}




}
