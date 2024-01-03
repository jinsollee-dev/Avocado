package com.example.avocado.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class testcontroller {


  @GetMapping("/thistory")
  public void thistory(){}

  @GetMapping("/buyhistory")
  public void buyhistory(){}


  @GetMapping("/view")
  public void view(){}


}
