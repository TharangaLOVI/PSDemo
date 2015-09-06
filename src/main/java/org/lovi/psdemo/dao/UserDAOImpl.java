package org.lovi.psdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.lovi.psdemo.models.Menu;
import org.lovi.psdemo.models.User;
import org.lovi.psdemo.session.UserSession;
import org.springframework.beans.factory.annotation.Autowired;



public class UserDAOImpl implements UserDAO {
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insert(User user) throws Exception{
		String sql = "INSERT INTO user " +
				"(user_id,password,first_name,phone_number,role_id) VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getPhoneNumber());
			ps.setInt(5, user.getRoleId());
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

	@Override
	public User findByUserIdAndPassword(String userId,String password) throws Exception {
		// TODO Auto-generated method stub
		
		User user = null;
		
		String sql = "SELECT u.user_id,u.first_name,u.phone_number,u.role_id,ur.default_page_url "
					+ "FROM user u,user_role ur "
					+ "WHERE u.user_id = ? AND u.password = ? and u.role_id = ur.role_id";
		 
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				
				user = new User();
				user.setUserId(rs.getString(1));
				user.setFirstName(rs.getString(2));
				user.setPhoneNumber(rs.getString(3));
				user.setRoleId(rs.getInt(4));
				
				user.setArrayListMenu(findByUserRoleForMenu(rs.getInt(4)));
				user.setDefaultPageUrl(rs.getString(5));
			}
			
			rs.close();
			ps.close();
			return user;
			
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
	
	private ArrayList<Menu> findByUserRoleForMenu(int roleId) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT m.menu_id ,page_name,page_url "
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

	@Override
	public List<User> findByRoleId(int role) throws Exception {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<User>();
		
		String sql = "SELECT user_id,first_name,phone_number "
					+ "FROM user "
					+ "WHERE role_id = ?";
		 
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, role);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				User user = new User();
				user.setUserId(rs.getString(1));
				user.setFirstName(rs.getString(2));
				user.setPhoneNumber(rs.getString(3));
				
				users.add(user);
			}
			
			rs.close();
			ps.close();
			return users;
			
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
