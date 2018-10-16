package fr.kflamand.PostPlatform.services;

import fr.kflamand.PostPlatform.Exception.UserAlreadyExistException;
import fr.kflamand.PostPlatform.persistance.models.PasswordResetToken;
import fr.kflamand.PostPlatform.persistance.models.User;
import fr.kflamand.PostPlatform.persistance.models.VerificationToken;
import fr.kflamand.PostPlatform.web.dto.UserDto;


import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IUserService {


    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

    User getUser(String verificationToken);

    User findUserByEmail(String email);

    User getUserByPasswordResetToken(String token);

    Optional<User> getUserByID(long id);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void changeUserPassword(User user, String password);

    List<String> getUsersFromSessionRegistry();

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Test Methods

    boolean checkIfValidOldPassword(User user, String password);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Tokens Methods
    void createVerificationTokenForUser(User user, String token);

    void createPasswordResetTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    PasswordResetToken getPasswordResetToken(String token);

    String validateVerificationToken(String token);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Options d'Auth

    //QR CODE
    //public String generateQRUrl(User user) throws UnsupportedEncodingException;

    // Double Identification
    // public User updateUser2FA(boolean use2FA);
}
