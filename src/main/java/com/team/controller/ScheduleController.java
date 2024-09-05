package com.team.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @GetMapping("/register_schedule")
    public void get_schedule(){}

    @PostMapping("/register_schedule")
    public String post_schedule(){
        return "redirect:/schedule";
    }
}
