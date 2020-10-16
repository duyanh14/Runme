/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.amonir.controller;

import com.dn.amonir.model.Request;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class View {

    @RequestMapping(value = "/")
    @ResponseBody
    public ModelAndView View(HttpServletRequest request) {
        ModelAndView m = new ModelAndView();
        m.addObject("Page", "Device".toLowerCase());
        m.addObject("Request", new Request(request,"Device".toLowerCase()));
        m.setViewName("../view");
        return m;
    }

    @RequestMapping(value = "/{name}")
    @ResponseBody
    public ModelAndView View_Name(@PathVariable("name") String name, HttpServletRequest request) {
        ModelAndView m = new ModelAndView();
        m.addObject("Page", name);
        m.addObject("Request", new Request(request,name));
        m.setViewName("../view");
        return m;
    }

}
