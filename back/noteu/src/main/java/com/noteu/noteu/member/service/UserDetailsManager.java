package com.noteu.noteu.member.service;

import com.noteu.noteu.member.dto.MemberEditDto;
import com.noteu.noteu.member.dto.MemberPasswordDto;
import com.noteu.noteu.member.dto.SignUpDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetailsManager extends UserDetailsService {

    /**
     * Create a new user with the supplied details.
     */
    void createUser(SignUpDto user);

    /**
     * Update the specified user.
     */
    void updateUser(MemberEditDto user);

    /**
     * Remove the user with the given login name from the system.
     */
    void deleteUser(Long memberId, String username);

    /**
     * Modify the current user's password. This should change the user's password in the
     * persistent user repository (database, LDAP etc).
     * @param oldPassword current password (for re-authentication if required)
     * @param newPassword the password to change to
     */
    void changePassword(MemberPasswordDto memberPasswordDto);

    /**
     * Check if a user with the supplied login name exists in the system.
     */
    boolean userExists(String username);

}
