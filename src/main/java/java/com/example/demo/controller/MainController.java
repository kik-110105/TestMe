package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Person;
import com.example.demo.PersonDAOPersonImpl;
import com.example.demo.ShiftDAOPersonImpl;
import com.example.demo.repositories.PersonRepository;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Controller
public class MainController {
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonDAOPersonImpl dao;
	
	@Autowired
	ShiftDAOPersonImpl shift;
	
	@RequestMapping("/top")
	public ModelAndView top(
			ModelAndView mav) {
		mav.setViewName("top");
		mav.addObject("title","トップページ");
		mav.addObject("msg","メニューを選んでね");
		return mav;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
 	}

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("JST"));
	}
	
	@RequestMapping("/")
	public ModelAndView index(@ModelAttribute("formModel")Person person, ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("title", "ユーザー登録");
		mav.addObject("msg","ユーザー情報を入力してください");
		List<Person> list = dao.getAll();
		mav.addObject("data",list);
		return mav;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, ModelAndView mav) {
		mav.setViewName("login");
		String mail = request.getParameter("mail");
		String pass_O = request.getParameter("pass_O");
		if(mail=="") {
			mav.addObject("title", "ログインエラー");
			mav.addObject("msg","もう一度ユーザー情報を入力してください");
			mav=new ModelAndView("redirect:/login");
		}
		else {
			if(pass_O=="") {
				mav.addObject("title", "ログインエラー");
				mav.addObject("msg","もう一度ユーザー情報を入力してください");
				mav=new ModelAndView("redirect:/login");
			}
			else {
				List<Person> list1 = dao.login_mail(mail);
				List<Person> list2 = dao.login_pass(pass_O);
				if (list1.equals(list2)) {
					mav.addObject("data", list1);
					mav=new ModelAndView("redirect:/top");
				}
				else {
					mav.addObject("title", "ログインエラー");
					mav.addObject("msg","もう一度ユーザー情報を入力してください");
					mav=new ModelAndView("redirect:/login");
				}
			}
		}
		return mav;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView login(ModelAndView mav) {
		mav.setViewName("login");
		mav.addObject("title", "ログイン");
		mav.addObject("msg","ユーザー情報を入力してください");
		Iterable<Person> list1 = dao.getAll();
		mav.addObject("data",list1);
		return mav;
	}
	
	@RequestMapping(value="/find", method=RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request, ModelAndView mav) {
		mav.setViewName("find");
		String param = request.getParameter("find_str");
		if(param=="") {
			mav=new ModelAndView("redirect:/find");
		}else {
			List<Person> list = dao.find(param);
			mav.addObject("data", list);
		}
		return mav;
	}
	
	@RequestMapping(value="/find", method=RequestMethod.GET)
	public ModelAndView find(ModelAndView mav) {
		mav.setViewName("find");
		mav.addObject("msg","Personのサンプルです");
		Iterable<Person> list = dao.getAll();
		mav.addObject("data",list);
		return mav;
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	@Transactional
	public ModelAndView form(
			@ModelAttribute("formModel") @Validated Person person, 
			BindingResult result,
			@RequestParam String pass_A,
			@RequestParam String pass_O,			
			ModelAndView mav) {
		
		ModelAndView res = null;
		System.out.println(result.getFieldErrors());
		if(!result.hasErrors()) {
			if (pass_A.equals(pass_O)) {
				repository.saveAndFlush(person);
				res = new ModelAndView("redirect:/");
			}
			else {
				mav.setViewName("index");
				mav.addObject("title","パスワードが一致しませんでした");
				mav.addObject("msg","もう一度情報を入力してください");
				Iterable<Person> list = repository.findAll();
				mav.addObject("datalist",list);
				res = mav;
			}
		}else {
			mav.setViewName("index");
			mav.addObject("title","Hello world!");
			mav.addObject("msg","soory, error is occurrd...");
			Iterable<Person> list = repository.findAll();
			mav.addObject("datalist",list);
			res = mav;
		}
		return res;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView edit(@ModelAttribute Person Person,@PathVariable int id, ModelAndView mav) {
		mav.setViewName("edit");
		mav.addObject("title","edit Person");
		Optional<Person> data = repository.findById((long)id);
		mav.addObject("formModel",data.get());
		return mav;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@Transactional
	public ModelAndView update(@ModelAttribute Person Person, ModelAndView mav) {
		repository.saveAndFlush(Person);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id, ModelAndView mav) {
		mav.setViewName("delete");
		mav.addObject("title","Delete Person.");
		mav.addObject("msg", "Can I delete this record?");
		Optional<Person> data = repository.findById((long)id);
		mav.addObject("formModel",data.get());
		return mav;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@Transactional
	public ModelAndView remove(@RequestParam long id, ModelAndView mav) {
		repository.deleteById(id);
		return new ModelAndView("redirect:/");
		
	}
}