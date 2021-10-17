package isu.library.model.service;

import isu.library.model.entity.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Part of Warehouse Visualizer.
 *
 * @author Tomáš  Korbař
 * @author Ondřej Babec
 * @date 17$ 9:01 AM$
 */
public interface MyUserDetailsService extends UserDetailsService {
    Person registerNewUserAccount(Person userDto) throws Exception;
}
