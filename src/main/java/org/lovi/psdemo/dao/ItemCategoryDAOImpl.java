package org.lovi.psdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.lovi.psdemo.models.ItemCategory;

public class ItemCategoryDAOImpl implements ItemCategoryDAO {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public ArrayList<ItemCategory> getCategories() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM psdemo.item_category";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ArrayList<ItemCategory> categories = new ArrayList<ItemCategory>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				ItemCategory category = new  ItemCategory();
				category.setCategoryId(rs.getInt(1));
				category.setCategoryName(rs.getString(2));
				categories.add(category);
			}
			rs.close();
			ps.close();
			return categories;
		} catch (SQLException e) {
			throw new Exception(e.getMessage().toString());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
