package isu.library.model.service;

import isu.library.model.entity.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MyUserDetailsService extends UserDetailsService {
    Person registerNewUserAccount(Person userDto) throws Exception;
}
