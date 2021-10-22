package isu.library.model.service.user;


import isu.library.model.entity.Person;
import isu.library.model.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("MyUserDetailsService")
public class UserDetailsServiceImpl implements MyUserDetailsService {

    @Autowired
    private PersonRepository usersRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Person> optionalUser = usersRepository.findPersonByUsername(userName);
        if(optionalUser.isPresent()) {
            return User.builder()
                    .username(optionalUser.get().getUsername())
                    //change here to store encoded password in db
                    .password(optionalUser.get().getPassword())
                    .roles(optionalUser.get().getRole())
                    .build();
        } else {
            throw new UsernameNotFoundException("User Name is not Found");
        }
    }

    @Override
    public Person registerNewUserAccount(Person userDto) throws Exception {
        try {
            loadUserByUsername(userDto.getUsername());
            throw new Exception("User already exists");
        } catch (UsernameNotFoundException e) {

        }

        return usersRepository.save(userDto);
    }
}
