package com.spring.web.service.Impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.web.exceptions.TokenNotFound;
import com.spring.web.model.User;
import com.spring.web.model.VerificationToken;
import com.spring.web.repository.VerificationTokenRepository;
import com.spring.web.service.VerificationTokenService;


@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

	private final VerificationTokenRepository repository;

	
	public VerificationTokenServiceImpl(VerificationTokenRepository repository) {
		
		this.repository = repository;
	}
	

	@Override
	public List<VerificationToken> allTokens(User user) {
		
		List<VerificationToken> tokens = repository.findAll();
		List<VerificationToken> filteredToken = new ArrayList<VerificationToken>();
		
		for(var token : tokens) {
			
			if(token.getUser() == user) {
				filteredToken.add(token);
			}
		}
		
		return filteredToken;
		
		
	}

	
	@Override
	public VerificationToken getToken(String token){
		
	
		return repository.findByToken(token).orElseThrow(() -> new TokenNotFound());
		
	}

	@Override
	public VerificationToken createToken(User user, String token) {
		VerificationToken t = new VerificationToken();
		
		t.setUser(user);
		t.setToken(token);
		repository.save(t);
		return t;
		
	}


	@Override
	public Boolean verificationToken(VerificationToken token) {
		
		
		if(!token.isValid()) {
			return false;
		}
		
		Calendar cal = Calendar.getInstance();
		if(token.getExpiryDate().getTime() - cal.getTime().getTime() <=0) {
			
			token.setValid(false);
			repository.save(token);
			
			return false;
		}
		
		token.setValid(false);
		repository.save(token);
		
		return true;
		
	
	}

	
	@Override
	public void updateVeriticationToken(VerificationToken token) {
		
		
		
		repository.save(token);
		
	}



	
	
	

	
}
