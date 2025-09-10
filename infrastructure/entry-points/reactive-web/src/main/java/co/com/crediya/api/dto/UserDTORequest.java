package co.com.crediya.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserDTORequest {
    private Long id;

    @NotBlank(message = "{user.name.notblank}")
    private String name;

    @NotBlank(message = "{user.lastname.notblank}")
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;

    @NotBlank(message = "{user.email.notblank}")
    @Email(message = "{user.email.format}")
    private String email;

    @NotNull(message = "{user.salary.notnull}")
    @DecimalMin(value = "0.0", message = "{user.salary.min}")
    @DecimalMax(value = "15000000.0", message = "{user.salary.max}")
    @Digits(integer = 8, fraction = 2, message = "{user.salary.digits}")
    private BigDecimal baseSalary;

    @NotBlank(message = "{user.document.notblank}")
    private String identityDocument;

    private String password;
}