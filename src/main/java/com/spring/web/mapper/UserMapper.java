package com.spring.web.mapper;

import com.spring.web.dto.UserDTO;
import com.spring.web.model.User;

public class UserMapper {
	
	public static User DTOtoModel(UserDTO model) {
	
		return  User.builder()
				.id(model.getId())
				.firstname(model.getFirstname())
				.lastname(model.getLastname())
				.username(model.getUsername())
				.password(model.getPassword())
				.email(model.getEmail())
				.enabled(model.isEnabled())
				.build();
				
			
		
	}
	
	public static UserDTO ModeltoDto(User model) {
			
		return  UserDTO.builder()
				.id(model.getId())
				.firstname(model.getFirstname())
				.lastname(model.getLastname())
				.username(model.getUsername())
				.password(model.getPassword())
				.enabled(model.isEnabled())
				.email(model.getEmail())
				.build();
				
			
		
	}
}
