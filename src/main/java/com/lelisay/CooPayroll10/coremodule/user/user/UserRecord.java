package com.lelisay.CooPayroll10.coremodule.user.user;



import com.lelisay.CooPayroll10.coremodule.user.role.Role;

import java.util.Set;

public record UserRecord(Long id, String firstName, String lastName, String email, Set<Role> roles){}