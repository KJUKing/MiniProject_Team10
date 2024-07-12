package dao;

import java.util.List;

import util.JDBCUtil;
import vo.SeatVo;

public class SeatDao {
	private static SeatDao instance;

	private SeatDao() {

	}

	public static SeatDao getInstance() {
		if (instance == null) {
			instance = new SeatDao();
		}
		return instance;
	}
	JDBCUtil jdbc = JDBCUtil.getInstance();



	public void seat(List<Object> param) {
		String sql = " UPDATE SEAT\r\n" + 
				" SET STATUS = 'Y'\r\n" + 
				" WHERE ROW_NUM = ? "
				+ " AND SEAT_NUM = ? ";
		
		jdbc.update(sql, param);
	}

	public void seat1(List<Object> param) {
		String sql = " UPDATE SEAT\r\n" + 
				" SET STATUS = 'Y'\r\n" + 
				" WHERE (ROW_NUM = ? AND SEAT_NUM = ?)\r\n" + 
				" OR    (ROW_NUM = ? AND SEAT_NUM = ?)";
		
		jdbc.update(sql, param);
		
	}

	public List<SeatVo> seatList(List<Object> param1) {
		String sql = " SELECT ROW_NUM, SEAT_NUM, STATUS,FK_THE_CODE1\r\n" + 
				" FROM SEAT\r\n" + 
				" WHERE FK_THE_CODE1 = ?";
		return jdbc.selectList(sql,param1,SeatVo.class);
	}

	public SeatVo seatInfo(List<Object> param) {
		String sql = "SELECT SEAT_CODE\r\n" + 
				"FROM SEAT\r\n" + 
				"WHERE ROW_NUM = ? \r\n" + 
				"AND SEAT_NUM = ? \r\n" + 
				"AND STATUS = 'Y'\r\n" + 
				"AND FK_THE_CODE1 = ?";
		return jdbc.selectOne(sql, param, SeatVo.class);
	}

	public List<SeatVo> seatInfo1(List<Object> param) {
		String sql = "SELECT *\r\n" + 
				"FROM SEAT\r\n" + 
				"WHERE ((ROW_NUM = ? AND SEAT_NUM = ?)\r\n" + 
				"OR (ROW_NUM = ? AND SEAT_NUM = ?))\r\n" + 
				"AND STATUS = 'Y'\r\n" + 
				"AND FK_THE_CODE1 = ?";
		return jdbc.selectList(sql, param, SeatVo.class);
	}
}
