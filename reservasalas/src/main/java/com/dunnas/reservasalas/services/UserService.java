package com.dunnas.reservasalas.services;

import com.dunnas.reservasalas.enums.UserRole;
import com.dunnas.reservasalas.model.Role;
import com.dunnas.reservasalas.model.User;
import com.dunnas.reservasalas.repository.RoleRepository;
import com.dunnas.reservasalas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void createNewUser(User user, String userRole, Authentication authentication) {

        //TODO: Substituir por validação usando SQL
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("Usuário com mesmo nome já existente");
        }

        System.out.println(user);

        boolean isCurrentUserAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_"+UserRole.ADMINISTRADOR));


        if(isCurrentUserAdmin) {
            Optional<Role> roleOptional = roleRepository.findByName(userRole);
            if (roleOptional.isPresent()) {
                user.setRoles(Set.of(roleOptional.get()));
            }

        } else {
            Optional<Role> role = roleRepository.findByName(UserRole.CLIENTE.name());
            if(role.isPresent()) {
                user.setRoles(Set.of(role.get()));
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println("Usuario criado com sucesso");
    }

    private void createRoles(){
        List<Role> roles = new ArrayList<>();

        roles.add(new Role(null,UserRole.ADMINISTRADOR.name()));
        roles.add(new Role(null,UserRole.RECEPCIONISTA.name()));
        roles.add(new Role(null,UserRole.CLIENTE.name()));

        roleRepository.saveAll(roles);
    }

    @Transactional
    public void CreateFirstAdmin(){
        String username = "admin";
        String password = "admin";

        if(!userRepository.existsByUsername(username)) {
            createRoles();

            User admin = new User();
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode(password));

            Optional<Role> role = roleRepository.findByName(UserRole.ADMINISTRADOR.name());

            if (role.isPresent()) {

                admin.setRoles(Set.of(role.get()));

                userRepository.save(admin);
            }
            else {
                userRepository.save(admin);
            }
        }
    }
}
