package com.lelisay.CooPayroll10.coremodule.user.role.data;

import com.lelisay.CooPayroll10.coremodule.user.role.RoleRepository;
import com.lelisay.CooPayroll10.coremodule.user.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data exists
        if (roleRepository.count() == 0) {
            // Data doesn't exist, initialize it
            Role defaultRole = new Role();
            defaultRole.setName("COMPANY");
            // Save to the database
            roleRepository.save(defaultRole);
            // You can add more data initialization as needed

        }
    }
}
