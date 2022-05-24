package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest){

        if (Integer.parseInt(httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString())
                == HttpStatus.NOT_FOUND.value()){
            return "error_404";
        }
        return "error";
    }
}
