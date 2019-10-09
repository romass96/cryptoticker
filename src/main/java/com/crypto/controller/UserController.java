package com.crypto.controller;

import com.crypto.dto.UserDTO;
import com.crypto.model.PasswordResetToken;
import com.crypto.model.User;
import com.crypto.service.EmailService;
import com.crypto.service.PasswordResetService;
import com.crypto.service.UserService;
import com.crypto.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetService passwordResetService;

    @GetMapping("/login")
    public String loginPage(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your email and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("userForm", new UserDTO());
        return "register";
    }

    @PostMapping("/registration")
    public ModelAndView register(@ModelAttribute("userForm") @Valid UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("register", "userForm", userDTO);
        }
        return new ModelAndView("successRegistration");
    }


    @GetMapping("/user/resetPassword")
    public String resetPasswordPage() {
        return "resetPassword";
    }

    @PostMapping("/user/resetPassword")
    public String resetPassword(@RequestParam String email, HttpServletRequest request) {
        User user = userService.findUserByEmail(email);
        String token = UUID.randomUUID().toString();
        passwordResetService.createPasswordResetTokenForUser(user, token);
        emailService.sendResetTokenEmail(user, Utils.getApplicationUrl(request), token);
        return "login";
    }

    @GetMapping("/user/changePassword")
    public String changePasswordPage(@RequestParam Long id, @RequestParam String token) {
        PasswordResetToken resetToken = passwordResetService.findByToken(token);
        final PasswordResetToken.TokenStatus result = passwordResetService.validatePasswordResetToken(id, resetToken);
        if (result == PasswordResetToken.TokenStatus.CORRECT) {
            passwordResetService.authenticateUserForResetPassword(resetToken);
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
