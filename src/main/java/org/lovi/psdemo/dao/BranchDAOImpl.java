package org.lovi.psdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.lovi.psdemo.models.Branch;
import org.lovi.psdemo.models.User;


public class BranchDAOImpl implements BranchDAO {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ArrayList<Branch> getAllBranches() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM shop";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ArrayList<Branch> arrayListShop = new ArrayList<Branch>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				Branch shop = new Branch();
				shop.setBranchId(rs.getInt(1));
				shop.setBranchName(rs.getString(2));
				shop.setAddress(rs.getString(3));
				shop.setPhoneNumber(rs.getString(4));
				
				arrayListShop.add(shop);
				
			}
			rs.close();
			ps.close();
			return arrayListShop;
			
		} catch (SQLException e) {
			throw new Exception(e.getMessage().toString());
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	public Branch findById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inserBranch(Branch shop) throws Exception {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO shop " +
				"(shop_name,address,phone_number) VALUES (?, ?, ?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, shop.getBranchName());
			ps.setString(2, shop.getAddress());
			ps.setString(3, shop.getPhoneNumber());
			
			ps.executeUpdate();
			ps.close();
 
		} catch (SQLException e) {
			throw new Exception(e.getMessage().toString());
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

}
