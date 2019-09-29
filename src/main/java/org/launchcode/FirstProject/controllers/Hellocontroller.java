package org.launchcode.FirstProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class Hellocontroller {
    static int count = 0;
    static HashMap <String , String> languages = new HashMap<String,String>();

    @RequestMapping(value="")
    @ResponseBody
    public String index(HttpServletRequest request) {
        String theName = request.getParameter("name")==null ? "stranger" : request.getParameter("name");

        return "Hello " + theName;
    }

    @RequestMapping(value="hello" , method = RequestMethod.GET)
    @ResponseBody
    public String helloform(){
        languages.put("English","Hello ");
        languages.put("French", "Bonjour ");
        languages.put("Spanish", "Hola, como estas ");
        languages.put("German"," Hallo ");
        languages.put("Italian","CIAO ");
        String addi = "";
        for(String i : languages.keySet()){
            addi+="<option value='"+i+"'>"+i+"</option>";
        }
        return "<form method='post'>"+
                "<input type='text' name='name' />"+
                "<select name=\"langselect\">"+
                addi+"</select>"+
                "<button>Greet me!</button>"+
                "</form><hr><a href='../register'>add another language</a>";
    }
    @RequestMapping(value = "register" , method = RequestMethod.GET)
    @ResponseBody
    public String register(){
        return "<form method='post'>"+
        "<label>Language name: <input type='text' name='code' /></label>"+
                "<label>How to say Hello: <input type='text' name='hello' /></label><button>Submit</button></form>";
    }@RequestMapping(value = "register" , method = RequestMethod.POST)

    public String registerDone(HttpServletRequest request){
       languages.put(request.getParameter("code"),request.getParameter("hello")+" ");
        return "redirect:/hello";
    }
    @RequestMapping(value="hello", method = RequestMethod.POST)
    @ResponseBody
    public String helloPostHandler(HttpServletRequest request, HttpServletResponse response){
      String cookieCounter="1";
       Cookie[] cookies = request.getCookies();
       if(cookies != null){
           for(Cookie cookie : cookies){
                    if(cookie.getName().equals("count")){
                       cookie.setValue(String.valueOf(Integer.parseInt(cookie.getValue())+1));
                     response.addCookie(cookie);
                       cookieCounter = cookie.getValue();
                    }
           }
       }else{
           response.addCookie(new Cookie("count","1"));
            cookieCounter = "1";
       }
        //count++;
        String theName = request.getParameter("name");
        String lang = request.getParameter("langselect");

        return "<p>I've said hello to you "+cookieCounter+" times</p><hr><p style = 'text-align:center;color:red'>"+languages.get(lang) + theName+"<p>"+
                "<a href='../hello'>go back</a>";
    }
    @RequestMapping(value = "hello/{name}")
    @ResponseBody
    public String helloUrlSegment(@PathVariable String name){
        return "hello "+name;
    }
    @RequestMapping(value="goodbye")
    public String goodbye(){
        return "redirect:/";
    }
}
