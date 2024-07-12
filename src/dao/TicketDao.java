package dao;

import java.util.List;

import util.JDBCUtil;

public class TicketDao {
	private static TicketDao instance;

	private TicketDao() {

	}

	public static TicketDao getInstance() {
		if (instance == null) {
			instance = new TicketDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();

	public void tkInsert(List<Object> paramTk) {
		String sql = "INSERT INTO TICKET\r\n" + 
				" VALUES ((SELECT NVL(MAX(TICKET),0)+1 FROM TICKET), ? , ? , ? , ?)";
		
		jdbc.update(sql,paramTk);
		
	}
}
