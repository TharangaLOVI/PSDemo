package org.lovi.psdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.lovi.psdemo.models.PlateLog;


public class PlateLogDAOImpl implements PlateLogDAO{

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insert(PlateLog plateLog) throws Exception {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO plate_log " +
				"(date,related_supervicer_id,related_driver_id,plate_id) VALUES (?, ?, ?, ?)";
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, plateLog.getDate());
			ps.setString(2, plateLog.getRelatedSupervicerId());
			ps.setString(3, plateLog.getRelatedDriverId());
			ps.setInt(4, plateLog.getItemsPlate().getPlateId());
			
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
