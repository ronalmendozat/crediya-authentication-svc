package co.com.crediya.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(1L),
    USER(2L),
    ASESOR(3L),
    CLIENTE(4L);
    private final Long id;
}
