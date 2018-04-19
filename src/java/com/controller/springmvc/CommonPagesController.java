package com.controller.springmvc;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CommonPagesController {

	public CommonPagesController() {
	}

	@RequestMapping(value = "/pages/{project}/{module}/{menu}")
    public ModelAndView pages(@PathVariable String project, @PathVariable String module, @PathVariable String menu) throws Exception {
		return new ModelAndView(project + "/" + module + "/" + menu);
    }
	
	@RequestMapping(value = "/pages/{project}/{menu}")
    public ModelAndView pages(@PathVariable String project, @PathVariable String menu) throws Exception {
		return new ModelAndView(project+"/"+menu);
    }
}
