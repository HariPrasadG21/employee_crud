package com.sc.controller;

import com.sc.dao.UsersDao;
import com.sc.entity.*;
import com.sc.security.JwtTokenHelper;
import com.sc.service.EmpService;
import com.sc.service.UserDetailsServiceimpl;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController
{
    @Autowired
    private EmpService empService;
    @Autowired
    private UserDetailsServiceimpl userDetailsServiceimpl;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
     @Autowired
     @Qualifier("authenticationManager")
     AuthenticationManager authenticationManager;
    @Autowired
    private UsersDao usersDao;
    @RequestMapping(path = "/home")
    public String home(Model m)
    {
        List<Emp> elist = empService.getAllEmp();
        m.addAttribute("emplist",elist);
        return "home";
    }
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

    @RequestMapping(path = "/register")
    public String registerPage()
    {
        return "register";
    }

    @GetMapping(path = "/login")
    public String loginPage()
    {
        return "login";
    }

    @RequestMapping(path = "/userLogin",method = RequestMethod.POST)
    public String loginPage(@RequestParam String username, @RequestParam String password, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response)
    {      //System.out.println("tokennnn "+ username+ "password "+password);
        this.authenticate(username,password);
        //System.out.println("tokennnn ");
        UserDetails userDetails= userDetailsServiceimpl.loadUserByUsername(username);
        //System.out.println("userDetails "+ userDetails);
        String token=jwtTokenHelper.generateToken(userDetails.getUsername());
        // System.out.println("tokennnn "+ token);
        //session.setAttribute("token", token);
       // System.out.println("tokennnn "+ session.getAttribute("token"));
        // Set the token in the model
        //model.addAttribute("token",token);
        Cookie cookie = new Cookie("auth_token", token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        // Create an Authentication object
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Set the Authentication in the SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Redirect to the homepage
        return "redirect:/home";
    }
   public void authenticate(String username, String password) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("Authenticated successfully");
        }
        catch (AuthenticationException e) {
            System.out.println("Authentication failed");
            // Clear the authentication information in the SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(null);
            throw e;
        }
    }


    @RequestMapping(path = "/createUser",method = RequestMethod.POST)
    public String register(@RequestParam String username,@RequestParam String password, HttpSession session){
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);
        user.setAuthority("ROLE_ADMIN");
        int i = usersDao.saveUser(user);
        session.setAttribute("msg","User registered Successfully");
        return "redirect:/register";
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
