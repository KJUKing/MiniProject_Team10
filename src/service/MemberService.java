package service;

import controller.MainController;
import dao.MemberDao;
import vo.MovieVo;
import vo.MemberVo;

import java.util.List;
import java.util.Map;

public class MemberService {

    private static MemberService instance;

    private MemberService() {

    }

    public static MemberService getInstance() {
        if (instance == null) {
            instance = new MemberService();
        }
        return instance;
    }
    MemberDao dao = MemberDao.getInstance();


    public boolean login(List<Object> param) {
        MemberVo member = dao.login(param);
        if (member ==null) return false;
        MainController.sessionStorage.put("member", member);
        return true;
    }

    public void signUp(List<Object> param) {
        dao.signUp(param);
    }

//    public MovieVo memberDetail(List<Object> param) { 굳이필요없음
//        return dao.memberDetail(param);
//    }

    public void memberUpdate(List<Object> param, int sel) {
        dao.memberUpdate(param, sel);
    }

    public void memberDelete(List<Object> param) {
        dao.memberDelete(param);
    }

	public MemberVo ageCheck(List<Object> param) {
		return dao.ageCheck(param);
		
	}

}
