# Spring Security User Password Encode

## Description
- This project is a continuation of the project linked here: https://github.com/RBaykan/Spring_Security_Register_Resend_Email_Verifacation_Token.
- The registered user's password will be encrypted using the BCrypt algorithm and saved to the database. For testing purposes, a Login API sends a POST request with the username and password.
  The user found by the username will have the entered password compared to the encrypted password.
  If the passwords match, the user will be notified that the login was successful. If they do not match, an "invalid credentials" message will be shown.
- The BCryptPasswordEncoder object, which encrypts and matches the password, is defined as a Bean and injected into the UserServiceImpl class as a PasswordEncoder object via the constructor.
  BCryptPasswordEncoder is a part of Spring Security and implements the PasswordEncoder interface.

