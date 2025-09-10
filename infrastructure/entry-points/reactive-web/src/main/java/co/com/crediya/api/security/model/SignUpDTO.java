package co.com.crediya.api.security.model;

public record SignUpDTO(String name,
                        String lastName,
                        String email,
                        String password) {}