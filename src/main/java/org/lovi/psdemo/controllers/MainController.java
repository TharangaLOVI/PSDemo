package org.lovi.psdemo.controllers;

import org.json.JSONObject;
import org.lovi.psdemo.config.CommonConfig;
import org.lovi.psdemo.dao.UserDAO;
import org.lovi.psdemo.models.User;
import org.lovi.psdemo.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/")
public class MainController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserSession userSession;
	
	@RequestMapping()
	public String index(){
		
		if(userSession.getUser() == null)
			return "index";
		else
			return "redirect:/" + userSession.getUser().getDefaultPageUrl();
		
	}
	
	@RequestMapping(value = "/sign_in", method = RequestMethod.POST)
	public String signIn(@ModelAttribute User requestParm,RedirectAttributes redirectAttrs) {
		
		try {
			if (requestParm.getUserId().equals("")
					|| requestParm.getPassword().equals("")) {
				
				redirectAttrs.addFlashAttribute("user", requestParm);
				
				redirectAttrs.addFlashAttribute(CommonConfig.MESSAGE,
						prepareJSONOBJ(-1,
										CommonConfig.VIEW_TYPE_SIGN_IN, 
										CommonConfig.VIEW_REQUEST_PARAMETERS_ARE_NULL));
				
				return "redirect:/";

			} else {
				
				User user = userDAO.findByUserIdAndPassword(requestParm.getUserId(), requestParm.getPassword());
				
				if(user != null){
					userSession.setUser(user);
					String userDefaultPage = userSession.getUser().getDefaultPageUrl();
					
					return "redirect:/" + userDefaultPage;
					
				}else{
					
					redirectAttrs.addFlashAttribute("user", requestParm);
					redirectAttrs.addFlashAttribute(CommonConfig.MESSAGE,
							prepareJSONOBJ(-1,
											CommonConfig.VIEW_TYPE_SIGN_IN, 
											CommonConfig.VIEW_USER_NOT_FOUND));
					return "redirect:/";
				}
				
				
			}
		} catch (Exception e) {
			System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());
			redirectAttrs.addFlashAttribute(CommonConfig.MESSAGE,
					prepareJSONOBJ(-1,
									CommonConfig.VIEW_TYPE_SIGN_IN, 
									e.getMessage()));
			return "redirect:/";
		}
		
	}

	@RequestMapping(value="sign_out")
	public String signOut(){
		
		if(userSession.getUser() != null){
			userSession.setUser(null);
			return "redirect:/";
		}else{
			return "redirect:/";
		}
	}
	
	/**
	 * prepare Object to JSON format
	 * 
	 * @param status
	 * @param value
	 * @return
	 */
	private String prepareJSONresult(int status, String value) {

		JSONObject jsonResponce = new JSONObject();
		jsonResponce.put(CommonConfig.STATUS, status);
		jsonResponce.put(CommonConfig.VALUE, value);
		return jsonResponce.toString();

	}

	/**
	 * Prepare JSON Object
	 * This method use only in view controller.
	 * view controller are which directly connect with views.
	 * this methods is specific for this controller.
	 * this method is message encoder,encodes massage return for response to the view
	 * @param status -> 1 or -1
	 * @param messageType -> indicate weather ,response is came from what process(SIGN_UP,SIGN_IN,....) 
	 * @param value -> message
	 * @return
	 */
	private JSONObject prepareJSONOBJ(int status, String messageType,String value) {

		JSONObject jsonResponce = new JSONObject();
		jsonResponce.put(CommonConfig.STATUS, status);
		jsonResponce.put(CommonConfig.VIEW_MESSAGE_TYPE, messageType);
		jsonResponce.put(CommonConfig.VALUE, value);
		return jsonResponce;

	}
}
