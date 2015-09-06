package org.lovi.psdemo.controllers;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lovi.psdemo.config.CommonConfig;
import org.lovi.psdemo.controllers.ItemController.CustomFieldEditorDouble;
import org.lovi.psdemo.dao.BranchDAO;
import org.lovi.psdemo.dao.ItemDAO;
import org.lovi.psdemo.dao.ItemsPlateDAO;
import org.lovi.psdemo.models.Item;
import org.lovi.psdemo.models.ItemCategory;
import org.lovi.psdemo.models.ItemsPlate;
import org.lovi.psdemo.models.Menu;
import org.lovi.psdemo.models.Branch;
import org.lovi.psdemo.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = CommonConfig.URL_ITEMS_PLATE)
public class ItemsPlateController {

	@Autowired
	private UserSession userSession;
	
	@Autowired
	private BranchDAO branchDAO;
	
	@Autowired
	private ItemDAO itemDAO;
	
	@Autowired
	private ItemsPlateDAO itemsPlateDAO;
	
	@RequestMapping()
	public ModelAndView index(){
		
		if((userSession.getUser() != null) && checkPageRequestIsValide()){
			ModelAndView modelAndView = new ModelAndView("items_plate/manage_items_plates");
			
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

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		/**
		 * ItemsPlate->items(Item->itemId)
		 */
		binder.registerCustomEditor(Integer.class, "items.itemId",
				new CustomFieldInteger());
		binder.registerCustomEditor(Integer.class, "items.plateItemQty",
				new CustomFieldInteger());
		binder.registerCustomEditor(Branch.class, "branch", new CustomFieldBranchId());
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addItemsPlate(@ModelAttribute ItemsPlate requestParm,
			RedirectAttributes redirectAttrs) {

		try {

			// required fields -> itemName,itemPrice,itemCategory
			if (requestParm.getPlateName().equals("")
					|| (requestParm.getItems().size() == 0)) {
				redirectAttrs.addFlashAttribute("item", requestParm);

				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(-1, CommonConfig.VIEW_TYPE_ITEM_ADD,
								CommonConfig.VIEW_REQUEST_PARAMETERS_ARE_NULL));

				return "redirect:" + CommonConfig.URL_BRANCH;
			} else {

				itemsPlateDAO.insertItemsPlate(requestParm);

				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(1, CommonConfig.VIEW_TYPE_ITEM_ADD,
								CommonConfig.VIEW_ITEM_ADD_SUCCESS));

				return "redirect:" + CommonConfig.URL_BRANCH;
			}
		} catch (Exception e) {

			System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());

			redirectAttrs.addFlashAttribute("item", requestParm);

			redirectAttrs.addFlashAttribute(
					CommonConfig.MESSAGE,
					prepareJSONOBJ(-1, CommonConfig.VIEW_TYPE_ITEM_ADD,
							CommonConfig.VIEW_ITEM_NAME_DUPLICATE));

			return "redirect:" + CommonConfig.URL_BRANCH;
		}
	}

	@RequestMapping(value = "shop/{shopId}")
	public @ResponseBody String getItemsPlatesForShop(@PathVariable int shopId) {
		try {
			if (shopId == 0) {
				return prepareJSONresult(-1,
						CommonConfig.REQUEST_PARAMETERS_INVALID);
			} else {
				List<ItemsPlate> itemsPlates = itemsPlateDAO
						.searchByShopId(shopId);
				return prepareJSONArrayResult(itemsPlates);
			}
		} catch (Exception e) {
			return prepareJSONresult(-1, CommonConfig.DB_ERROR);
		}
	}

	/**
	 * Check this page is allow to access current user
	 * 
	 * @return
	 */
	private boolean checkPageRequestIsValide() {

		for (Menu menu : userSession.getUser().getArrayListMenu()) {
			if (menu.getPageUrl().equals(
					CommonConfig.URL_ITEMS_PLATE.substring(1)))
				return true;
		}
		return false;
	}

	/**
	 * Prepare message for View
	 * 
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

	public class CustomFieldInteger extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			// TODO Auto-generated method stub
			if (text.equals("")) {
				setValue(0);
			} else {
				setValue(Integer.parseInt(text));
			}

		}
	}

	private class CustomFieldBranchId extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			// TODO Auto-generated method stub
			if (text.equals("0")) {
				Branch brach = new Branch();
				brach.setBranchId(0);
				setValue(brach);
			} else {
				Branch brach = new Branch();
				brach.setBranchId(Integer.parseInt(text));
				setValue(brach);
			}

		}
	}

	/**
	 * prepare Object to JSON format
	 * 
	 * @param objectValue
	 * @return
	 */
	private String prepareJSONresult(Object objectValue) {

		JSONObject jsonResponce = new JSONObject();
		try {
			if (objectValue != null) {
				jsonResponce.put(CommonConfig.STATUS, 1);

				ObjectMapper objectMapper = new ObjectMapper();
				String stringObject = objectMapper
						.writeValueAsString(objectValue);
				JSONObject jsonObjectValue = new JSONObject(stringObject);
				jsonResponce.put(CommonConfig.VALUE, jsonObjectValue);

			} else {
				throw new Exception("");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			jsonResponce.put(CommonConfig.STATUS, -1);
			jsonResponce.put(CommonConfig.VALUE, e.getMessage().toString());
		}
		return jsonResponce.toString();

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
	 * prepare JSON Object to JSON format
	 * 
	 * @param status
	 * @param value
	 * @return
	 */
	private String prepareJSONresult(int status, JSONObject value) {

		JSONObject jsonResponce = new JSONObject();
		jsonResponce.put(CommonConfig.STATUS, status);
		jsonResponce.put(CommonConfig.VALUE, value);
		return jsonResponce.toString();

	}

	private String prepareJSONArrayresult(JSONArray array) {

		JSONObject jsonResponce = new JSONObject();
		try {
			if (array != null) {
				jsonResponce.put(CommonConfig.STATUS, 1);
				jsonResponce.put(CommonConfig.VALUE, array);

			} else {
				throw new Exception("");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			jsonResponce.put(CommonConfig.STATUS, -1);
			jsonResponce.put(CommonConfig.VALUE, e.getMessage().toString());
		}
		return jsonResponce.toString();

	}
	
	private String prepareJSONArrayResult(Object objectValue) {

		JSONObject jsonResponce = new JSONObject();
		try {
			if (objectValue != null) {
				jsonResponce.put(CommonConfig.STATUS, 1);

				ObjectMapper objectMapper = new ObjectMapper();
				String stringObject = objectMapper
						.writeValueAsString(objectValue);
				JSONArray jsonArrayValue = new JSONArray(stringObject);
				jsonResponce.put(CommonConfig.VALUE, jsonArrayValue);

			} else {
				throw new Exception("");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage().toString());
			jsonResponce.put(CommonConfig.STATUS, -1);
			jsonResponce.put(CommonConfig.VALUE, e.getMessage().toString());
		}
		return jsonResponce.toString();

	}

}
