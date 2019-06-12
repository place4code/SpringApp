package com.place4code.reddit.repo;

import com.place4code.reddit.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepo extends JpaRepository<Link,Long> {

    List<Link> findAllByUserId(Long id);

}
