package org.lovi.psdemo.dao;

import java.util.ArrayList;

import org.lovi.psdemo.models.Menu;


public interface MenuDAO {

	/**
	 * Get Menus for particular user
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Menu> findByUserRole(int roleId)  throws Exception;
}
