package com.team.controller;

import com.team.domain.ProductDTO;
import com.team.service.productservice.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
@Log4j2
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/manage_product")
    public String get_manage_product(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String rental,
            Model model
    ) {
        List<ProductDTO> products = productService.get_products(query, rental);
        model.addAttribute("products", products);
        return "/product/manage_product";
    }

    @GetMapping("/register_product")
    public void get_register_product() {
    }

    @PostMapping("/register_product")
    public String post_register_product(ProductDTO product){
        productService.insert_product(product);
        log.info(product);
        return "redirect:/product/manage_product";
    }

    @GetMapping("/{productNo}")
    public String get_product(
            @PathVariable("productNo") Integer productNo,
            Model model
    ){
        ProductDTO product = productService.select_product_by_no(productNo);
        model.addAttribute("product", product);
        return "product/read_product";
    }

    @PostMapping("/update/{productNo}")
    public String post_update_product(
            @PathVariable("productNo") Integer productNo,
            ProductDTO product
    ){
        product.setProductNo(productNo);
        productService.update_product(product);
        return "redirect:/product/manage_product";
    }

    @ResponseBody
    @DeleteMapping("/manage_product")
    public void delete_product(
            @RequestBody List<ProductDTO> products
    ){
        for(ProductDTO product : products){
            productService.delete_product(product.getProductNo());
        }
    }


//    주소에 /1 , /2 와 같이 여러 개 있을 경우 사용
//    @GetMapping("/{product_no}")
//    public void product_no(){
//
//    }
}
