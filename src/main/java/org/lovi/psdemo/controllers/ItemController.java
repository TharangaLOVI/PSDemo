package org.lovi.psdemo.controllers;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;

import org.json.JSONObject;
import org.lovi.psdemo.config.CommonConfig;
import org.lovi.psdemo.dao.ItemCategoryDAO;
import org.lovi.psdemo.dao.ItemDAO;
import org.lovi.psdemo.dao.UserDAO;
import org.lovi.psdemo.models.Item;
import org.lovi.psdemo.models.ItemCategory;
import org.lovi.psdemo.models.Menu;
import org.lovi.psdemo.models.User;
import org.lovi.psdemo.session.UserSession;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value=CommonConfig.URL_ITEM)
public class ItemController {

	@Autowired
	private UserSession userSession;
	
	@Autowired
	private ItemDAO itemDAO;
	@Autowired
	private ItemCategoryDAO itemCategoryDAO;
	@Autowired 
	private UserDAO userDAO;
	
	@RequestMapping()
	public ModelAndView index(){
		
		if((userSession.getUser() != null) && checkPageRequestIsValide()){
			ModelAndView modelAndView = new ModelAndView("item/manage_items");
			
			modelAndView.addObject("user", userSession.getUser());
			
			try{
				//load items
				modelAndView.addObject("items", itemDAO.getItems());
				
				//load item categories
				modelAndView.addObject("categories", itemCategoryDAO.getCategories());
			}catch(Exception e){
				System.out.print(CommonConfig.DB_ERROR + " : " + e.getMessage());
				modelAndView.addObject("items", new ArrayList<Item>());
			}
			
			return modelAndView;
		}else{
			ModelAndView modelAndView = new ModelAndView("redirect:/");
			return modelAndView;
		}
		
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		/**
		 * before price field send to the addItem(),
		 * need to convert Double type using CustomFieldItemCat()
		 */
		binder.registerCustomEditor(Double.class,"price",new CustomFieldEditorDouble());
		
		/**
		 * before itemCategory field send to the addItem(),
		 * need to convert ItemCategory type using CustomFieldItemCat()
		 */
		binder.registerCustomEditor(ItemCategory.class,"itemCategory",new CustomFieldItemCat());
	}
	
	/**
	 * Add new item POST
	 * @param requestParm
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public String addItem(@ModelAttribute Item requestParm,RedirectAttributes redirectAttrs){
		
		try {
			
			//required fields -> itemName,itemPrice,itemCategory
			if (requestParm.getName().equals("")
					|| (requestParm.getItemCategory() == null)
					|| (requestParm.getPrice() == 0)) {
				redirectAttrs.addFlashAttribute("item", requestParm);

				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(-1,
								CommonConfig.VIEW_TYPE_ITEM_ADD,
								CommonConfig.VIEW_REQUEST_PARAMETERS_ARE_NULL));

				return "redirect:"+CommonConfig.URL_ITEM;
			} else {

				itemDAO.insertItem(requestParm);
				
				redirectAttrs.addFlashAttribute(
						CommonConfig.MESSAGE,
						prepareJSONOBJ(1,
								CommonConfig.VIEW_TYPE_ITEM_ADD,
								CommonConfig.VIEW_ITEM_ADD_SUCCESS));

				return "redirect:"+CommonConfig.URL_ITEM;
			}
		} catch (Exception e) {
			
			System.out.println(CommonConfig.DB_ERROR + " : " + e.getMessage());
			
			redirectAttrs.addFlashAttribute("item", requestParm);

			redirectAttrs.addFlashAttribute(
					CommonConfig.MESSAGE,
					prepareJSONOBJ(-1,
							CommonConfig.VIEW_TYPE_ITEM_ADD,
							CommonConfig.VIEW_ITEM_NAME_DUPLICATE));

			return "redirect:"+CommonConfig.URL_ITEM;
		}

	}
	
	/**
	 * Check this page is allow to access current user
	 * @return
	 */
	private boolean checkPageRequestIsValide(){

		for(Menu menu : userSession.getUser().getArrayListMenu()){
			if(menu.getPageUrl().equals(CommonConfig.URL_ITEM.substring(1))) return true;
		}
		return false; 
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
	
	/**
	 * Convert itemCategory field as ItemCategory,
	 * if text = '' then set categoryId as 0
	 * else parse text based categoryId to ItemCategory.categoryId
	 * @author Tharanga
	 *
	 */
	private class CustomFieldItemCat extends PropertyEditorSupport{

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			// TODO Auto-generated method stub
			if(text.equals("0")){
				ItemCategory category = new ItemCategory();
				category.setCategoryId(0);
				setValue(category);
			}else{
				ItemCategory category = new ItemCategory();
				category.setCategoryId(Integer.parseInt(text));
				setValue(category);
			}
			
			
		}
	}
	
	/**
	 * convert String price value into Double
	 * @author Tharanga
	 *
	 */
	public class CustomFieldEditorDouble extends PropertyEditorSupport{

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			// TODO Auto-generated method stub
			if(text.equals("")){
				setValue(0.0);
			}else{
				setValue(Double.parseDouble(text));
			}
			
		}
	}
	
	
}
