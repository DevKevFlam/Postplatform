package fr.kflamand.Backend.services;

import fr.kflamand.Backend.dao.RoleRepository;
import fr.kflamand.Backend.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleService {

    @Autowired
    private RoleRepository roleDao;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Role findByName (String name){
        return roleDao.findByName(name);
    }

}
