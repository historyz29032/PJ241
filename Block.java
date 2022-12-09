public class Block {
	private int length; //ใช้เก็บความยาวของจาน
	private Block next; 
	
	//constructor ของ Block ใช้รับค่า length และกำหนดค่าเริ่มต้นให้ next
	public Block(int length) {
		//super();
		this.length = length; 
		this.next = null;
	}
	
	public int getLength() { //method ดึงค่า length
		return length;
	}
	public void setLength(int length) { //method set ค่า length
		this.length = length;
	}
	public Block getNext() { //method ดึง Node ข้อมูลถัดไปของ Stack
		return next;
	}
	public void setNext(Block next) { //method set Node ข้อมูลถัดไปของ Stack
		this.next = next;
	}

	
}
