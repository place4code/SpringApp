package com.place4code.reddit.repo;

import com.place4code.reddit.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepo extends JpaRepository<Link,Long> {
}
