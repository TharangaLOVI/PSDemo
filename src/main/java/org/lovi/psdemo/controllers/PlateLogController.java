package org.lovi.psdemo.controllers;

import java.util.List;

import org.lovi.psdemo.config.CommonConfig;
import org.lovi.psdemo.dao.BranchDAO;
import org.lovi.psdemo.models.Menu;
import org.lovi.psdemo.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value=CommonConfig.URL_PLATE_LOG)
public class PlateLogController {

	@Autowired
	private UserSession userSession;
	
	@Autowired
	private BranchDAO shopDAO;
	
	@RequestMapping()
	public ModelAndView index(){
		
		if((userSession.getUser() != null) && checkPageRequestIsValide()){
			ModelAndView modelAndView = new ModelAndView("plate_log/manage_plate_logs");
			modelAndView.addObject("user", userSession.getUser());
			
			try{
				//load all shops
				modelAndView.addObject("shops", shopDAO.getAllBranches());
			}catch(Exception e){
				
			}
			
			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("redirect:/");
			return modelAndView;
		}

		
	}
	
	/**
	 * check page is allow for current user
	 * @return
	 */
	private boolean checkPageRequestIsValide(){

		for(Menu menu : userSession.getUser().getArrayListMenu()){
			//CommonConfig.URL_PLATE_LOG -> '/path'
			//CommonConfig.URL_PLATE_LOG.substring(1) -> 'path'
			if(menu.getPageUrl().equals(CommonConfig.URL_PLATE_LOG.substring(1))) return true;
		}
		return false; 
	}
}
