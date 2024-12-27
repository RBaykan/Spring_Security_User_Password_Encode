# Spring Security User Password Encode

## Description
- This project is a continuation of the project linked here: https://github.com/RBaykan/Spring_Security_Register_Resend_Email_Verifacation_Token.
- The registered user's password will be encrypted using the `BCrypt` algorithm and saved to the database. For testing purposes, a Login API sends a POST request with the username and password.
  The user found by the username will have the entered password compared to the encrypted password.
  If the passwords match, the user will be notified that the login was successful. If they do not match, an "invalid credentials" message will be shown.
- The `BCryptPasswordEncoder` object, which encrypts and matches the password, is defined as a Bean and injected into the UserServiceImpl class as a `PasswordEncoder` object via the constructor.
  `BCryptPasswordEncoder` is a part of Spring Security and implements the `PasswordEncoder` interface.

## Steps to Run the Project
1. **Clone the repository**:
```bash
git clone https://github.com/RBaykan/Spring_Security_Register_Email_Verification_Token.git
```
2. **Navigate to the project directory**:
```bash
cd Spring_Security_Register_Email_Verification_Token
```
3. **Dependencies and build the project**:
```bash
mvn clean install
```
- Run the application:
```bash
mvn spring-boot:run
```
The application should now be running on `http://localhost:8080.`

4. Follow the `CreateDTO` object and prepare a JSON file accordingly, then send a POST request to the following address: `http://localhost:8080/api/user`.
   When a new user is created, their password will be encrypted in the database.


