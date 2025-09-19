package com.dunnas.reservasalas.services;

import com.dunnas.reservasalas.enums.UserRole;
import com.dunnas.reservasalas.model.Role;
import com.dunnas.reservasalas.model.Sector;
import com.dunnas.reservasalas.model.User;
import com.dunnas.reservasalas.repository.RoleRepository;
import com.dunnas.reservasalas.repository.SectorRepository;
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
    private final SectorRepository sectorRepository;

    public User getById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void createNewUser(User user, String userRole, Long sectorId, Authentication authentication) {

        Sector userSector = null;

        //TODO: Substituir por validação usando SQL
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("Usuário com mesmo nome já existente");
        }

        boolean isCurrentUserAdmin = false;
        if(authentication!=null) {
            isCurrentUserAdmin = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ROLE_" + UserRole.ADMINISTRADOR));
        }

        if(isCurrentUserAdmin) {
            Optional<Role> roleOptional = roleRepository.findByName(userRole);
            if (roleOptional.isPresent()) {
                user.setRoles(Set.of(roleOptional.get()));
                if(roleOptional.get().getName().equals(UserRole.RECEPCIONISTA.name()) &&
                   sectorId!=null){
                    Optional<Sector> sectorOptional = sectorRepository.findById(sectorId);
                    if(sectorOptional.isPresent()) {
                        userSector = sectorOptional.get();
                    }
                }
            }

        } else {
            Optional<Role> role = roleRepository.findByName(UserRole.CLIENTE.name());
            if(role.isPresent()) {
                user.setRoles(Set.of(role.get()));
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);

        if(userSector!=null){
            userSector.setUser(newUser);
            sectorRepository.save(userSector);
        }

        System.out.println("Usuario criado com sucesso");
    }

    @Transactional
    public void update(User user){
        Optional<User> userOptional = userRepository.findById(user.getId());
        if(userOptional.isPresent()) {

            userOptional.get().setUsername(user.getUsername());

            userOptional.get().setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(userOptional.get());
        }
    }

    public void delete(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            userRepository.delete(userOptional.get());
        }
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
