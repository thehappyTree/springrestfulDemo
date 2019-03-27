package com.websystique.springmvc.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.model.User;

@Service("userService")
@Transactional
public class UserServiceImp implements UserService {

    private static final AtomicLong counter = new AtomicLong();
    
    private static List<User> users;
    static {
    	users = populateDummyUsers();
    	
    }

	public User findById(long id) {
		// TODO Auto-generated method stub
		for (User user : users) {
			if(user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public User findByName(String name) {
		// TODO Auto-generated method stub
		for (User user : users) {
			if(user.getName().equalsIgnoreCase(name)) {
				return user;
			}
		}
		return null;
	}

	public void saveUser(User user) {
		// TODO Auto-generated method stub
		user.setId(counter.incrementAndGet());
		users.add(user);
		
	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub
		int index = users.indexOf(user);
        users.set(index, user);

		
		
	}

	public void deleteUserById(long id) {
		// TODO Auto-generated method stub
		User user = null;

		for (Iterator<User> iterator = users.iterator();iterator.hasNext();) {
			user = iterator.next();
			if (user.getId() == id) {
				iterator.remove();
			}
		}
		
		
	}

	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return users;
	}

	public void deleteAllUsers() {
		// TODO Auto-generated method stub
		
	}

	public boolean isUserExist(User user) {
		// TODO Auto-generated method stub
		return findByName(user.getName()) != null;
	}
	
   private static List<User> populateDummyUsers(){
        List<User> users = new ArrayList<User>();
        users.add(new User(counter.incrementAndGet(),"Sam",30, 70000));
        users.add(new User(counter.incrementAndGet(),"Tom",40, 50000));
        users.add(new User(counter.incrementAndGet(),"Jerome",45, 30000));
        users.add(new User(counter.incrementAndGet(),"Silvia",50, 40000));
        return users;
    }

	
	
}
