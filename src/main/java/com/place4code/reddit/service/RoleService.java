package com.place4code.reddit.service;

import com.place4code.reddit.model.Role;
import com.place4code.reddit.repo.RoleRepo;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role findByName(String name) {
        return roleRepo.findByName(name);
    }
}
