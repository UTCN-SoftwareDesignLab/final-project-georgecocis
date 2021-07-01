package com.example.demo.user.services;

import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import com.example.demo.user.model.dto.UserListDTO;
import com.example.demo.user.model.dto.UserMinimalDTO;
import com.example.demo.user.repos.RoleRepository;
import com.example.demo.user.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public UserListDTO create(UserListDTO userListDTO){
        Role defaultRole = roleRepository.findByRole(ERole.EMPLOYEE).orElseThrow(() -> new RuntimeException("Can't find role: EMPLOYEE"));
        User user = userMapper.userFromUserListDto(userListDTO);
        user.setRoles(Set.of(defaultRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.userListDtoFromUser(userRepository.save(user));
    }

    public UserListDTO updateUser(Long id, UserListDTO userListDTO){
        User user = findById(id);
        user.setUsername(userListDTO.getName());
        user.setEmail(userListDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userListDTO.getPassword()));
        return userMapper.userListDtoFromUser(userRepository.save(user));
    }

    public void deleteUser(Long id){
        userRepository.delete(findById(id));
    }

    public UserListDTO get(Long id){
        return userMapper.userListDtoFromUser(findById(id));
    }
}
