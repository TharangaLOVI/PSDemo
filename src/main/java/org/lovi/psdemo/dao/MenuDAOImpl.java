package org.lovi.psdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.lovi.psdemo.models.Menu;

public class MenuDAOImpl implements MenuDAO{

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ArrayList<Menu> findByUserRole(int roleId) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT m.menu_id,page_name,page_url "
				+ "FROM user_role_menu urm,menu m "
				+ "WHERE urm.menu_id = m.menu_id AND urm.role_id = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, roleId);
			
			ArrayList<Menu> arrayListMenu = new ArrayList<Menu>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Menu menu = new Menu();
				menu.setMenuId(rs.getInt(1));
				menu.setMenupageName(rs.getString(2));
				menu.setPageUrl(rs.getString(3));
				arrayListMenu.add(menu);
				
			}
			rs.close();
			ps.close();
			return arrayListMenu;
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
