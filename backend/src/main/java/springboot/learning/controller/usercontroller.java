package springboot.learning.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.learning.Securityconfig;
import springboot.learning.repository.userrepository;
import springboot.learning.service.userservice;
import springboot.learning.models.userdata;
import springboot.learning.utility.jwtutil;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class usercontroller {

    @Autowired
    private final userservice userservice;

    @Autowired
    private final userrepository userrepository;

    private final jwtutil jwtutil;

    private final Securityconfig securityconfig;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody Map<String,String> body){

        String email = body.get("email");
        String password = passwordEncoder.encode(body.get("password"));

        if (userrepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("aldready user registered", HttpStatus.CONFLICT);
        }



        userservice.createuser(userdata.builder()
                        .email(email)
                        .password(password)
                .build());
        return new ResponseEntity<>("user registered", HttpStatus.CREATED);

    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody Map<String,String> body){

        String email = body.get("email");
        String password = body.get("password");

        var userdetails = userrepository.findByEmail(email);

        if (userdetails.isEmpty()){
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }


        userdata userdata = userdetails.get();

        if (!passwordEncoder.matches(password,userdata.getPassword())){
            return new ResponseEntity<>("password is wrong", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtutil.generatetoken(email);
        return ResponseEntity.ok(Map.of("token",token));
    }

}
