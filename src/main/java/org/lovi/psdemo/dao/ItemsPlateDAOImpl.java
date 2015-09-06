package org.lovi.psdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.lovi.psdemo.models.Item;
import org.lovi.psdemo.models.ItemsPlate;
import org.lovi.psdemo.models.Branch;
import org.lovi.psdemo.models.User;

public class ItemsPlateDAOImpl implements ItemsPlateDAO {

private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public void insertItemsPlate(ItemsPlate itemsPlate) throws Exception {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO items_plate " +
				"(plate_name,shop_id) VALUES (?, ?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, itemsPlate.getPlateName());
			ps.setInt(2, itemsPlate.getBranch().getBranchId());
			
			int affectedRows = ps.executeUpdate();
			
			if(affectedRows == 0) throw new SQLException("No affected tow");
			
			else{
				ResultSet genaratedKeys = ps.getGeneratedKeys();
				if(genaratedKeys.next()){
					int insertedPlateId = genaratedKeys.getInt(1);
					
					sql = "INSERT INTO item_items_plate (item_id,plate_id,item_qty) "
							+ "VALUES (?, ? ,?)";
					
					ps = conn.prepareStatement(sql);
					
					for(Item item : itemsPlate.getItems()){
						ps.setInt(1, item.getItemId());
						ps.setInt(2, insertedPlateId);
						ps.setInt(3, item.getPlateItemQty());
						
						ps.executeUpdate();
					}
					
					
				}else{
					throw new SQLException("Unable create ItemsPlate id");
				}
			}
			
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
	public List<ItemsPlate> searchByShopId(int shopId) throws Exception {
		// TODO Auto-generated method stub
		
		List<ItemsPlate> itemsPlates = new ArrayList<ItemsPlate>();
		
		String sql = "SELECT ip.plate_id,ip.plate_name "
					+ "FROM items_plate ip , shop s "
					+ "WHERE ip.shop_id = s.shop_id AND ip.shop_id = ?";
		 
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, shopId);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				
				ItemsPlate itemsPlate = new ItemsPlate();
				itemsPlate.setPlateId(rs.getInt(1));
				itemsPlate.setPlateName(rs.getString(2));
				itemsPlate.setItems(getItemsForPlate(rs.getInt(1)));
				itemsPlates.add(itemsPlate);
			}
			
			rs.close();
			ps.close();
			return itemsPlates;
			
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
	
	private List<Item> getItemsForPlate(int plateId){
		
		List<Item> items = new ArrayList<Item>();
		
		String sql = "SELECT i.item_id,i.name,i.comment,iip.item_qty "
					+ "FROM item_items_plate iip,item i "
					+ "WHERE i.item_id = iip.item_id AND iip.plate_id = ?";
		 
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, plateId);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				
				Item item = new Item();
				item.setItemId(rs.getInt(1));
				item.setName(rs.getString(2));
				item.setComment(rs.getString(3));
				item.setPlateItemQty(rs.getInt(4));
				
				items.add(item);
			}
			
			rs.close();
			ps.close();
			return items;
			
		} catch (SQLException e) {
			return items;
		} 
	}

}
