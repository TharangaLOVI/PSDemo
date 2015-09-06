package org.lovi.psdemo.dao;

import java.util.List;

import org.lovi.psdemo.models.ItemsPlate;
import org.lovi.psdemo.models.Branch;

public interface ItemsPlateDAO {

	/**
	 * Insert new items plate
	 * @param itemsPlate
	 * @throws Exception
	 */
	public void insertItemsPlate(ItemsPlate itemsPlate) throws Exception; 
	
	/**
	 * Search by shop id
	 * @param shopId
	 * @return List<Shop>
	 * @throws Exception
	 */
	public List<ItemsPlate> searchByShopId(int shopId) throws Exception;
}
