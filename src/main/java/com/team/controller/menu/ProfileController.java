package com.team.controller.menu;

import com.team.domain.EmployeeDTO;
import com.team.service.auth.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/menu")
@Log4j2
public class ProfileController {

    @Autowired
    private AuthService authService;

    // 아이디로 유저 데이터 보여주기
    @GetMapping("/profile/{employeeId}")
    public String get_profile(@PathVariable("employeeId") String employeeId, Model model){

        EmployeeDTO employeeDTO = authService.selectAll();
        log.info("임플로이 "  + employeeDTO);
        model.addAttribute("employee", employeeDTO);
        return "/menu/profile";
    }

//    @PostMapping("/profile/{employeeId}")
//    public String updateProfile(@ModelAttribute EmployeeDTO employeeDTO,
//                                RedirectAttributes redirectAttributes) {
//        try {
//            profileService.updateProfile(employeeDTO);
//            redirectAttributes.addFlashAttribute("message", "프로필이 성공적으로 업데이트되었습니다.");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("message", "프로필 업데이트에 실패했습니다.");
//        }
//        return "redirect:/menu/profile/${employeeId}";
//    }

}
