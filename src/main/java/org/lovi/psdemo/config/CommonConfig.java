package org.lovi.psdemo.config;

public class CommonConfig {

	/**
	 * JSON result keys
	 */
	public static String REQUEST_PARAMETERS_INVALID = "Invalid Values are provided";
	public static String MESSAGE = "message";
	public static String STATUS = "status";
	public static String VALUE = "value";
	public static String DB_ERROR = "db_error";
	public static String JSON_ERROR = "json_error";
	
	/**
	 * View type
	 * This is indicate ,what is the Controller which response is came
	 * ex: VIEW_TYPE_ITEM_ADD mean responce is come from item adding function(/item : POST) 
	 */
	public static String VIEW_MESSAGE_TYPE = "message_type";//this is the key
	public static String VIEW_TYPE_SIGN_IN = "sign_in";
	public static String VIEW_TYPE_SIGN_UP = "sign_up";
	public static String VIEW_TYPE_ITEM_ADD = "item_add";
	public static String VIEW_TYPE_BRANCH_ADD = "branch_add";
	public static String VIEW_TYPE_DASH_BOARD = "dash_board";
	
	/**
	 * Messages for Views
	 * Response values
	 */
	public static String VIEW_USER_ADD_SUCCESS = "Successfully create account";
	public static String VIEW_USER_ALREADY_REGISTERED = "UserId is allready registered.";
	public static String VIEW_USER_NOT_FOUND = "Please input correct user id and password";
	public static String VIEW_USER_LOGIN_SUCCESS = "Successfully Login";
	
	public static String VIEW_DB_ERROR = "Sorry serice is down.Please try again";
	public static String VIEW_REQUEST_PARAMETERS_ARE_NULL = "Please fill all fields correctly";
	
	public static String VIEW_ITEM_NAME_DUPLICATE= "Sorry you can t duplicate the item s name.";
	public static String VIEW_ITEM_ADD_SUCCESS= "Successfully add new item.";
	
	public static String VIEW_BRANCH_ADD_SUCCESS= "Successfully add new branch.";
	
	public static String VIEW_DASH_BOARD_ADD_SUCCESS= "Successfully add plates log.";
	
	/**
	 * URL Mapping
	 */
	public final static String URL_DASH_BOARD = "/dash_board";
	public final static String URL_BRANCH = "/branch";
	public final static String URL_ITEM = "/item";
	public final static String URL_ITEMS_PLATE = "/items_plate";
	public final static String URL_PLATE_LOG = "/plate_log";
	
}
