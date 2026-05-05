package com.guiaserv.publico.config;

import com.guiaserv.publico.model.Perfil;
import com.guiaserv.publico.model.Usuario;
import com.guiaserv.publico.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        criarUsuarioAdmin();
    }

    private void criarUsuarioAdmin() {
        String emailAdmin = "admin@guiaserv.com";

        if (!usuarioRepository.existsByEmail(emailAdmin)) {
            Usuario admin = Usuario.builder()
                    .nome("Administrador")
                    .email(emailAdmin)
                    .senha(passwordEncoder.encode("admin123"))
                    .perfil(Perfil.ADMIN)
                    .build();

            usuarioRepository.save(admin);
        }
    }
}