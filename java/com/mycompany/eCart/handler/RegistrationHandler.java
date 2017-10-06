package com.mycompany.eCart.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;

import com.mycompany.eCartBackEnd.dao.UserDAO;
import com.mycompany.eCartBackEnd.model.User;

@Component
public class RegistrationHandler {

	@Autowired
	UserDAO userDAO;

	public User initFlow()
	{
		return new User();
	}

	public String validateDetails(User user, MessageContext messageContext)
	{
		String status = "success";
		
		if (user.getF_name().isEmpty()) {
			messageContext.addMessage(
					new MessageBuilder().error().source("f_name").defaultText("First Name cannot be empty").build());
			status = "failure";
		}
		
		if (user.getL_name().isEmpty()) {
			messageContext.addMessage(
					new MessageBuilder().error().source("l_name").defaultText("Last Name cannot be Empty").build());
			status = "failure";
		}

		if (user.getMobile().isEmpty()) {
			messageContext.addMessage(
					new MessageBuilder().error().source("mobile").defaultText("Mobile Number cannot be Empty").build());
			status = "failure";
		}

		
		if (user.getMail_id().isEmpty()) {
			messageContext.addMessage(
					new MessageBuilder().error().source("mail_id").defaultText("E-mail ID cannot be Empty").build());
			status = "failure";
		}
		
		if (user.getPassword().isEmpty()) {
			messageContext.addMessage(
					new MessageBuilder().error().source("password").defaultText("Password cannot be Empty").build());
			status = "failure";
		}

		return status;
	}

	public String saveDetails(User user) 
	{
		user.setRole("ROLE_USER");
		userDAO.addUser(user);
		return "success";
	}
}