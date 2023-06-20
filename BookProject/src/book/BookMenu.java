package book;

public enum BookMenu {
	ADD(1), LIST(2), SEAR(3), BORR(4), COLL(5), EXIT(6);
	
	private int val;
	
	BookMenu(int val){
		this.val = val;
	}
	
	boolean equal(int val) {
		return this.val == val;
	}
	
}
