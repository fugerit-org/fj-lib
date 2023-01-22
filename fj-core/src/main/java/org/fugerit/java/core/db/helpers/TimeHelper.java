package org.fugerit.java.core.db.helpers;

import java.sql.Date;
import java.sql.Timestamp;

public class TimeHelper {

	public static Date nowDate() {
		return new Date( System.currentTimeMillis() );
	}
	
	public static Timestamp nowTimestamp() {
		return new Timestamp( System.currentTimeMillis() );
	}
	
}
