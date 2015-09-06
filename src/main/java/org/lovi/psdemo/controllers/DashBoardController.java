package org.lovi.psdemo.controllers;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lovi.psdemo.config.CommonConfig;
import org.lovi.psdemo.dao.BranchDAO;
import org.lovi.psdemo.dao.ItemsPlateDAO;
import org.lovi.psdemo.dao.PlateLogDAO;
import org.lovi.psdemo.dao.UserDAO;
import org.lovi.psdemo.models.Branch;
import org.lovi.psdemo.models.Item;
import org.lovi.psdemo.models.ItemsPlate;
import org.lovi.psdemo.models.Menu;
import org.lovi.psdemo.models.PlateLog;
import org.lovi.psdemo.models.User;
import org.lovi.psdemo.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value=CommonConfig.URL_DASH_BOARD)
public class DashBoardController {
	
	@Autowired
	private UserSession userSession;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BranchDAO branchDAO;
	
	@Autowired
	private ItemsPlateDAO itemsPlateDAO;
	
	@Autowired
	private PlateLogDAO plateLogDAO;
	
	/**
	 * This is first version of DASH BOARD
	 * @param request
	 * @return
	 */
	/*
	@RequestMapping()
	public ModelAndView index(){
		
		if((userSession.getUser() != null) && checkPageRequestIsValide()){
			ModelAndView modelAndView = new ModelAndView("dash_board/index");
			
			modelAndView.addObject("user", userSession.getUser());
			
			try{
				JSONArray driversJSONArray = new JSONArray();
				List<User> drivers = userDAO.findByRoleId(4);
				
				JSONArray branchesJSONArray = new JSONArray();
				List<Branch> branchs = branchDAO.getAllBranches();
				
				//append all the items plate with particular branch
				for(Branch branch : branchs){
					//create branchJSONObject from branch
					JSONObject branchJSONObject = new JSONObject(branch);
					List<ItemsPlate> itemsPlates = itemsPlateDAO.searchByShopId(branch.getBranchId());
					
					//add itemsPlates for branchJsonObject
					branchJSONObject.put("itemsPlates", itemsPlates);
					branchesJSONArray.put(branchJSONObject);
				}
				
				//append all the branches with each driver
				for(User driver : drivers){
					//create driverJSONObject from driver
					JSONObject driverJSONObject = new JSONObject(driver);
					
					//add branches with driverJSONObject
					driverJSONObject.put("branches", branchesJSONArray);
					
					driversJSONArray.put(driverJSONObject);
					
				}
				modelAndView.addObject("dataJSONArray", driversJSONArray);
				
				System.out.println(driversJSONArray.toString());
				
			}catch(Exception e){
				System.out.print(CommonConfig.DB_ERROR + " : " + e.getMessage());
				modelAndView.addObject("dataJSONArray", new JSONArray());
				modelAndView.addObject("items", new ArrayList<Item>());
			}
			
			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("redirect:/");
			return modelAndView;
		}
		
	}
	*/
	
	@RequestMapping()
	public ModelAndView index(){
		
		if((userSession.getUser() != null) && checkPageRequestIsValide()){
			ModelAndView modelAndView = new ModelAndView("dash_board/supervisor_dash_board");
			//ModelAndView modelAndView = new ModelAndView("dash_board/dd");
			
			modelAndView.addObject("user", userSession.getUser());
			
			try{
				List<User> drivers = userDAO.findByRoleId(4);
				
				JSONArray branchesJSONArray = new JSONArray();
				List<Branch> branches = branchDAO.getAllBranches();
				
				//append all the items plate with particular branch
				for(Branch branch : branches){
					//create branchJSONObject from branch
					JSONObject branchJSONObject = new JSONObject(branch);
					List<ItemsPlate> itemsPlates = itemsPlateDAO.searchByShopId(branch.getBranchId());
					
					//add itemsPlates for branchJsonObject
					branchJSONObject.put("itemsPlates", itemsPlates);
					branchesJSONArray.put(branchJSONObject);
				}
				
				modelAndView.addObject("drivers", drivers);
				modelAndView.addObject("branches", branchesJSONArray);
				
				System.out.println("drivers : " + drivers);
				System.out.println("branches : " + branchesJSONArray);
				
				
			}catch(Exception e){
				System.out.print(CommonConfig.DB_ERROR + " : " + e.getMessage());
				modelAndView.addObject("drivers", new JSONArray());
				modelAndView.addObject("branches", new JSONArray());
			}
			
			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("redirect:/");
			return modelAndView;
		}
		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody String addPlateLogs(HttpServletRequest request){
		
		try{
			String requestContent = getRequestContent(request);
			JSONArray plateLogsJSONArray = new JSONArray(requestContent);
			
			for(int i = 0 ; i<plateLogsJSONArray.length() ; i++){
					
				JSONObject plateLogJSON = plateLogsJSONArray.getJSONObject(i);
				
				PlateLog plateLog = new PlateLog();
				
				plateLog.setDate(new SimpleDateFormat("yy-MM-dd").format(new Date()));
				
				plateLog.setRelatedSupervicerId(userSession.getUser().getUserId());
				plateLog.setRelatedDriverId(plateLogJSON.getString("driver"));
				
				//get items plates from the request
				JSONArray itemsPlatesJSONArray = plateLogJSON.getJSONArray("itemsPlates");
				for(int j = 0 ; j<itemsPlatesJSONArray.length() ; j++){
					ItemsPlate itemsPlate = new ItemsPlate();
					itemsPlate.setPlateId(itemsPlatesJSONArray.getInt(j));
					
					plateLog.setItemsPlate(itemsPlate);
					
					plateLogDAO.insert(plateLog);
				}
			
			}
			
			return prepareJSONresult(1, CommonConfig.VIEW_DASH_BOARD_ADD_SUCCESS);
			
		}catch(Exception e){
			System.out.println("DB Error : " + e.getMessage());
			return prepareJSONresult(-1, CommonConfig.DB_ERROR);
		}
		
	}
	
	/**
	 * Check this page is allow to access current user
	 * @return
	 */
	private boolean checkPageRequestIsValide(){

		for(Menu menu : userSession.getUser().getArrayListMenu()){
			if(menu.getPageUrl().equals(CommonConfig.URL_DASH_BOARD.substring(1))) return true;
		}
		return false; 
	}
	
	/**
	 * Get JSON Request Content in String 
	 * @param httpServletRequest
	 * @return
	 * @throws Exception
	 */
	private String getRequestContent(HttpServletRequest httpServletRequest) throws Exception{
		
		StringBuffer rideDataJsonString = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = httpServletRequest.getReader();
			while ((line = reader.readLine()) != null) {
				rideDataJsonString.append(line);
			}
			String requetsData = rideDataJsonString.toString();
			System.out.println("Request Data --- \n" + requetsData);
			
			return requetsData;
			
		} catch (Exception e) {
			
			throw new Exception(e.getMessage().toString());
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

}
