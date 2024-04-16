package com.moneytransactions.core.user;

import com.moneytransactions.core.exception.RequestException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private UserRepository userRepository;

  public UserService(UserRepository repository) {
    this.userRepository = repository;
  }

  public User create(UserCreationRequest request) {
    boolean emailAlreadyInUse = this.userRepository.findByEmail(request.email).isPresent();
    if (emailAlreadyInUse) throw new RequestException("email already in use");

    boolean documentAlreadyInUse = this.userRepository.findByDocument(request.document).isPresent();
    if (documentAlreadyInUse) throw new RequestException("document already in use");

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
