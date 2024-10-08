package fr.ldnr.FullStackBackend.security;

public class SecurityConstants {
    public static final String HEADER_STRING = "Authorization";
    public static final String SECRET = "arthur@gmail.com";
    public static final long EXPIRATION_TIME = 120 * 60 *1000;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ERROR_MSG = "error-message";
}

