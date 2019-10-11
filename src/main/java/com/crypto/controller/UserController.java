package com.crypto.controller;

import com.crypto.dto.UserDTO;
import com.crypto.model.PasswordResetToken;
import com.crypto.model.Token;
import com.crypto.model.User;
import com.crypto.model.VerificationToken;
import com.crypto.service.EmailService;
import com.crypto.service.TokenService;
import com.crypto.service.UserService;
import com.crypto.util.Utils;
import com.crypto.util.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/login")
    public String loginPage(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your email and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "register";
    }

    @PostMapping("/registration")
    public ResponseEntity<List<ObjectError>> register(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        final HttpStatus httpStatus;
        ValidationResult<User> validationResult = userService.createUserAccount(userDTO);
        if (validationResult.isSuccess()) {
            User user = validationResult.getValue().get();
            VerificationToken verificationToken = tokenService.createVerificationTokenForUser(user);
            emailService.sendVerificationEmail(user, Utils.getApplicationUrl(request), verificationToken.getToken());
            httpStatus = HttpStatus.CREATED;
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(validationResult.getErrors(), httpStatus);
    }

    @GetMapping("/successRegistration")
    public String successRegistration() {
        return "successRegistration";
    }

    @GetMapping("/user/verifyEmail")
    public String verifyEmail(@RequestParam Long id, @RequestParam String token) {
        VerificationToken verificationToken = tokenService.findVerificationTokenByToken(token);
        final Token.TokenStatus result = tokenService.validateToken(id, verificationToken);
        if (result == Token.TokenStatus.CORRECT) {
            userService.setUserEnabled(id, true);
            return "login";
        } else {
            return "";
        }
    }

    @GetMapping("/forgotPassword")
    public String resetPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/user/resetPassword")
    public String resetPassword(@RequestParam String email, HttpServletRequest request) {
        User user = userService.findUserByEmail(email);
        PasswordResetToken resetToken = tokenService.createPasswordResetTokenForUser(user);
        emailService.sendResetTokenEmail(user, Utils.getApplicationUrl(request), resetToken.getToken());
        return "login";
    }

    @GetMapping("/user/changePassword")
    public String changePasswordPage(@RequestParam Long id, @RequestParam String token) {
        PasswordResetToken resetToken = tokenService.findResetTokenByToken(token);
        final Token.TokenStatus result = tokenService.validateToken(id, resetToken);
        if (result == Token.TokenStatus.CORRECT) {
            tokenService.authenticateUserForResetPassword(resetToken);
            return "changePassword";
        } else {
            return "";
        }
    }

    @PostMapping("/user/changePassword")
    public String changePassword(@RequestParam String password) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updateUserPassword(user, password);
        return "login";
    }


}
