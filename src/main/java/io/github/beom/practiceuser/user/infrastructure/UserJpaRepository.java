package io.github.beom.practiceuser.user.infrastructure;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface UserJpaRepository extends JpaRepository<UserJpaEntity, String> {

    @EntityGraph(attributePaths = {"roleSet"})
    @Query("select u from UserJpaEntity u where u.id = :id and u.social = false")
    Optional<UserJpaEntity> getWithRoles(String id);

    @EntityGraph(attributePaths = {"roleSet"})
    Optional<UserJpaEntity> findByEmail(String email);


    @Modifying
    @Transactional
    @Query("update UserJpaEntity u set u.pw = :pw where u.id = :id")
    void updatePassword(@Param("pw") String password, @Param("id") String id);
}
