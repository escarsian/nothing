package book;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookDao {
	private static BookDao instance = new BookDao();
	
	public BookDao() {
		
	}
	
	public static BookDao getInstance() {
		return instance;
	}
	
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	private String sql;
	
	// 책 등록.(번호, 이름, 작가, 출판사)
	public boolean insert(BookVO book) {
		conn = Dao.getConnect();
		sql = "insert into book_manage(book_no, book_title, book_writer, book_publisher)" 
				+" values (book_seq.nextval, ?, ?, ?)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, book.getBookTitle());
			psmt.setString(2, book.getBookWriter());
			psmt.setString(3, book.getBookPublisher());
		
			int r = psmt.executeUpdate();
			if(r>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}
	
	// 책 목록(전체 목록)
	public List<BookVO> list(){
		conn = Dao.getConnect();
		sql = "select * from book_manage";
		List<BookVO> list = new ArrayList<>();
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				BookVO book = new BookVO();
				book.setBookNo(rs.getInt("book_no"));
				book.setBookTitle(rs.getString("book_title"));
				book.setBookWriter(rs.getString("book_writer"));
				book.setBookPublisher(rs.getString("book_publisher"));
				book.setBookBorrow(rs.getString("book_borrow"));
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return list;
	}
	
	//책 조회 (책 제목으로 조회)
	public List<BookVO> search(String title) {
		conn = Dao.getConnect();
		List<BookVO> list = new ArrayList<>();
		sql = "select * from book_manage where book_title LIKE '%'||?||'%'";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			rs = psmt.executeQuery();
			while(rs.next()) {
				BookVO book = new BookVO();
				book.setBookNo(rs.getInt("book_no"));
				book.setBookTitle(rs.getString("book_title"));
				book.setBookWriter(rs.getString("book_writer"));
				book.setBookPublisher(rs.getString("book_publisher"));
				book.setBookBorrow(rs.getString("book_borrow"));
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return list;
	}

	//책 대출 가능 여부..
	public BookVO borrow(int no){
		conn = Dao.getConnect();
		sql = "select * from book_manage where book_no = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);
			rs = psmt.executeQuery();
			if(rs.next()) {
				BookVO book = new BookVO();
				book.setBookNo(rs.getInt("book_no"));
				book.setBookTitle(rs.getString("book_title"));
//				book.setBookWriter(rs.getString("book_writer"));
//				book.setBookPublisher(rs.getString("book_publisher"));
				book.setBookBorrow(rs.getString("book_borrow"));
				return book;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return null;
	}
	
	// 책 대출
	public boolean borrow(BookVO book) {
		conn = Dao.getConnect();
		sql = "update book_manage set book_borrow = ? where book_no = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, book.getBookBorrow());
			psmt.setInt(2, book.getBookNo());
			int r = psmt.executeUpdate();
			if(r>0) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}
	
	//반납 가능 여부
	public BookVO collect(int no){
		conn = Dao.getConnect();
		sql = "select * from book_manage where book_no = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, no);
			rs = psmt.executeQuery();
			if(rs.next()) {
				BookVO book = new BookVO();
				book.setBookNo(rs.getInt("book_no"));
//				book.setBookTitle(rs.getString("book_title"));
//				book.setBookWriter(rs.getString("book_writer"));
//				book.setBookPublisher(rs.getString("book_publisher"));
				book.setBookBorrow(rs.getString("book_borrow"));
				return book;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return null;
	}
	
	//책 반납
	public boolean collect(BookVO book) {
		conn = Dao.getConnect();
		sql = "update book_manage set book_borrow = ? where book_no = ?";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, book.getBookBorrow());
			psmt.setInt(2, book.getBookNo());
			int r = psmt.executeUpdate();
			if(r>0) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}
	
	//대출 현황 리스트
	public List<BookVO> borrowList(){
		conn = Dao.getConnect();
		sql = "select b.borrow_no"
				+ "     , a.book_no"
				+ "     , a.book_title"
				+ "     , b.borrow_id"
				+ "     , to_char(b.borrow_day, 'YYYY/MM/DD hh:mm:ss') as borrow_day "
				+ "     , nvl(to_char(b.collect_day, 'YYYY/MM/DD hh:mm:ss'), '대출 중') as collect_day "
				+ " from book_manage a"
				+ " join borr_manage b"
				+ " on a.book_no = b.book_no"
				+ " order by collect_day desc";
		List<BookVO> list = new ArrayList<>();
		
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				BookVO borr = new BookVO();
				borr.setBorrowNo(rs.getInt("borrow_no"));
				borr.setBookNo(rs.getInt("book_no"));
				borr.setBorrowId(rs.getString("borrow_id"));
				borr.setBorrowDay(rs.getString("borrow_day"));
				borr.setCollectDay(rs.getString("collect_day"));
				list.add(borr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return list;
	}
	
	//대출하면 대출현황에 추가되는 함수
	public boolean borrowInsert(BookVO borr) {
		conn = Dao.getConnect();
		sql = "insert into borr_manage (borrow_no, book_no, borrow_id, borrow_day)" 
		    + " values (borr_seq.nextval ,? ,? , sysdate)";
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, borr.getBookNo());
			psmt.setString(2, borr.getBorrowId());
			
			int r= psmt.executeUpdate();
			if(r>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}
	
	//반납하면 대출현황에 반영되는 함수
	public boolean collectInsert(BookVO borr) {
		conn = Dao.getConnect();
		sql = "update borr_manage set collect_day = sysdate"
				+ " where book_no = ? and collect_day is null";
		
		try {
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, borr.getBookNo());
			
			int r= psmt.executeUpdate();
			if(r>0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}
	
	
	
	
//	//재고 관리.....
//	public boolean edit(BookVO book) {
//		conn = Dao.getConnect();
//		sql = "update book_manage set book_publisher=nvl(?, book_publisher) where book_no = ?";
//		try {
//			psmt = conn.prepareStatement(sql);
//			psmt.setString(1, book.getBookPublisher());
//			psmt.setInt(2, book.getBookNo());
//			int r = psmt.executeUpdate();
//			if(r>0) {
//				return true;
//			}
//		}catch(Exception e) {
//			e.printStackTrace();
//		}finally {
//			close();
//		}
//		return false;
//	}
	
	private void close() {
		try {
			if(conn != null) {
				conn.close();
			}
			if(psmt != null) {
				psmt.close();
			}
			if(rs != null) {
				rs.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
