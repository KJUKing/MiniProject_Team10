package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import service.BookingService;
import service.MemberService;
import service.MovieService;
import service.ScheduleService;
import service.SeatService;
import service.TicketService;
import util.ScanUtil;
import view.View;
import vo.BookingVo;
import vo.MemberVo;
import vo.MovieVo;
import vo.ScheduleVo;
import vo.SeatVo;

public class MainController{
	static public Map<String, Object> sessionStorage = new HashMap<>();
	// 프리서비스랑 연결
	MovieService movieService = MovieService.getInstance();
	ScheduleService scheduleService = ScheduleService.getInstance();
	SeatService seatService = SeatService.getInstance();
	MemberService memberService = MemberService.getInstance();
	BookingService bookingService = BookingService.getInstance();
	TicketService ticketService = TicketService.getInstance();
	public static void main(String[] args) {
		new MainController().start(); // 시작
	}
	private void start() {
		View view = View.MAIN; // MAIN 1
		while(true) {
			switch (view) {
				case MAIN:
					view = main();
					break;
				case LOGIN:
					view = login();
					break;
				case SIGN_UP:
					view = signUp();
					break;
				case ADMIN:
					view = admin();
					break;
				case MOVIE_LIST:
					view = movieList();
					break;
				case MOVIE_DETAIL:
					view = movieDetail();
					break;
				case SCHEDULE:
					view = schedule();
					break;
				case SCHEDULE_DETAIL:
					view = scheduleDetail();
					break;
				case SEAT:
					view = seat();
					break;
				case BOOKING:
					view = booking();
					break;
				case TICKET_BUY:
					view = ticketBuy();
					break;
				case MEMBER_DETAIL:
					view = memberDetail();
					break;
				case MEMBER_UPDATE:
					view = memberUpdate();
					break;
				case MEMBER_DELETE:
					view = memberDelete();
					break;
			
					
				
				default:
					break;
			}
		}
	}
	



	   private View memberDelete() {
		      MemberVo member = (MemberVo) sessionStorage.get("member");
		      int memNo = member.getMem_no();
		      List<Object> param = new ArrayList<>();
		      param.add(memNo);
		      System.out.println("정말 탈퇴하시겠습니까?");
		      System.out.println("1. 네");
		      System.out.println("2. 아니오");
		      int sel = ScanUtil.menu();

		      if (sel == 1) {
		         memberService.memberDelete(param);
		         System.out.println("탈퇴하였습니다 감사합니다");
		         System.out.println();
		         return View.MAIN;
		      }if (sel == 2) {
		         System.out.println("마이페이지로 이동합니다");
		         System.out.println();
		         return View.MEMBER_DETAIL;
		      }
		      return View.MEMBER_DELETE;
		   }


		   private View memberUpdate() {
		      MemberVo member = (MemberVo) sessionStorage.get("member");
		      int memNo = member.getMem_no();

		      System.out.println("개인정보 변경 페이지입니다");
		      System.out.println("바꾸실 정보를 입력해주세요");
		      System.out.println();
		      System.out.println("1. 닉네임");
		      System.out.println("2. 비밀번호");
		      System.out.println("3. 전체");
		      System.out.println("4. 마이페이지로");
		      int sel = ScanUtil.menu();
		      if (sel == 4) {
		         return View.MEMBER_DETAIL;
		      }
		      List<Object> param = new ArrayList<>();
		      if (sel == 1 || sel == 3) {
		         String nickname = ScanUtil.nextLine("수정할 닉네임 : ");
		         param.add(nickname);
		      }if (sel == 2 || sel == 3) {
		         String password = ScanUtil.nextLine("수정할 비밀번호 : ");
		         param.add(password);
		      }
		      param.add(memNo);

		      memberService.memberUpdate(param, sel);
		      MainController.sessionStorage.clear();
		      sessionStorage.put("view", View.MEMBER_DETAIL);
		      System.out.println("개인정보가 수정되었습니다. 다시 로그인해주십시오");
		      System.out.println();
		      return View.MAIN;
		   }

		   private View memberDetail() {
		      System.out.println("마이 페이지입니다.");
		      MemberVo member = (MemberVo) sessionStorage.get("member");

		      String memName = member.getMem_name();
		      String memId = member.getMem_id();
		      String memPass = member.getMem_pass();
		      String memBir = member.getMem_bir();
//		      String memNick = member.getMem_nick();
		      int memNo = member.getMem_no();
		      System.out.println("내 정보\n이름 : "+memName+"\n닉네임 : "+"\nID : "+memId+"\nPW : "+memPass+"\n생년월일 : "+memBir);

		      System.out.println();
		      System.out.println("1. 개인정보 수정");
		      System.out.println("2. 예매 화면으로");
		      System.out.println("3. 로그아웃");
		      System.out.println("4. 회원탈퇴");
		      int sel = ScanUtil.menu();
		      switch (sel) {
		         case 1:
		            return View.MEMBER_UPDATE;
		         case 2:
		            return View.MOVIE_LIST;
		         case 3:
		            System.out.println("로그아웃되었습니다");
		            sessionStorage.clear();
		            return View.MAIN;
		         case 4:
		            return View.MEMBER_DELETE;
		         default:
		            return View.MEMBER_DETAIL;
		      }
		   }
	//티켓구매
	private View ticketBuy() {
		System.out.println("결제확인 창입니다");
		int selMovie = (int)sessionStorage.get("movieNo");
		List<Object> param = new ArrayList<Object>();
		param.add(selMovie);
		MovieVo movie = movieService.ticketSelect(param);
		String movieName = movie.getMn(); // 영화제목
		String genre = movie.getGn();     // 장르
		String partType = movie.getPart_type(); // 관람가
		String thName = movie.getTn();    // 상영관
		int price = movie.getPr(); // 가격
		System.out.println("영화제목 : "+movieName+" 상영관 : "+thName+"\n장르 : "+genre+" 관람가 : "+partType);
		System.out.println("결제금액 : "+price);
		System.out.println("================================");
		System.out.println("결제하실 방법을 선택하세요");
		System.out.println("1. 카드");
		System.out.println("2. 현금");
		System.out.println("3. 문화상품권");
		
		int memNo = (int)sessionStorage.get("memNo");
		int bkCode = (int)sessionStorage.get("bkCode");
		List<Object> paramTk = new ArrayList<Object>();
		paramTk.add(memNo);
		paramTk.add(bkCode);
		
		int sel = ScanUtil.menu();
		switch (sel) {
		case 1: paramTk.add(sel);
				paramTk.add(price);
				ticketService.tkInsert(paramTk);
			
			break;

		default:
			break;
		}
		
		
		
		return null;
	}
	//예매내역확인
	private View booking() {
		String id = (String)sessionStorage.get("id");
//		System.out.println(id);
		
//		System.out.println(mem);
//		int age = mem.getAge();
//		System.out.println(age);
		MovieVo movie = (MovieVo) sessionStorage.get("movie");
		String seat = (String) sessionStorage.get("seat");
		String movieName = movie.getMovie_name();
		sessionStorage.put("movieName", movieName); // 선택한 영화이름
		String genre = movie.getGenre();
		String rateType = movie.getRATETYPE();
		int rateCode = movie.getFk_rate();
		System.out.println(movie);
		List<Object> param = new ArrayList<Object>();
		param.add(id);
		param.add(rateCode);
		MemberVo mem = memberService.ageCheck(param);
		int grade = mem.getOk();
		System.out.println(grade);
		if(grade == 2) {
			System.out.println("나이제한 예매 불가능 합니다");
			return View.MOVIE_LIST;
		}
		int bookingSel = (int)sessionStorage.get("bookingSel");
		if(bookingSel==1) {
			String schCode = (String) sessionStorage.get("sch");
			String seatCode = (String)sessionStorage.get("seatCode");
			System.out.println(schCode);
			System.out.println(seatCode);
			List<Object>paramBk = new ArrayList<Object>();
			paramBk.add(schCode);
			paramBk.add(seatCode);
			bookingService.bookingUpdate(paramBk);
			BookingVo booking = (BookingVo)bookingService.bookingSelect(paramBk);
			int bkCode = booking.getBk_code();
			sessionStorage.put("bkCode", bkCode); // bkCode
		}
		if(bookingSel==2) {
			String schCode = (String) sessionStorage.get("sch");
			String seatCode1 = (String) sessionStorage.get("arr1");
			String seatCode2 = (String) sessionStorage.get("arr2");
			List<Object> paramBk = new ArrayList<Object>();
			paramBk.add(schCode);
			paramBk.add(seatCode1);
			paramBk.add(schCode);
			paramBk.add(seatCode2);
			bookingService.bookingUpdate1(paramBk);
		}
		System.out.println(mem);
		System.out.println("영화 제목 : "+movieName+"\n장르 : "+genre+" 관람가 : "+rateType);
		System.out.println(seat);
		System.out.println("1. 결제하기");
		System.out.println("2. 메인으로 돌아가기");
		int sel = ScanUtil.menu();
		switch (sel) {
		case 1: return View.TICKET_BUY;
		case 2: return View.MOVIE_LIST;
		
		default: return View.BOOKING;
		}
	}
	private View seat() {
	      String th = (String) sessionStorage.get("th");
	      List<Object> param1 = new ArrayList<Object>();
	      param1.add(th);
	      
	      List<SeatVo> seat = seatService.seatList(param1);
	      Set<String> uniqueRows = new HashSet<>(); // SET으로 중복제거목적
	      String[] arr = new String[50];
	      int cnt = 0;
	      int cnt1 = 0; // 현재 행의 인덱스를 추적하는 변수
	      String[] row = {"A", "B", "C", "D", "E"};

	      System.out.println("  1  2  3  4  5  6"); // 좌석 번호 출력

	      for (int i = 0; i < seat.size(); i++) { // A B C D E만 출력 로직
	         SeatVo seatVo = seat.get(i);
	         String rowNum = seatVo.getRow_num();
	         uniqueRows.add(rowNum); // 값담기

	         arr[i] = seatVo.getStatus();

	         if (cnt % 6 == 0) {
	            System.out.print(row[cnt1] + " "); // 행 이름 출력
	         }

	         if (arr[i].equals("Y")) {
	            System.out.print("■  ");
	         } else {
	            System.out.print("□  ");
	         }

	         cnt++;

	         if (cnt % 6 == 0) {
	            System.out.println(); // 다음 행으로 넘어가기
	            cnt1++; // 다음 행으로 이동
	         }
	      }
	      
		
		int sel = ScanUtil.nextInt("예매 인원을 선택해주세요(1~2)");
		List<Object> param = new ArrayList<Object>();
		sessionStorage.put("bookingSel", sel);
		if(sel == 1) {
			String row1 = ScanUtil.nextLine("좌석 열을 선택해주세요 A~E");
			int seatNum1 = ScanUtil.nextInt("좌석 번호를 선택해주세요 1~10");
			param.add(row1);
			param.add(seatNum1);
			System.out.println(row1+seatNum1);
			seatService.seat(param);
			param.add(th);
			sessionStorage.put("seat", row1+seatNum1);
			SeatVo seatInfo = seatService.seatInfo(param);
			String seatCode = (String)seatInfo.getSeat_code();
			sessionStorage.put("seatCode", seatCode);
			return View.BOOKING;
		}
		if(sel == 2) {
			String row1 = ScanUtil.nextLine("좌석 열을 선택해주세요 A~E");
			int seatNum1 = ScanUtil.nextInt("좌석 번호를 선택해주세요 1~10");
			param.add(row1);
			param.add(seatNum1);
			String row2 = ScanUtil.nextLine("좌석 열을 선택해주세요 A~E");
			int seatNum2 = ScanUtil.nextInt("좌석 번호를 선택해주세요 1~10");
			param.add(row2);
			param.add(seatNum2);
			seatService.seat1(param);
			param.add(th);
			System.out.println(row1+seatNum1+", "+row2+seatNum2);
			sessionStorage.put("seat", row1+seatNum1+", "+row2+seatNum2);
			List<SeatVo> seatInfo1 = seatService.seatInfo1(param);
			ArrayList<String> arr1 = new ArrayList<>();
			for (SeatVo seatVo : seatInfo1) {
				String seatCode = (String)seatVo.getSeat_code();

				arr1.add(seatCode);
			}
			String a1 = arr1.get(0);
			String a2 = arr1.get(1);
			sessionStorage.put("arr1", a1);
			sessionStorage.put("arr2", a2);
			return View.BOOKING;
		}
		return View.MOVIE_LIST;
	}
		private View scheduleDetail() {
			String schTime = (String)sessionStorage.get("schTime");
			String movieCode = (String)sessionStorage.get("movieCode");
			System.out.println(movieCode);
			
			List<Object> param = new ArrayList<Object>();
			param.add(schTime);
			param.add(movieCode);
			System.out.println(schTime);
			System.out.println(movieCode);
			
			ScheduleVo scheduleVo = scheduleService.scheduleDetail(param);
			System.out.println(scheduleVo);
			String th = scheduleVo.getFk_the_code();
			String sch = scheduleVo.getSch_code();
			sessionStorage.put("th", th);
			sessionStorage.put("sch", sch);
			
			return View.SEAT;
		}
	private View schedule() {
		int movieNo = (int)sessionStorage.get("movieNo");
		List<Object> param = new ArrayList<Object>();
		param.add(movieNo);
		List<ScheduleVo> schedule = scheduleService.schedule(param);
		for (ScheduleVo scheduleVo : schedule) {
			String partTime = scheduleVo.getPART_TIME();
			System.out.print(partTime+"\t");
		}
		String sel = ScanUtil.nextLine("예매 시간을 고르시오");
		sessionStorage.put("schTime", sel);
		System.out.println(sel);
		
		return View.SCHEDULE_DETAIL;
	}
	// 상세영화조회
	private View movieDetail() {
		int movieNo = (int)sessionStorage.get("movieNo"); // 선택한 상세영화번호
		List<Object> param = new ArrayList<Object>();
		param.add(movieNo);
		MovieVo movie = movieService.movieDetail(param);
		System.out.println(movie);
		String movieName = movie.getMovie_name();
		String genre = movie.getGenre();
		String rateType = movie.getRATETYPE();
		String movieCode = movie.getMovie_code();
		sessionStorage.put("movieCode", movieCode);
		System.out.println(movieCode);
		sessionStorage.put("movie", movie);
		System.out.println("영화제목 : "+movieName+"장르 : "+genre+" 관람가 : "+rateType);
		System.out.println("1. 영화스케쥴 조회");
		System.out.println("2. 영화리스트");
		int sel = ScanUtil.menu();
		switch (sel) {
		case 1: return View.SCHEDULE;
		case 2: return View.MOVIE_LIST;
		default: return View.MAIN;
		}
	}
	// 무비 리스트
	private View movieList() {
		List<MovieVo> movieList = movieService.movieList();
		for (MovieVo movieVo : movieList) {
			String movieNo = movieVo.getMono();
			String movieName = movieVo.getMn();
			String genre = movieVo.getGn();
			String thName = movieVo.getTn();
			String partType = movieVo.getPart_type();
			int price = movieVo.getPr();
			System.out.println(movieNo+"번  영화제목 : "+movieName+"장르 : "+genre+" 관람가 : "+partType+" 상영관 : "+thName);
		}
		
		int sel = ScanUtil.nextInt("상세조회할 무비번호를 고르시오");
		sessionStorage.put("movieNo", sel);
		return View.MOVIE_DETAIL;
	}
	// 영화관메인사이트
	private View admin() {
		MemberVo member = (MemberVo) sessionStorage.get("member");
		int memNo = member.getMem_no();
		sessionStorage.put("memNo",memNo);
		String memName = member.getMem_name();

		System.out.println("----------환영합니다! "+memName+"님----------");
		System.out.println();
		System.out.println("1. 예매화면으로");
		System.out.println("2. 마이페이지");
		System.out.println("3. 로그아웃");
		int sel = ScanUtil.menu();
		switch (sel) {
			case 1:
				return View.MOVIE_LIST;
			case 2:
				return View.MEMBER_DETAIL;
			case 3:
				return View.MAIN;
			default:
				return View.ADMIN;
		}
	}

	//회원가입
	private View signUp() {
		System.out.println("회원가입 화면입니다");
		System.out.println("개인 정보를 입력해주세요");
		String name = ScanUtil.nextLine("이름 : ");
		String id = ScanUtil.nextLine("ID : ");
		String pw = ScanUtil.nextLine("PW : ");
		String bir = ScanUtil.nextLine("생년월일 8자리 : ");
		String nickname = ScanUtil.nextLine("닉네임 : ");

		List<Object> param = new ArrayList<>();
		param.add(name);
		param.add(id);
		param.add(pw);
		param.add(bir);
		param.add(nickname);

		memberService.signUp(param);
		System.out.println("회원가입되었습니다 다시 로그인해주세요");
		return View.MAIN;
	}
	// 로그인
	private View login() {
		System.out.println("로그인 하십시오");
		String id = ScanUtil.nextLine("ID : ");
		String pw = ScanUtil.nextLine("PW : ");
		List<Object> param = new ArrayList<>();
		param.add(id);
		sessionStorage.put("id", id);
		param.add(pw);
		boolean login = memberService.login(param);
		if (!login) {
			System.out.println("1. 재로그인");
			System.out.println("2. 회원가입");
			System.out.println("3. 홈");
			return View.LOGIN;
		}
		View view =null;
		if (sessionStorage.containsKey("view")) {
				view = (View) sessionStorage.get("view");
				return view;
		}
		return View.ADMIN;
	}

	public View main() {
		System.out.println("환영합니다 자바 영화관입니다");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		
		int sel = ScanUtil.menu();
		// 궁금하면 nextInt 클릭
		switch (sel) {
		case 1:
			return View.LOGIN;
		case 2:
			return View.SIGN_UP;

		default:
			return View.MAIN;
		}
	}
}
