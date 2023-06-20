package book;

import java.util.Scanner;

import user.UserDao;
import user.UserVO;

public class BookMain {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		int choice;
		BookProc proc = new BookProc();
		UserVO user = new UserVO();
		UserDao uDao = new UserDao();
		String loginId;
		// 로그인 기능. 아이디&비번.

		user = proc.loginCheck();

		while (true) {
			if (user.getUserRole().equals("0")) {
				while (true) {
					try {
						System.out.println("1.도서등록 2.도서목록 3.도서조회 4.대출 5.반납 6.대출현황 7.종료");
						System.out.print("선택> ");
						choice = scn.nextInt();
						scn.nextLine();

						switch (choice) {
						case 1: // 도서 등록
							proc.addBook();
							break;
						case 2: // 목록
							proc.listBook();
							break;
						case 3: // 조회
							proc.searchBook();
							break;
						case 4: // 대출 가능
							proc.borrowBook(user.getUserId());
							break;
						case 5: // 반납
							proc.collectBook(user.getUserId());
							break;
						case 6: // 대출현황
							proc.checkBook();
							break;
						case 7: // 종료
							proc.endBook();
						}
					} catch (ExitException e) {
						e.getMsg();
						break;
					}
				}

			} else {
				while (true) {
					try {
						System.out.println("1.도서목록 2.도서조회 3.대출 4.반납 5.종료");
						System.out.print("선택> ");
						choice = scn.nextInt();
						scn.nextLine();

						switch (choice) {
						case 1: // 목록
							proc.listBook();
							break;
						case 2: // 조회
							proc.searchBook();
							break;
						case 3: // 대출 가능
							proc.borrowBook(user.getUserId());
							break;
						case 4: // 반납
							proc.collectBook(user.getUserId());
							break;
						case 5: // 종료
							proc.endBook();
						}
					} catch (ExitException e) {
						e.getMsg();
						break;
					}
				}
			}
			return;
		}
	}
}

//		while (true) {
//			try {
//				System.out.println("1.도서등록 2.도서목록 3.도서조회 4.대출 5.반납 6.종료");
//				System.out.print("선택> ");
//				choice = scn.nextInt();
//				scn.nextLine();
//
//				switch (choice) {
//				case Menu.ADD: // 도서 등록
//					proc.addBook();
//					break;
//				case Menu.LIST: // 목록
//					proc.listBook();
//					break;
//				case Menu.SEAR: // 조회
//					proc.searchBook();
//					break;
//				case Menu.BORR: // 대출 가능
//					proc.borrowBook();
//					break;
//				case Menu.COLL: // 반납
//					proc.collectBook();
//					break;
//				case Menu.EXIT: // 종료
//					proc.endBook();
//				}
//			} catch (ExitException e) {
//				e.getMsg();
//				break;
//			}
//		}
