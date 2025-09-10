package co.com.crediya.r2dbc.entity;

import jakarta.persistence.Column;
import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("tb_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Column(name = "base_salary")
    private BigDecimal baseSalary;

    private String identityDocument;

    private String password;

    @Column(name = "rol_id")
    private Long rolId;

    private Boolean active;
}
