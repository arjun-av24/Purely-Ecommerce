package com.purely.services;

import com.purely.enums.ERole;
import com.purely.modals.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role findByName(ERole eRole);
}