package com.example.pcenter.repository;

import com.example.pcenter.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = """
            SELECT exists(
                SELECT 1
                FROM users_appointments
                WHERE user_id = :userId
                AND appointment_id = :appointmentId)""", nativeQuery = true)
    boolean isAppointmentOwner(@Param("userId") Long userId,@Param("appointmentId") Long appointmentId);
}
