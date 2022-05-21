package com.webapplication.watchlist.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {

    public String getErrorPath(){
        return "/error";
    }

    @GetMapping("/error")
    public String handleError(HttpServletResponse response){
        int code = response.getStatus();
        System.out.println("Error with code" + code + "happened");
        return "error";
    }

}
