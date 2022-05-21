package com.webapplication.watchlist.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {

    public String getErrorPath(){
        return "/error";
    }

    @GetMapping("/error")
    public String handleError(HttpServletResponse response, Model model){
        int code = response.getStatus();
        model.addAttribute("code", code);
        System.out.println("Error with code " + code + " happened");
        return "error";
    }

}
