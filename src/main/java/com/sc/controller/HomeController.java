package com.sc.controller;

import com.sc.entity.Emp;
import com.sc.entity.User;
import com.sc.service.EmpService;
import com.sc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private EmpService empService;
    @Autowired
    private  UserService userService;
    @RequestMapping(path = "/home")
    public String home(Model m)
    {
        List<Emp> elist = empService.getAllEmp();
        m.addAttribute("emplist",elist);
        return "home";
    }
/*
    private static String authorizationRequestBaseUri
            = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls
            = new HashMap<>();
*/

  /*  @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
*/
  /*  @GetMapping("/oauth_login")
    public String getLoginPage(Model model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "oauth_login";
    }*/
    @RequestMapping(path = "/addEmp")
    public String addEmp()
    {
        return "addEmp";
    }
    @RequestMapping(path = "/createEmp",method = RequestMethod.POST)
    public String createEmp(@ModelAttribute Emp emp,HttpSession httpSession)
    {
        System.out.println(emp);
        int i = empService.saveEmp(emp);
        httpSession.setAttribute("msg","Registered Successfully");
        return "redirect:/addEmp";
    }
    @RequestMapping(path = "/editEmp/{id}")
    public String editEmp(@PathVariable int id, Model m) {

        Emp emp = empService.getEmpById(id);
        m.addAttribute("emp", emp);
        return "editEmp";
    }

    @RequestMapping(path = "/updateEmp", method = RequestMethod.POST)
    public String updateEmp(@ModelAttribute Emp emp, HttpSession session) {

        empService.update(emp);
        session.setAttribute("msg", "Updated Sucessfully");
        return "redirect:/home";
    }
    @RequestMapping(path="/deleteEmp/{id}")
    public String deleteEmp(@PathVariable int id,HttpSession session)
    {
        empService.deleteEmp(id);
        session.setAttribute("msg","Deleted Successfully");
        return "redirect:/home";
    }

    /*@RequestMapping(path = "/register")
    public String registerPage()
    {
        return "register";
    }
    @RequestMapping(path = "/login")
    public String loginPage()
    {
        return "login";
    }*/
    @RequestMapping(path = "/createUser",method = RequestMethod.POST)
    public String register(@ModelAttribute User user,HttpSession session){
        System.out.println(user);
        userService.saveUser(user);
        session.setAttribute("msg","User registered Successfully");
        return "redirect:/register";
    }
/*    @RequestMapping(path = "/userLogin",method = RequestMethod.POST)
    public String login(@RequestParam("email") String email,@RequestParam("password")String pwd,HttpSession session)
    {
        User user = userService.loginUser(email,pwd);
        if(user!=null)
        {
            session.setAttribute("loginuser",user);
            return "profile";
        }
        else {
            session.setAttribute("msg","Invalid Email or Password");
        }
        return "redirect:/login";
    }*/
    @RequestMapping(path = "/myProfile")
    public String myProfile()
    {
        return "profile";
    }
    @RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request,HttpSession session)
    {
        HttpSession sessio = request.getSession();
        sessio.removeAttribute("loginuser");
        session.setAttribute("msg","Logout Successfull");
        return "login";
    }
}
