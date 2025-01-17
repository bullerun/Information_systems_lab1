package com.example.information_systems_lab1.repository;

import com.example.information_systems_lab1.entity.Role;
import com.example.information_systems_lab1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("update User u set u.role = :role where u.id = :id")
    void updateRole(@Param("id") Long id, @Param("role") Role role);
//    @Query(nativeQuery = true, value ="BEGIN TRANSACTION;update users u set role=?2 where u.id=?1;" +
//            "delete from adminqueue a where a.owner_id= ?1;COMMIT TRANSACTION;")
//    void updateRole(@Param("id") Long id, @Param("role") String role);
}