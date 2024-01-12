package com.lelisay.CooPayroll10.coremodule.user.role;

import com.lelisay.CooPayroll10.coremodule.user.user.IUserRepository;
import com.lelisay.CooPayroll10.coremodule.user.user.User;
import com.lelisay.CooPayroll10.generalmodule.exception.RoleAlreadyExistException;
import com.lelisay.CooPayroll10.generalmodule.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;
    private final IUserRepository userRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        Optional<Role> checkRole =roleRepository.findByName(theRole.getName());

        if(checkRole.isPresent()){
            throw new RoleAlreadyExistException(checkRole.get() +  "role already exist");
        }

        return roleRepository.save(theRole);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUserFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public Role findRoleById(Long roleId) {
        return roleRepository.findById(roleId).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if(role.isPresent() && role.get().getUsers().contains(user.get())){
            role.get().removeAllUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("user not found !");
    }

    @Override
    public User assignUserToRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(user.get().getFirstName() +
                    "already is already assigned to the " +
                    role.get().getName() + "role" );
        }

        role.ifPresent(theRole -> theRole.assignUserToRole(user.get()));
        roleRepository.save(role.get());
        return user.get();
    }

    @Override
    public Role removeAllUserFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.ifPresent(Role::removeAllUsersFromRole);
        return roleRepository.save(role.get());
    }

}
