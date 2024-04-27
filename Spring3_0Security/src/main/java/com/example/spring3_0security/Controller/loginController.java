package com.example.spring3_0security.Controller;


import com.example.spring3_0security.Service.UserService;
import com.example.spring3_0security.dto.AddUserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class loginController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        System.out.println("00");
        return "/login/loginMain";
    }
    @GetMapping("/insert")
    public String insert(){
        System.out.println("30");
        return "/login/insertMain";
    }
    @PostMapping("/insert")
    public String insertLogin(AddUserRequest dto){
        System.out.println("31");
        userService.save(dto);
        return "/login/loginMain";
    }

    @GetMapping("loginSuccess")
    public String success(){
        System.out.println("10");
        return "login/loginSuccess";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        System.out.println("20");
        new SecurityContextLogoutHandler().logout(request, response,
        SecurityContextHolder.getContext().getAuthentication());
        return "/login/logoutMain";
    }
}
