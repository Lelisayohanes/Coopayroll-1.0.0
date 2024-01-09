package com.lelisay.CooPayroll10.coremodule.user.role;

import com.lelisay.CooPayroll10.coremodule.user.user.User;


import java.util.List;

public interface IRoleService {

    List<Role> getAllRoles();

    Role createRole(Role theRole);

    void deleteRole(Long roleId);

    Role findRoleByName(String name);

    Role findRoleById(Long roleId);

    User removeUserFromRole(Long userId, Long roleId);

    User assignUserToRole(Long userId, Long roleId);

    Role removeAllUserFromRole(Long roleId);



}
