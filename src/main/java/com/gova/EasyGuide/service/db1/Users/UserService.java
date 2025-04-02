package com.gova.EasyGuide.service.db1.Users;

import com.gova.EasyGuide.entities.bd1.Mentors;
import com.gova.EasyGuide.entities.bd1.User;
import com.gova.EasyGuide.entities.bd1.UserRegistartionDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User regiseterUser(UserRegistartionDto dto);

    public void  updatingUsersDeatails(User user);

    public boolean validateUser(String mail,String password);


}
