package org.lovi.psdemo.dao;

import java.util.ArrayList;

import org.lovi.psdemo.models.Branch;


public interface BranchDAO {
	
	/**
	 * Get all branch
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Branch> getAllBranches() throws Exception;
	
	/**
	 * Insert new branch
	 * @param shop
	 * @throws Exception
	 */
	public void inserBranch(Branch shop) throws Exception;
	
	/**
	 * Find the branch by id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Branch findById(int id) throws Exception;

}
