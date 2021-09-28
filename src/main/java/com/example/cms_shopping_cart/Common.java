package com.example.cms_shopping_cart;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.cms_shopping_cart.models.Cart;
import com.example.cms_shopping_cart.models.CategoryRepository;
import com.example.cms_shopping_cart.models.PageRepository;
import com.example.cms_shopping_cart.models.data.Category;
import com.example.cms_shopping_cart.models.data.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@SuppressWarnings("unchecked")
public class Common {

    @Autowired
    private PageRepository pageRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @ModelAttribute
    public void sharedData(Model model, HttpSession session, Principal principal) {

        if (principal != null) {
            model.addAttribute("principal", principal.getName());
        }

        List<page> pages = pageRepo.findAllByOrderBySortingAsc();
        List<Category> categories = categoryRepo.findAllByOrderBySortingAsc();

        boolean cartActive=false;
        
        if(session.getAttribute("cart")!=null)
        {
            cartActive=true;
            HashMap<Integer, Cart> cart=(HashMap<Integer, Cart>)session.getAttribute("cart");
            int size=0;
            double total=0;

            for(Cart value: cart.values())
            {
                size+=value.getQuantity();
                total+=Double.parseDouble(value.getPrice())*value.getQuantity();
            }

            model.addAttribute("csize", size);
            model.addAttribute("ctotal", total);
        }
        model.addAttribute("cpages", pages);
        model.addAttribute("cartActive", cartActive);
        model.addAttribute("ccategories", categories);


    }
    
}
