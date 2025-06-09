package org.example.trailverse.user.repository;

import org.example.trailverse.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select m.id from User m where m.userId = :userId")
    Long findByUserid(String userId);

    User findByUserId(String userId);

}
