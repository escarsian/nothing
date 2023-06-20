package book;

import java.util.List;
import java.util.Scanner;

import user.UserDao;
import user.UserVO;

public class BookProc {

	private static String menuErrMsg = "잘못된 값을 입력했습니다.";
	private BookDao dao = BookDao.getInstance();
	private BookVO book;
	private Scanner scn = new Scanner(System.in);
	private UserDao uDao = new UserDao();
	private String loginId;
	private UserVO user;

	//로그인 //관리자와 일반을 차이두고 싶음...........
	
	public UserVO loginCheck() {
		while (true) {
			UserVO user = new UserVO();
			String id = promptString("아이디를 입력하세요");
			String pw = promptString("비밀번호를 입력하세요");
			user = uDao.login(id, pw);
			if (user.getUserId()!= null) {
				loginId = id;
				
				return user;
			}
			System.out.println("입력정보를 확인하세요.");
		}	
	}
	
	// 추가
	public void addBook() {

		String title = promptString("도서 제목을 입력하세요");
		String writer = promptString("작가 이름을 입력하세요");
		String publisher = promptString("출판사를 입력하세요");

		book = new BookVO(title, writer, publisher);

		if (dao.insert(book)) {
			prompt("저장되었습니다");
		} else {
			prompt("실패했습니다");
		}
	}

	// 목록.
	public void listBook() {
		List<BookVO> list = dao.list();
		if (list.size() == 0) {
			prompt("조회할 도서가 없습니다.");
			return;
		}
		for (BookVO book : list) {
			prompt(book.detailInfo());
		}
	}

	// 조회.
	public void searchBook() {
		String title = promptString("도서 제목을 입력하세요");
		List<BookVO> booklist = dao.search(title);
		if (booklist.size() == 0) {
			prompt("도서 정보가 존재하지 않습니다");
			return;
		}
		for(BookVO book : booklist) {
			prompt(book.detailInfo());
		}
	}

//	 대출.
	public void borrowBook(String userId) {
		int no = promptInt("대출할 도서번호를 입력하세요");
		book = dao.borrow(no);
		//도서 여부 확인
		if (book == null) { 
			prompt("도서 정보가 존재하지 않습니다");
		//book이 있을 때 대출 가능여부가 y이면  
		} else if(book.getBookBorrow().equals("y")){ 
			//n(대출불가상태)으로 바꿔주는거야.
			book.setBookBorrow("n"); 
			prompt(book.borrowInfo());
			dao.borrow(book);
			//+ 대출기록추가
			BookVO brr = new BookVO();
//			brr.setBookNo(no);
			brr.setBorrowId(userId);
			brr.setBookNo(no);
			dao.borrowInsert(brr);
			
		//대충 가능여부가 n(대출불가상태)일 때 대출불가 안내문.
		} else if(book.getBookBorrow().equals("n")) {
			prompt("이미 대출된 도서입니다");
		}
	}
	
	//반납
	public void collectBook(String userId) {
		int no = promptInt("반납할 도서번호를 입력하세요");
		book = dao.collect(no);
		if(book == null) {
			prompt("반납할 도서가 아닙니다");
		//반납할 도서가 맞으면 n을 다시 y로 바꿔줌.
		} else if(book.getBookBorrow().equals("n")) {
			book.setBookBorrow("y");
			dao.collect(book);
			prompt("반납 완료되었습니다");
			//반납기록추가
			BookVO brr = new BookVO();
			brr.setBookNo(no);
			brr.setBorrowId(userId);
			dao.collectInsert(brr);
		} else if(book.getBookBorrow().equals("y")) {
			prompt("반납할 도서가 아닙니다");
		}
			
	}
	//전체 대출 현황.
	public void checkBook() {
		List<BookVO> checkList = dao.borrowList();
		if(checkList.size() == 0) {
			prompt("대출된 도서가 없습니다");
			return;
		}for(BookVO list : checkList) {
			prompt(list.brListInfo());
		}
	}

	// 종료
	public void endBook() throws ExitException {
		throw new ExitException("프로그램을 종료합니다.");
	}
	

	//prompt 정리.
	private void prompt(String msg) {
		System.out.println(msg);
	}

	String promptString(String msg) {
		System.out.print(msg + "> ");
		return scn.nextLine();
	}

	private int promptInt(String msg) {
		int result = 0;
		while (true) {
			try {
				System.out.print(msg + "> ");
				result = scn.nextInt();
				break;
			} catch (Exception e) {
				System.out.println(menuErrMsg);
				scn.nextLine();
			}
		}
		return result;
	}

}
