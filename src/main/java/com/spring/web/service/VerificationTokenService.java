package com.spring.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.web.model.User;
import com.spring.web.model.VerificationToken;

@Service
public interface VerificationTokenService {

	List<VerificationToken> allTokens(User user);
	VerificationToken getToken(String token);
	VerificationToken createToken(User user, String token);
	Boolean verificationToken(VerificationToken token);
	
	void updateVeriticationToken(VerificationToken token);
	
	
}
