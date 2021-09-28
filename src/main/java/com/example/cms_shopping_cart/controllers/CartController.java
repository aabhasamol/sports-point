package com.example.cms_shopping_cart.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.cms_shopping_cart.models.Cart;
import com.example.cms_shopping_cart.models.ProductRepository;
import com.example.cms_shopping_cart.models.data.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
@SuppressWarnings("unchecked")
public class CartController {
    
    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/add/{id}")
    public String add(@PathVariable int id, HttpSession session, Model model, @RequestParam(value="cartPage", required=false) String cartPage) 
    {

        Products product=productRepo.findById(id);

        if(session.getAttribute("cart")==null)
        {
            HashMap<Integer, Cart> cart=new HashMap<Integer, Cart>();
            cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
            session.setAttribute("cart", cart);
        }
        else
        {
            HashMap<Integer, Cart> cart=(HashMap<Integer, Cart>)session.getAttribute("cart");
            if(cart.containsKey(id))
            {
                int quantity=cart.get(id).getQuantity()+1;
                cart.put(id, new Cart(id, product.getName(), product.getPrice(), quantity, product.getImage()));
            }
            else
            {
                cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
                session.setAttribute("cart", cart);
            }
        }
        
        HashMap<Integer, Cart> cart=(HashMap<Integer, Cart>)session.getAttribute("cart");
        int size=0;
        double total=0;

        for(Cart value: cart.values())
        {
            size+=value.getQuantity();
            total+=Double.parseDouble(value.getPrice())*value.getQuantity();
        }

        model.addAttribute("size", size);
        model.addAttribute("total", total);

        if(cartPage!=null)
            return "redirect:/cart/view";
        
        return "cart_view";
        
    }

    @GetMapping("/subtract/{id}")
    public String subtract(@PathVariable int id, HttpSession session, Model model, HttpServletRequest httpServletRequest) {

        Products product = productRepo.findById(id);

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

        int quantity = cart.get(id).getQuantity() - 1;
        if (quantity == 0) {
            cart.remove(id);
            if (cart.size() == 0) {
                session.removeAttribute("cart");
            } 
        } else {
            cart.put(id, new Cart(id, product.getName(), product.getPrice(), quantity, product.getImage()));
        }

        String refererLink = httpServletRequest.getHeader("referer");

        return "redirect:"+ refererLink;
    }    
    
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable int id, HttpSession session, Model model, HttpServletRequest httpServletRequest) {

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

        cart.remove(id);
        if (cart.size() == 0) {
            session.removeAttribute("cart");
        } 

        String refererLink = httpServletRequest.getHeader("referer");

        return "redirect:" + refererLink;
    }

    @GetMapping("/clear")
    public String clear(HttpSession session, HttpServletRequest httpServletRequest) {

        session.removeAttribute("cart");

        String refererLink = httpServletRequest.getHeader("referer");

        return "redirect:" + refererLink;
    }

    @RequestMapping("/view")
    public String view(HttpSession session, Model model) {
        
        if(session.getAttribute("cart") == null)
        {
            return "redirect:/";
        }
        HashMap<Integer, Cart> cart=(HashMap<Integer, Cart>)session.getAttribute("cart");
        model.addAttribute("cart", cart);
        model.addAttribute("notCartViewPage", true);

        return "cart";
    }
}
