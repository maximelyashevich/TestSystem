package com.incubator.app.controller;


import com.incubator.app.entity.Test;
import com.incubator.app.entity.Topic;
import com.incubator.app.entity.User;
import com.incubator.app.service.TestService;
import com.incubator.app.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TutorController {

    @Autowired
    private TestService testService;

    @Autowired
    private TopicService topicService;


    @RequestMapping(value = {"/tutor"}, method = RequestMethod.GET)
    public ModelAndView listAllTests(){
        ModelAndView modelAndView = new ModelAndView();
        List<Test> tests = testService.findAll();
        modelAndView.setViewName("tests-list");
        modelAndView.addObject("tests", tests);
        return modelAndView;
    }


    @RequestMapping(value = {"/tutor/tests/create"}, method = RequestMethod.GET)
    public String redirectToCreateTestPage(Model model){
        model.addAttribute("test", new Test());
        List<Topic> topics = topicService.findAll();
        Map<Long, String> topicsMap = topics.stream().collect(
                Collectors.toMap(Topic::getId, Topic::getName));
        model.addAttribute("topics", topicsMap);
        return "create-test";
    }

    @RequestMapping(value = {"/tutor/tests/create"}, method = RequestMethod.POST)
    public String addTest(@ModelAttribute("test") Test test){
        System.out.println(test);
        test.setTopic(topicService.findById(test.getTopic().getId()));
        test.setIsDeleted(0);
        testService.insert(test);
        return "redirect:/tutor";
    }
    @RequestMapping(value = {"/tutor/tests/{id}"}, method = RequestMethod.GET)
    public String editTestRedirect(@PathVariable("id") long id, Model model){
        Test test = testService.findById(id);
        model.addAttribute("test", test);
        return "create-test";
    }

    @RequestMapping(value = {"/tutor/tests/{id}"}, method = RequestMethod.POST)
    public String editTest(@PathVariable("id") long id, @ModelAttribute("test") Test test){
        test.setId(id);
        test.setIsDeleted(0);
        testService.update(test);
        return "redirect:/admin/tests";
    }

    @RequestMapping(value = {"/tutor/tests/{ids}"}, method = RequestMethod.DELETE)
    public ResponseEntity deleteTests(@PathVariable("ids") long[] ids){
        testService.deleteAll(ids);
        return new ResponseEntity<Topic>(HttpStatus.NO_CONTENT);
    }


}