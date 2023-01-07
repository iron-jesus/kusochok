package ua.pp.kusochok.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.pp.kusochok.errors.UsernameAlreadyExists;
import ua.pp.kusochok.models.Settings;
import ua.pp.kusochok.models.User;
import ua.pp.kusochok.models.enums.Role;
import ua.pp.kusochok.repositories.UserRepository;
import ua.pp.kusochok.rest.dto.SignInRequestDto;
import ua.pp.kusochok.rest.dto.SignInResponseDto;
import ua.pp.kusochok.rest.dto.SignUpRequestDto;
import ua.pp.kusochok.security.JwtTokenProvider;

@Component
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public SignInResponseDto authenticate(SignInRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.username).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));

        return new SignInResponseDto(
                user.getUsername(),
                jwtTokenProvider.createToken(requestDto.username, user.getRole().name()),
                user.getRole()
        );
    }

    public User register(SignUpRequestDto requestDto) throws UsernameAlreadyExists {
        try {
            String passwordHash = passwordEncoder.encode(requestDto.getPassword());

            User user = new User(requestDto.getUsername(), requestDto.getDisplayName(), passwordHash, Role.ADMIN, new Settings());

            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyExists(requestDto.getUsername());
        }
    }
}
