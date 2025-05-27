package com.purely.services;

import com.purely.exceptions.UserNotFoundException;
import com.purely.modals.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String Email);

    User findByEmail(String email) throws UserNotFoundException;


}
