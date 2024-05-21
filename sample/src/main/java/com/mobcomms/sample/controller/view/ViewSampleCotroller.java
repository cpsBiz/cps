package com.mobcomms.sample.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewSampleCotroller {

    @GetMapping("/index")
    public String greeting(@RequestParam(name="val", required=false, defaultValue="Sample") String val, Model model) {
        model.addAttribute("val", val);
        return "index";
    }
}
