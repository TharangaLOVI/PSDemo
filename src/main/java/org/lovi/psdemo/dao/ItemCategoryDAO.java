package org.lovi.psdemo.dao;

import java.util.ArrayList;

import org.lovi.psdemo.models.ItemCategory;

public interface ItemCategoryDAO {
	/**
	 * Get all categories of items
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ItemCategory> getCategories() throws Exception;
}
