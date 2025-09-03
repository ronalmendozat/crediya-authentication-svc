package co.com.crediya.usecase.user.helper;

import co.com.crediya.model.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserUseCaseTestHelper {
    public static User crearUsuario() {
        return User.builder()
                .id(1L)
                .name("Ronal")
                .lastName("Mendoza")
                .birthDate(LocalDate.now())
                .address("Calle las manzanas 123")
                .phoneNumber("987654321")
                .email("ronal@gmail.com")
                .baseSalary(new BigDecimal(5000))
                .build();

    }
}
