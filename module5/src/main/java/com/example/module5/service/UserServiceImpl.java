package com.example.module5.service;

import com.example.module5.DTO.LoginDTO;
import com.example.module5.DTO.SignUpDTO;
import com.example.module5.DTO.UserDTO;
import com.example.module5.entities.User;
import com.example.module5.exceptions.ResourceNotFoundException;
import com.example.module5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new BadCredentialsException("User is not found with the name " + username));
    }

    public User getUserById(Long userId){
        return userRepository
                .findById(userId)
                .orElseThrow(()->
                        new ResourceNotFoundException("\"User is not found with the id \" + userId")
                );
    }

    public UserDTO signUP(SignUpDTO signUpDTO) {
        Optional<User> user = userRepository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("user with this email " + signUpDTO.getEmail() + " already exists");
        }
        User toBeCreate = modelMapper.map(signUpDTO, User.class);

        System.out.println("DTO Password = " + signUpDTO.getPassword());
        System.out.println("Mapped Password = " + toBeCreate.getPassword());

        toBeCreate.setPassword(passwordEncoder.encode(toBeCreate.getPassword()));

        System.out.println("Encoded Password = " + toBeCreate.getPassword());
        User toSave = userRepository.save(toBeCreate);
        return modelMapper.map(toSave, UserDTO.class);
    }
}
