package com.place4code.reddit.repo;

import com.place4code.reddit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
