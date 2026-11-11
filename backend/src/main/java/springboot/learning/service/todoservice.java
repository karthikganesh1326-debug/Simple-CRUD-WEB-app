
package springboot.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import springboot.learning.models.tododata;
import springboot.learning.models.userdata;
import springboot.learning.repository.todorepository;
import springboot.learning.repository.userrepository;

import java.util.List;

@Service
public class todoservice {

    @Autowired
    private todorepository todorepository;

    @Autowired
    private userrepository userrepository;

    // Get logged-in user's email from JWT authentication
    private String getCurrentUserEmail() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }

    // Create Todo
    public tododata createtodo(tododata data) {

        String email = getCurrentUserEmail();

        userdata user = userrepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        data.setUser(user);

        return todorepository.save(data);
    }

    // Get Todo by ID, only if it belongs to logged-in user
    public tododata gettodobyid(Long id) {

        String email = getCurrentUserEmail();

        return todorepository.findByIdAndUser_Email(id, email)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }

    // Get all Todos belonging to logged-in user
    public List<tododata> getalltodo() {

        String email = getCurrentUserEmail();

        return todorepository.findAllByUser_Email(email);
    }

    // Update Todo belonging to logged-in user
    public tododata updatetodo(tododata data) {

        String email = getCurrentUserEmail();

        tododata existingTodo = todorepository
                .findByIdAndUser_Email(data.getId(), email)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        existingTodo.setTitle(data.getTitle());
        existingTodo.setIscompleted(data.getIscompleted());

        return todorepository.save(existingTodo);
    }

    // Delete Todo belonging to logged-in user
    public void deletebyid(Long id) {

        String email =
                getCurrentUserEmail();

        tododata existingTodo = todorepository
                .findByIdAndUser_Email(id, email)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todorepository.delete(existingTodo);
    }


}
