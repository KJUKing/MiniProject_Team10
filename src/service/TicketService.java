package service;

import java.util.List;

import dao.TicketDao;

public class TicketService {
	private static TicketService instance;

	private TicketService() {

	}

	public static TicketService getInstance() {
		if (instance == null) {
			instance = new TicketService();
		}
		return instance;
	}
	TicketDao dao = TicketDao.getInstance();

	public void tkInsert(List<Object> paramTk) {
		dao.tkInsert(paramTk);
		
	}
}
