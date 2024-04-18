package com.epayment.core.user;

import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
  private UserRepository userRepository;

  public CreateUserService(UserRepository repository) {
    this.userRepository = repository;
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
    
    String passwordHash = Integer.toString(request.password.hashCode()); // stub
    user.setPassword(passwordHash);

    this.userRepository.save(user);

    return user;
  }
}
