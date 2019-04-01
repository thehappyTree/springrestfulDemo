package com.websystique.springmvc.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.websystique.springmvc.model.User;
import com.websystique.springmvc.service.UserService;

@RestController
public class HelloWorldRestController {
	@Autowired
	UserService userService;
	static Logger log = Logger.getLogger(HelloWorldRestController.class.getName());

	@RequestMapping(value="/user/", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> listUsers(){
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(users,HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/user/", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addUser(@RequestBody User user ) {
		if (userService.isUserExist(user)) {
			return new ResponseEntity<Long>((long) -1,HttpStatus.CONFLICT);
		}
		userService.saveUser(user);
		long userId = user.getId();
		return new ResponseEntity<Long>(userId, HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") long id ){
        System.out.println("Fetching User with id " + id);
       // User user = userservice.
        User user = userService.findById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}", method=RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user){
		log.info("update user"+ id);
		User currentUser = userService.findById(id);
		if (currentUser == null) {
			log.info("未找到用户"+id);
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			
		}
		currentUser.setAge(user.getAge());
		currentUser.setName(user.getName());
		currentUser.setSalary(user.getSalary());
		return new ResponseEntity<User>(currentUser,HttpStatus.OK);
		
		
	}
	
	@RequestMapping(value="/user/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id){
		log.info("delete user" + id);
		User currentUser = userService.findById(id);
		if (currentUser == null) {
			log.info("未找到用户"+id);
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
		
	}
	
}
