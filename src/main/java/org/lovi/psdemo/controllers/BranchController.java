package org.lovi.psdemo.controllers;

import java.util.ArrayList;

import org.json.JSONObject;
import org.lovi.psdemo.config.CommonConfig;
import org.lovi.psdemo.dao.ItemDAO;
import org.lovi.psdemo.dao.BranchDAO;
import org.lovi.psdemo.dao.UserDAO;
import org.lovi.psdemo.models.Item;
import org.lovi.psdemo.models.Menu;
import org.lovi.psdemo.models.Branch;
import org.lovi.psdemo.models.User;
import org.lovi.psdemo.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value=CommonConfig.URL_BRANCH)
public class BranchController {

	@Autowired
	private UserSession userSession;
	
	@Autowired
	private BranchDAO branchDAO;
	
	@Autowired
	private ItemDAO itemDAO;
	
	@RequestMapping()
	public ModelAndView index(){
		
		if((userSession.getUser() != null) && checkPageRequestIsValide()){
			ModelAndView modelAndView = new ModelAndView("branch/manage_branches");
			
			modelAndView.addObject("user", userSession.getUser());
			
			try{
				//load shops
				modelAndView.addObject("branches", branchDAO.getAllBranches());
				
				//load items
				modelAndView.addObject("items", itemDAO.getItems());
				
			}catch(Exception e){
				System.out.print(CommonConfig.DB_ERROR + " : " + e.getMessage());
				modelAndView.addObject("branches", new ArrayList<Item>());
			}
			
			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("redirect:/");
			return modelAndView;
		}
		
		
	}
	
	/**
	 * Add new branch
	 * @param requestParm
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String addShop(@ModelAttribute Branch requestParm,RedirectAttributes redirectAttrs){
		
		try {
			
			//required fields -> ShopName,PhoneNumber,Address
			if (requestParm.getBranchName().equals("")
					|| requestParm.getPhoneNumber().equals("")
					|| requestParm.getAddress().equals("")) {
				
				redirectAttrs.addFlashAttribute("branch", requestParm);

				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(-1,
								CommonConfig.VIEW_TYPE_BRANCH_ADD,
								CommonConfig.VIEW_REQUEST_PARAMETERS_ARE_NULL));

				return "redirect:"+CommonConfig.URL_BRANCH;
			} else {

				branchDAO.inserBranch(requestParm);
				
				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(1,
								CommonConfig.VIEW_TYPE_BRANCH_ADD,
								CommonConfig.VIEW_BRANCH_ADD_SUCCESS));

				return "redirect:"+CommonConfig.URL_BRANCH;
			}
		} catch (Exception e) {
			
			System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());
			
			redirectAttrs.addFlashAttribute("branch", requestParm);

			redirectAttrs.addFlashAttribute(
					CommonConfig.MESSAGE,
					prepareJSONOBJ(-1,
							CommonConfig.VIEW_TYPE_BRANCH_ADD,
							CommonConfig.DB_ERROR));

			return "redirect:"+CommonConfig.URL_BRANCH;
		}

	}
	
	/**
	 * Prepare message for View
	 * @param status
	 * @param messageType
	 * @param value
	 * @return
	 */
	private JSONObject prepareJSONOBJ(int status, String messageType,
			String value) {

		JSONObject jsonResponce = new JSONObject();
		jsonResponce.put(CommonConfig.STATUS, status);
		jsonResponce.put(CommonConfig.VIEW_MESSAGE_TYPE, messageType);
		jsonResponce.put(CommonConfig.VALUE, value);
		return jsonResponce;

	}
	
	private boolean checkPageRequestIsValide(){

		for(Menu menu : userSession.getUser().getArrayListMenu()){
			if(menu.getPageUrl().equals(CommonConfig.URL_BRANCH.substring(1))) return true;
		}
		return false; 
	}
}
