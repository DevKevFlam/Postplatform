package fr.kflamand.PostPlatform.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
