package org.lovi.psdemo.dao;

import java.util.List;

import org.lovi.psdemo.models.User;

public interface UserDAO {

	/**
	 * Add new user
	 * @param customer
	 * @throws Exception
	 */
	public void insert(User customer)  throws Exception;
	
	/**
	 * Find user by userId and password
	 * @param userId
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public User findByUserIdAndPassword(String userId,String password)  throws Exception;
	
	/**
	 * Find by role id
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public List<User> findByRoleId(int role) throws Exception;
	
}
