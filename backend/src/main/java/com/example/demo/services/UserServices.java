package com.example.demo.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repos.UserDao;

@Service 
public class UserServices {

    @Autowired
    private UserDao userDao;

	public ResponseEntity<Map<String, String>> login(String name, String password) {
		
		User obj=userDao.findByName(name);
		Map<String, String> response = new HashMap<>();
        if (obj != null && obj.getPassword().equals(password)) {
            response.put("message", "Login successful");
            response.put("user", String.valueOf(obj.getId()));
            return ResponseEntity.ok(response);
        }

        response.put("message", "Invalid Account Number or Password");
        return ResponseEntity.status(401).body(response); 
    }

	public ResponseEntity<Map<String, String>> register(User user) {
		 Map<String, String> response = new HashMap<>();
	   try{
		   System.out.println("1234");
		   userDao.save(user);
		   response.put("message", "Account created successfully");
		    response.put("status", "success");
		    return ResponseEntity.ok(response);

	   }
	   catch(Exception e){
		   response.put("message",e.getMessage());
		    response.put("status", "fail");
		    return ResponseEntity.status(401).body(response);

	   }

	   
	    

	}

	public ResponseEntity<Map<String, String>> changePassword(String name, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
		User obj=userDao.findByName(name);
		Map<String, String> response = new HashMap<>();
		if(obj !=null && obj.getPassword().equals(oldPassword)) {
				userDao.updatePassword(name, newPassword);
			response.put("message","User Updated Successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Invalid Account Number or Old Password");
        return ResponseEntity.status(401).body(response);
	}

	public ResponseEntity<Map<String, String>> forgetPassword(String name, Date dob) {
		// TODO Auto-generated method stub
		User obj=userDao.findByName(name);
		
	
		Map<String, String> response = new HashMap<>();
		if(obj !=null && obj.getDob().equals(dob)) {
			String password =obj.getPassword();
			response.put("message",password);
			return ResponseEntity.ok(response);
		}
		response.put("message", "Invalid Account name or DOB");
        return ResponseEntity.status(401).body(response);
	}

	public ResponseEntity<Map<String, String>> performTransaction(String name, Double updateAmount) {
		// TODO Auto-generated method stub
		User obj=userDao.findByName(name);
		
		Map<String, String> response = new HashMap<>();
		
		if(obj != null && obj.getBalance()+updateAmount>=0) {
			
			userDao.updateBalance(name,updateAmount+obj.getBalance());
			response.put("message","User Balance Updated Successfully");
			return ResponseEntity.ok(response);
		}
		response.put("message", "Invalid Account Name insufficent balance");
        return ResponseEntity.status(401).body(response);
		
	}
	}
	
	
	
	
	