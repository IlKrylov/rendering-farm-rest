package com.krylov.renderfarm.repository;

import com.krylov.renderfarm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
