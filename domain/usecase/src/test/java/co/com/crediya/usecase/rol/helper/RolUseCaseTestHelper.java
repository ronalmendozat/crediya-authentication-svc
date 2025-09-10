package co.com.crediya.usecase.rol.helper;

import co.com.crediya.model.rol.Rol;

public class RolUseCaseTestHelper {

    public static Rol crearRol(){
        return Rol.builder()
                .id(1L)
                .name("ADMIN")
                .description("Admin")
                .build();
    }
}
