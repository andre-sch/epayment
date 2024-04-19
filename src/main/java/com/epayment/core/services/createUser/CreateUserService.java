package com.epayment.core.services.createUser;

import com.epayment.core.entities.User;
import com.epayment.core.repositories.UserRepository;
import com.epayment.core.exceptions.DuplicateUserException;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CreateUserService {
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  public CreateUserService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User execute(UserCreationRequest request) {
    boolean emailAlreadyInUse = this.userRepository.findByEmail(request.email).isPresent();
    if (emailAlreadyInUse) throw new DuplicateUserException("email already in use");

    boolean documentAlreadyInUse = this.userRepository.findByDocument(request.document).isPresent();
    if (documentAlreadyInUse) throw new DuplicateUserException("document already in use");

    var user = new User();

    user.setDocument(request.document);
    user.setFullName(request.fullName);
    user.setEmail(request.email);
    
    String passwordHash = this.passwordEncoder.encode(request.password);
    user.setPassword(passwordHash);

    this.userRepository.save(user);

    return user;
  }
}
