package book;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class BookVO {

	public int getBookNo() {
		return bookNo;
	}

	public void setBookNo(int bookNo) {
		this.bookNo = bookNo;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookWriter() {
		return bookWriter;
	}

	public void setBookWriter(String bookWriter) {
		this.bookWriter = bookWriter;
	}

	public String getBookPublisher() {
		return bookPublisher;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public String getBookBorrow() {
		return bookBorrow;
	}

	public void setBookBorrow(String bookBorrow) {
		this.bookBorrow = bookBorrow;
	}

	public int getBorrowNo() {
		return borrowNo;
	}

	public void setBorrowNo(int borrowNo) {
		this.borrowNo = borrowNo;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getBorrowDay() {
		return borrowDay;
	}

	public void setBorrowDay(String borrowDay) {
		this.borrowDay = borrowDay;
	}

	public String getCollectDay() {
		return collectDay;
	}

	public void setCollectDay(String collectDay) {
		this.collectDay = collectDay;
	}

	private int bookNo;
	private String bookTitle;
	private String bookWriter;
	private String bookPublisher;
	private String bookBorrow;

	private int borrowNo;
	private String borrowId;
	private String borrowDay;
	private String collectDay;

	public BookVO(int bookNo, String bookTitle, String borrowId, String borrowDay, String collectDay) {
		this.bookNo = bookNo;
		this.bookTitle = bookTitle;
		this.borrowId = borrowId;
		this.borrowDay = borrowDay;
		this.collectDay = collectDay;
	}

	public BookVO(String bookTitle, String bookWriter, String bookPublisher) {
		this.bookTitle = bookTitle;
		this.bookWriter = bookWriter;
		this.bookPublisher = bookPublisher;
	}

	public BookVO() {
	}


//	public String briefInfo() {
//		return "도서번호: " + bookNo + ", 제목: " + bookTitle + ", 작가: " + bookWriter + ", 출판사: " + bookPublisher
//				;
//	}

	public String detailInfo() {
		String msg = "도서번호: " + bookNo + " 제목: " + bookTitle + " 작가: " + bookWriter + " 출판사: " + bookPublisher
				+ " 대출가능여부: ";
		if(bookBorrow.equals("y")) {
			msg += "가능";
		}else if(bookBorrow.equals("n")) {
			msg += "불가";
		}
		return msg;
	}

	public String borrowInfo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		Calendar cal = Calendar.getInstance();
		Date today = new Date();
		cal.setTime(today); // 캘린더에 오늘 날짜를 넣음
		cal.add(Calendar.DATE, 7); // 오늘 날짜에 7일 더함
		String msg = "도서번호: " + bookNo + " 제목: " + bookTitle + " 빌린 날짜: " + sdf.format(today) + " 반납 날짜: "
				+ sdf.format(cal.getTime());
		return msg;
	}
	
	public String brListInfo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		String msg = "도서번호: " + bookNo + " 제목: " + bookTitle + " 빌려간 ID: " + borrowId + " 빌린 날짜: " + borrowDay + " 반납 날짜: " + collectDay;
		return msg;
	}
	
}

