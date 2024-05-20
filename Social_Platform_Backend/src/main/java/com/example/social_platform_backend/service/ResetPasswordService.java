package com.example.social_platform_backend.service;

import com.example.social_platform_backend.facade.ResetPassword;
import com.example.social_platform_backend.repository.ResetPasswordRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService {

    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    public ResetPasswordService(ResetPasswordRepository resetPasswordRepository) {
        this.resetPasswordRepository = resetPasswordRepository;
    }

    //saves in a table that has associated the user and token for resetting password.
    public void saveInResetPasswordTable(String username, String token) {
        ResetPassword resetPassword = new ResetPassword();

        resetPassword.setToken(token);
        resetPassword.setUsername(username);

        resetPasswordRepository.save(resetPassword);

    }

    public String findUsernameByToken(String token) {
        return resetPasswordRepository.findUsernameByToken(token);
    }

    //deletes the record from the table after resetting the password, because the token is no longer available
    @Transactional
    public void deleteResetPasswordByUsername(String username) {
        resetPasswordRepository.deleteByUsername(username);
    }
}
