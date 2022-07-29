package com.dakual;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    User getUserById(String id);
}
