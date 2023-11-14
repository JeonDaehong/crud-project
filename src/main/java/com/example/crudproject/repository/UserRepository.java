package com.example.crudproject.repository;

import com.example.crudproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 회원가입
     */
    @Modifying
    @Query( value = "INSERT INTO TB_USER (LOGIN_ID, USER_NM, PWSD, CRTE_DTTM, UPDT_DTTM)" +
                    "VALUES (:loginId, :userName, :password, :createDateTime, :updateDatetime)",
            nativeQuery = true)
    void joinUser(@Param(value = "loginId") String loginId,
                  @Param(value = "userName") String userName,
                  @Param(value = "password") String password,
                  @Param(value = "createDateTime") LocalDateTime createDateTime,
                  @Param(value = "updateDatetime") LocalDateTime updateDatetime);


    /**
     * 중복 아이디 확인
     */
    @Query ( value = "SELECT * FROM TB_USER WHERE LOGIN_ID = :loginId", nativeQuery = true )
    User overlabCheck(@Param(value = "loginId") String loginId);


    /**
     * 내 정보 가져오기
     */
    @Query ( value = "SELECT * FROM TB_USER WHERE USER_ID = :userId", nativeQuery = true )
    User getMyInfo(@Param(value = "userId") Long userId);


    /**
     * 회원 정보 수정
     */
    @Transactional
    @Modifying
    @Query( value = "UPDATE TB_USER SET USER_NM = :userName, UPDT_DTTM = :updateDatetime WHERE USER_ID = :userId", nativeQuery = true)
    void updateUser(@Param(value = "userId") Long userId,
                    @Param(value = "userName") String userName,
                    @Param(value = "updateDatetime") LocalDateTime updateDatetime);

    /**
     * 회원 탈퇴
     */
    @Modifying
    @Query( value = "DELETE FROM TB_USER WHERE USER_ID = :userId", nativeQuery = true)
    void deleteUser(@Param(value = "userId") Long userId);

}
