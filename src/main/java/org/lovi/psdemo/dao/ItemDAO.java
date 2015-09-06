package org.lovi.psdemo.dao;

import java.util.ArrayList;

import org.lovi.psdemo.models.Item;

public interface ItemDAO {

	/**
	 * Get all items
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Item> getItems() throws Exception;
	
	/**
	 * Add new item
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public void insertItem(Item item) throws Exception; 
	
}
