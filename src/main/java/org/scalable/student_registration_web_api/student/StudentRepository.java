package org.scalable.student_registration_web_api.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Transactional
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END " +
            "FROM org.scalable.student_registration_web_api.student.Student s " +
            "WHERE s.email = ?1")
    public Boolean selectExistsEmail(String email);
}
