package springboot.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.learning.models.userdata;
import springboot.learning.repository.userrepository;

import java.util.List;

@Service
public class userservice {

    @Autowired
    private userrepository userrepository;


    public userdata createuser(userdata data){
        return userrepository.save(data);
    }

    public userdata getuserbyid(Long id){
        return userrepository.findById(id)
                .orElseThrow(()-> new RuntimeException("not found"));
    }


}
