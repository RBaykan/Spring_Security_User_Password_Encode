package com.spring.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This email already exits" )
public class EmailAlReady extends RuntimeException{
	
	
	

}
