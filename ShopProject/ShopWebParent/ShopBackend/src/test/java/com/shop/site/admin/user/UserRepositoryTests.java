package com.shop.site.admin.user;

import com.shop.site.common.entity.Role;
import com.shop.site.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest(showSql= false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userMW = new User("weklar@gmail.com", "admin", "Marcin", "Weklar");
        userMW.addRole(roleAdmin);

        User savedUser = repo.save(userMW);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles() {
        User userAB = new User("aleksandra@gmail.com", "admina", "Aleksandra", "Belka");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        userAB.addRole(roleEditor);
        userAB.addRole(roleAssistant);

        User savedUser = repo.save(userAB);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach((user -> System.out.println(user)));
    }

    @Test
    public void testGetUserById() {
        User userMW = repo.findById(1).get();
        System.out.println(userMW);
        assertThat(userMW).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userMW = repo.findById(1).get();
        userMW.setEnabled(true);
        userMW.setEmail("updateweklar@gmail.com");
        repo.save(userMW);
    }

    @Test
    public void testUpdateUserRoles() {
        User userAB = repo.findById(1).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);
        userAB.getRoles().remove(roleEditor);
        userAB.addRole(roleSalesperson);
        repo.save(userAB);
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 3;
        repo.deleteById(userId);

    }
    @Test
    public void testGetUserByEmail() {
        String email="dsaads@gmail.com";
        User user= repo.getUserByEmail(email);
        assertThat(user).isNotNull();
    }
    @Test
    public void testCountById() {
       Integer id= 33;
       Long countById = repo.countById(id);
       assertThat(countById).isNotNull().isGreaterThan(0);
    }
    @Test
    public void testDisableUser() {
        Integer id= 31;
        repo.updateEnabledStatus(id, false);
    }
    @Test
    public void testEnableUser() {
        Integer id= 31;
        repo.updateEnabledStatus(id, true);
    }
    @Test
    public void testListFirstPage() {
        Integer pageNumber = 0;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<User> page = repo.findAll(pageable);

        List<User> listUsers=page.getContent();
        listUsers.forEach(user -> System.out.println(user));
        assertThat(listUsers.size()).isEqualTo(pageSize);

    }

    @Test
    public void testSearchUsers() {
        String keyword = "marcin";
        Integer pageNumber = 0;
        int pageSize = 3;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<User> page = repo.findAll(keyword, pageable);

        List<User> listUsers=page.getContent();
        listUsers.forEach(user -> System.out.println(user));
        assertThat(listUsers.size()).isGreaterThan(0);

    }

}
