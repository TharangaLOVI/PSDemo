package org.lovi.psdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.lovi.psdemo.models.Item;
import org.lovi.psdemo.models.ItemCategory;
import org.lovi.psdemo.models.PlateLog;

public class ItemDAOImpl implements ItemDAO {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public ArrayList<Item> getItems() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT i.item_id,i.name,i.price,i.comment,ic.category_id,ic.category_name "
					+ "FROM item i,item_category ic "
					+ "WHERE i.category = ic.category_id";
		
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ArrayList<Item> items = new ArrayList<Item>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				Item item = new Item();
				item.setItemId(rs.getInt(1));
				item.setName(rs.getString(2));
				item.setPrice(rs.getDouble(3));
				item.setComment(rs.getString(4));
				
				ItemCategory category = new ItemCategory();
				category.setCategoryId(rs.getInt(5));
				category.setCategoryName(rs.getString(6));
				
				item.setItemCategory(category);
				
				items.add(item);
				
			}
			rs.close();
			ps.close();
			return items;
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

	@Override
	public void insertItem(Item item) throws Exception {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO item " +
				"(name,category,price,comment) VALUES (?, ?, ?, ?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, item.getName());
			ps.setInt(2, item.getItemCategory().getCategoryId());
			ps.setDouble(3, item.getPrice());
			ps.setString(4, item.getComment());
			
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
