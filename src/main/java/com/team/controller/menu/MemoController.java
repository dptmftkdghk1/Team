package com.team.controller.menu;

import com.team.domain.EmployeeDTO;
import com.team.domain.MemoDTO;
import com.team.service.menuservice.MemoServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Log4j2
@Controller
@RequestMapping("/menu")

public class MemoController {

    @Autowired
    private MemoServiceImpl memoServiceImpl;


    //    @GetMapping("/memo")
//    public String get_memo(
//            Model model,
//            // html의 query와 keyword에 넣은 값을 가져옴
//            @RequestParam(required = false) String query,
//            @RequestParam(required = false) String keyword,
//            @AuthenticationPrincipal EmployeeDTO employeeDTO
//    ) {
//        System.out.println(query + " " + keyword);
//        System.out.println(employeeDTO);
//        if(!Objects.isNull(employeeDTO)){
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String name = authentication.getName();
//            model.addAttribute("name", name);
//            List<MemoDTO> memos = memoService.getAllMemos(query,keyword);
//            System.out.println("memos" + memos);
//            model.addAttribute("memos", memos);
//            return "/menu/memo";
//        }
//        return "redirect:/auth/login"; // Thymeleaf 템플릿 경로와 파일명
//    }
    @GetMapping("/memo/{employeeId}")
    public String get_memo(
            Model model,
            // html의 query와 keyword에 넣은 값을 가져옴
            @PathVariable("employeeId") String employeeId,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String keyword,
            @AuthenticationPrincipal EmployeeDTO employeeDTO
    ) {
        model.addAttribute("employee", employeeDTO);
        System.out.println(query + " " + keyword);
        System.out.println(employeeDTO);
        if (!Objects.isNull(employeeDTO)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String name = authentication.getName();

            System.out.println("로그인한 네임임" + name);

            model.addAttribute("name", name);
            List<MemoDTO> memos = memoServiceImpl.getAllMemos(query, keyword);
            System.out.println("memos" + memos);
            model.addAttribute("memos", memos);
            List<MemoDTO> memoDTO = memoServiceImpl.selectMemoById(employeeId, query, keyword);
            model.addAttribute("memoDTO", memoDTO);
            return "/menu/memo";
        }
        return "redirect:/auth/login"; // Thymeleaf 템플릿 경로와 파일명
    }


//    @DeleteMapping("/memo/{memoNo}")
//    public String deleteMemo(@PathVariable Integer memoNo) {
//        memoService.deleteMemo(memoNo);
//        return "redirect:/memo";
//    }


    @PostMapping("/memo/{employeeId}")
    public String insertMemo(
            @PathVariable("employeeId") String employeeId, MemoDTO memoDTO) {
        memoServiceImpl.insertMemo(memoDTO);
        return "redirect:/menu/memo/{employeeId}";
    }


    @GetMapping("/{employeeId}/memo_detail/{memoNo}")
    public String selectMemoByNo(@PathVariable("employeeId") String employeeId,
                                 @PathVariable("memoNo") Integer memoNo,
                                 Model model) {
        MemoDTO memoDTO = memoServiceImpl.selectMemoByNo(memoNo); // MemoService에서 메모 조회 로직 구현 필요
        if (memoDTO == null) {
            // 메모가 존재하지 않는 경우 처리
            // 예를 들어, 메모가 없을 때 처리할 로직 추가
            return "redirect:/menu/memo/{employeeId}"; // 예시: 메모 목록 페이지로 리다이렉트
        }

        model.addAttribute("memoDTO", memoDTO);
        model.addAttribute("employeeId", employeeId); // employeeId도 모델에 추가하여 view에서 사용할 수 있도록 함
        return "/menu/memo_detail"; // memoDetail.html 또는 원하는 view 이름으로 설정
    }

    @DeleteMapping("/memo/{employeeId}/{memoNo}")
    @ResponseBody
    public ResponseEntity<Void> deleteMemo(@PathVariable("employeeId") String employeeId, @PathVariable("memoNo") Integer memoNo) {
        boolean isDeleted = memoServiceImpl.deleteMemoByNo(memoNo);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}

