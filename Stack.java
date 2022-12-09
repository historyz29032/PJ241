import java.awt.*;
import java.awt.Graphics;

public class Stack {
	private Block headBlock; //ประกาศ object ที่มาเป็บข้อมูลส่วนหัวของ Stack

	public Block getHeadBlock() { // method ที่ใช้ return ส่วนหัวของ Stack
		return headBlock;
	}

	public void setHeadBlock(Block headBlock) { // method ที่ใช้ set ข้อมูลส่วนหัวของ Stack
		this.headBlock = headBlock;
	}

	//Add Block(กรณีที่เพิ่มค่าลงใน Stack)
	public void push(Block block) { //รับค่าที่จะเพิ่มลงใน Stack เข้ามา
		if (headBlock == null) { //กรณีที่ Stack ว่างอยู่
			headBlock = block; //ให้ค่าที่รับมาเป็นหัวของ Stack
		} 
		else { //กรณี่ Stack มีข้อมูลอยู่แล้ว
			Block currentBlock = headBlock; //ประกาศ object มารับค่าข้อมูลตำแหน่งแรกของ Stack
			while (currentBlock.getNext() != null) { //เป็นการไล่หาไปจนกว่าจะเจอข้อมูลตัวสุดท้ายของ Stack
				currentBlock = currentBlock.getNext(); //เหมือนเป็นการเลื่อน Node ไปเลื่อยๆ
			}
			currentBlock.setNext(block); //เอาข้อมูลใหม่ที่รับเข้ามาไปต่อที่ตำแหน่งสุดท้ายของ Stack
		}
	}

	//Remove Block(กรณีเอาค่าออกจาก Stack)
	public Block pop() {
		Block popBlock = null;  // ประกาศ object ใช้รับค่าที่จะนำออก
		if (count() == 1) { //กรณีใน Stack มีข้อมูลแค่ตัวเดียว
			popBlock = headBlock; //ให้ popBlock เก็บค่าของข้อมูลใน Stack ตัวแรก
			headBlock = null; //ลบข้อมูลออกจาก Stack
		} 
		else if (count() > 1) { //กรณีใน Stack มีข้อมูลมากกว่า 1
			Block currentBlock = headBlock; //ประกาศ object มารับค่าข้อมูลตำแหน่งแรกของ Stack
			for(int i =1; i < count()-1; i++) { 
				currentBlock = currentBlock.getNext(); //จะไล่วนไปจน currentBlock คือตำแหน่งข้อมูลก่อนตัวสุดท้ายของ Stack
			}
			popBlock = currentBlock.getNext();// popBlock จะเก็บค่าตำแหน่งสุดท้ายของ Stack
			currentBlock.setNext(null); //ตัดการเชื่อมต่อกับข้อมูลตัวสุดท้ายของ Stack ทิ้ง(เหมือนลบค่าออกจาก Stack)
		}
		return popBlock; //คืนค่าตำแหน่งข้อมูลตำแหน่งสุดท้ายของ Stack
	}
	
	//GetBlock(คืนค่าตำแหน่งข้อมูลตัวสุดท้ายจาก Stack แต่ไม่ได้เอาค่านั้นออกจาก Stack)
	public Block peek() {		
		if(count() > 0) { //กรณีที่ Stack ไม่ว่าง
			Block currentBlock = headBlock;  //ประกาศ object มารับค่าข้อมูลตำแหน่งแรกของ Stack
			while(currentBlock.getNext() != null) { 
				currentBlock = currentBlock.getNext();	 //จะไล่วนไปจน currentBlock คือตำแหน่งตัวสุดท้ายของ Stack
			}
			return currentBlock; //คืนค่าตำแหน่งตัวสุดท้ายออกมา
		}
		else { //ถ้า Stack ว่าง ให้คืนค่าเป็น null
			return null;
		}
	}

	//Count of Block in a Stack
	public int count() { // method นับจำนวนข้อมูลใน Stack
		int ctr = 0; //ให้เริ่มต้นค่าเป็น 0
		Block currentBlock = headBlock; //ประกาศ object มารับค่าข้อมูลตำแหน่งแรกของ Stack		
		while (currentBlock != null) { //ให้ทำการวนนับจำวนข้อมูลใน Stack ตั้งแต่ตำแหน่งแรกไปจนถึงตำแหน่งสุดท้าย
			ctr++;
			currentBlock = currentBlock.getNext(); //เหมือนเป็นการเลื่อน node ใน Stack ไป node ถัดไปเลื่อยๆ
		}
		return ctr; //จะ return ค่า count ที่นับเสร็จ
	}
	
	public void drawBlock(Graphics g, int x) 
	{ //method ที่ใช้สำหรับวาดจาน
		Block currentBlock = headBlock; //ประกาศ object มารับค่าข้อมูลตำแหน่งแรกของ Stack		
		
		//จะไล่วนไปจนถึงข้อมูลใน Stack ตัวสุดท้าย
		for(int i = 0; i < count(); i++) 
		{	
			
			int xPos = x + ( 100-currentBlock.getLength() )/2; //คำนวนหา coordinate ของ X ที่จะใช้วาดรูปจาน
			int yPos = 180 - (i*20); //คำนวนหา coordinate ของ Y ที่จะใช้วาดรูปจาน
			
			g.setColor(Color.blue); //set สีของจานที่จะวาด
			
			//คำสั่ง fillRect() จะทำการวาดรูปสีเหลี่ยมแบบทึบ(มีสีในรูป)ที่มี coordinate ตามที่คำนวนมา และ มีความกว้าง(currentBlock.getLength()) กับ ความสูง(19) ตามที่กำหนด
			g.fillRect(xPos,yPos,currentBlock.getLength(),19); 
			
			//จะเลื่อนไปที่ข้อมูล block ตำแหน่งถัดไปของ Stack เพื่อมาทำการวาดต่อ
			currentBlock = currentBlock.getNext();			
		}
		
	}
}
