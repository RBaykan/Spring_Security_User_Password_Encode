package com.spring.web.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class VerificationToken {

	// Tokenin geçerli olduğu süre
	private static final int expiration = 60 * 24;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	private String token;
	
	private Date expiryDate;
	
	private boolean isValid;
	
	private Date calculateExpiryDate(int expiryTimeInMinutes) 
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		isValid = true;
		
		return new Date(cal.getTime().getTime());
	}
	
	public VerificationToken() {
		
		this.expiryDate = calculateExpiryDate(expiration);
	}
	
	
}
