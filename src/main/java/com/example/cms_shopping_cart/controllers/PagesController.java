package com.example.cms_shopping_cart.controllers;

import com.example.cms_shopping_cart.models.PageRepository;
import com.example.cms_shopping_cart.models.data.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PagesController {

    @Autowired
    private PageRepository pageRepository;
    
    @GetMapping
    public String home(Model model)
    {
        page page= pageRepository.findBySlug("home");
        model.addAttribute("page", page);
        return "page";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/{slug}")
    public String page(@PathVariable String slug, Model model)
    {
        page page= pageRepository.findBySlug(slug);
        if(page==null)
            return "redirect:/";
        model.addAttribute("page", page);
        return "page";
    }
}
