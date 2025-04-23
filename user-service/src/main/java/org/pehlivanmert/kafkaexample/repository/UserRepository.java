package org.pehlivanmert.kafkaexample.repository;

import org.pehlivanmert.kafkaexample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
