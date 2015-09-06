package org.lovi.psdemo.dao;

import java.util.ArrayList;

import org.lovi.psdemo.models.PlateLog;

public interface PlateLogDAO {

	/**
	 * Insert new plate log
	 * @param plateLog
	 * @throws Exception
	 */
	public void insert(PlateLog plateLog) throws Exception;
}
