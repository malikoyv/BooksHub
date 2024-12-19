package com.malikoyv.core.repositories;

import com.malikoyv.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
}
