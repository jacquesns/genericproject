import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.websystique.springboot.model.User;
import com.websystique.springboot.repositories.UserRepository;
import com.websystique.springboot.service.UserService;
import com.websystique.springboot.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
 
    //Mock
//    @TestConfiguration
//    static class EmployeeServiceImplTestContextConfiguration {
//  
//        @Bean
//        public UserService employeeService() {
//            return new UserServiceImpl();
//        }
//    }
//    
    
    
//    @Before
//    public void setUp() {
//        User user = new User();
//        user.setName("Mark");
//     
//        Mockito.when(userRepository.findByName(user.getName()))
//          .thenReturn(user);
//    }
    
    
    @Test
    public void whenFindByName_thenReturnUser() {
        // given
        User user = new User();
        user.setName("Mark5");
        entityManager.persist(user);
        entityManager.flush();
        User found = userRepository.findByName(user.getName());
        assertThat(found.getName())
          .isEqualTo(user.getName());
        
        
        
//        String name = "Mark";
//        User found = userService.findByName(name);
//      
//         assertThat(found.getName())
//          .isEqualTo(name);
    }
 
}
