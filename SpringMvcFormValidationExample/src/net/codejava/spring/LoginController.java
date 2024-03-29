/*
 * http://www.codejava.net/frameworks/spring/spring-mvc-form-validation-example-with-bean-validation-api
 * http://localhost:8080/SpringMvcFormValidationExample/login
 * 
 * http://springjavatutorial.blogspot.ro/2013/03/spring-3-mvc-form-validation.html
 * http://www.codejava.net/books/recommended-books-for-spring-framework#ProSpring3
 *
 */

package net.codejava.spring;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author www.codejava.net
 *
 */
@Controller
public class LoginController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLogin(Map<String, Object> model) {
		User user = new User();
		model.put("userForm", user);
		return "LoginForm";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(@Valid @ModelAttribute("userForm") User userForm,
			BindingResult result, Map<String, Object> model) {

		if (result.hasErrors()) {
			return "LoginForm";
		}

		return "LoginSuccess";
	}
}