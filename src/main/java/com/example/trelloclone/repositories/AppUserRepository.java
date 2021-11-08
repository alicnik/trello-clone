package com.example.trelloclone.repositories;

import com.example.trelloclone.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

    AppUser findByUsername(String username);

    boolean existsByUsernameAndPassword(String username, String password);
}
