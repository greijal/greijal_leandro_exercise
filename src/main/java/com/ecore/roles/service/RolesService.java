package com.ecore.roles.service;

import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class RolesService {

    public static final String DEFAULT_ROLE = "Developer";

    private final RoleRepository roleRepository;
    private final MembershipRepository membershipRepository;
    private final MembershipsService membershipsService;

    @Autowired
    public RolesService(
            RoleRepository roleRepository,
            MembershipRepository membershipRepository,
            MembershipsService membershipsService) {
        this.roleRepository = roleRepository;
        this.membershipRepository = membershipRepository;
        this.membershipsService = membershipsService;
    }

    public Role createRole(@NonNull Role role) {
        if (roleRepository.findByName(role.getName()).isPresent()) {
            throw new ResourceExistsException(Role.class);
        }
        return roleRepository.save(role);
    }

    public Role getRole(@NonNull UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class, id));
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }


}
