package io.proj3ct.telegramjokebot.service.repos;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import io.proj3ct.telegramjokebot.model.UserRole;

import java.util.Optional;

public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
    Optional<UserRole> findByRole(String role);
}