package fr.kflamand.PostPlatform.Configurations;

import fr.kflamand.PostPlatform.persistance.Dao.PrivilegeDao;
import fr.kflamand.PostPlatform.persistance.Dao.RoleDao;
import fr.kflamand.PostPlatform.persistance.Dao.UserDao;
import fr.kflamand.PostPlatform.persistance.models.Privilege;
import fr.kflamand.PostPlatform.persistance.models.RoleUser;
import fr.kflamand.PostPlatform.persistance.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, passwordPrivilege));
        final RoleUser adminRole = createRoleIfNotFound("ADMIN", adminPrivileges);
        final RoleUser userRole = createRoleIfNotFound("USER", userPrivileges);
        createRoleIfNotFound("USER", userPrivileges);

        // == create initial user
        // TODO Génération des User a la compil
        createUserIfNotFound("test@admin.com", "testAdmin","pseutestAd", adminRole);
        createUserIfNotFound("test@user.com", "testUser","pseutestUs", userRole);

        alreadySetup = true;
    }

    @Transactional
    private final Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeDao.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeDao.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private final RoleUser createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        RoleUser role = roleDao.findByName(name);
        if (role == null) {
            role = new RoleUser(name);
        }
        role.setPrivileges(privileges);
        role = roleDao.save(role);
        return role;
    }

    @Transactional
    private final User createUserIfNotFound(final String email, final String password, final String pseudo, final RoleUser role) {
        User user = userDao.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setPseudo(pseudo);
            user.setEnabled(true);
        }
        user.setRoleUser(role);
        user = userDao.save(user);
        return user;
    }

}