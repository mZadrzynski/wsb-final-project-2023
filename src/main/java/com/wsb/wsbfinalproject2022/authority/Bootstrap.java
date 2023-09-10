package com.wsb.wsbfinalproject2022.authority;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class Bootstrap implements InitializingBean {
    private final RoleRepository roleRepository;

    public Bootstrap (RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void afterPropertiesSet(){
        for (RoleType name : RoleType.values()) {
            Role existingRole = roleRepository.findByName(name);
            if (existingRole == null) {
                Role role = new Role(name);
                roleRepository.save(role);
            }
        }
    }

}
