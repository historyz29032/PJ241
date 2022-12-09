import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.FileNotFoundException;

@SuppressWarnings("serial")
//เป็น class JPanel ที่จะใช้แสดงผลบน JFrame หลัก สร้างโดยใช้ Windowbuilder
public class pnlGamePlay extends JPanel {

	// ทำการประกาศ Stack ขึ้นมา 3 ตัว 
	//(ในที่นี้ Stack แต่ล่ะตัวจะเป็นเหมือนแท่งไม้ ที่จะนำจานมาใส่)
	Stack tower1 = new Stack();
	Stack tower2 = new Stack();
	Stack tower3 = new Stack();
	
	//ประกาศตัวแปรที่จะใช้บอกว่าเรากำลังจะหยิบจาน ออกจากแท่งไม้ไหน
	boolean isSelectedTower1 = true;  //ค่าเริ่มต้นจะให้หยิบออกจาก แท่งไม้ที่ 1(แท่งฝั่งซ้ายสุด)
	boolean isSelectedTower2 = false; 
	boolean isSelectedTower3 = false; 
	
	Block carriedBlock = null; //ประกาศ object สำหรับใช้เก็บค่า จานที่จะหยิบออก
	
	boolean isGameOver; //เป็นตัวแปรที่จะใช้เก็บค่า T/F เพื่อบอกจบเกม

	JButton Restart_Button;
	JLabel count_move;
	int num_move = 0;
	
	public pnlGamePlay() { //กำหนด constructor ของ pnlGamePlay()
		
		addKeyListener(new ControlAdapter()); //เพิ่ม KeyListener ที่จะใช้จัดการกับ KeyEvent ต่างๆที่มาจาก คีย์บอร์ด เป็น class ControlAdapter() ที่เขียนต่างล่าง
		setFocusable(true);// set ให้หน้าจอนี้สามารถรับ focus จาก Event ต่างๆที่เกิดขึ้นได้
		
		//เริ่มต้นให้สร้างจาน 4 จาน 4 ขนาด และนำไปวางซ้อนกันในแท่งไม้แรก
		//การทำงานในที่นี้คือนำข้อมูลขนาดของจาน 4 จาน ไปเก็บลงใน stack แท่งไม้แรก
		tower1.push(new Block(90));
		tower1.push(new Block(70));
		tower1.push(new Block(50));
		tower1.push(new Block(30));		

		/*ส่วนแสดงจำนวนการ move ของผู้เล่น ณ ปัจจุบัน*/
		count_move = new JLabel();
		//count_move.setHorizontalAlignment(SwingConstants.CENTER); //set ให้ข้อความที่แสดงใน JLabel อยู่ตรงกลาง
		count_move.setText("Move : "+Integer.toString(num_move));	//set ข้อความที่จะแสดง(จะแสดงจำนวนครั้งการ Move ปัจจุบันที่เล่น)		    
		count_move.setForeground(Color.BLACK); //set สี Font    
		count_move.setFont(new Font("Comic Sans MS", Font.BOLD, 16)); //set ขนาดและรูปแบบของ Font
		// set ตำแหน่งและขนาดที่จะแสดงของ JLabel (ตำแหน่งตามแนวแกน x,ตำแหน่งตามแนวแกน y,ความกว้างของ JLabel,ความสูงของ JLabel)
		count_move.setBounds(400,0,100,20);
		add(count_move); //เพิ่ม JLabel นี้ไปที่หน้าจอ

	}
	
	@Override	
	//ทุกครั้งที่หน้าต่าง window มีการเปลี่ยนแปลง JVM จะเรียก paintComponent() โดนอัตโนมัติทันที เพื่อให้เกิดความถูกต้องในการวาด
	public void paintComponent(Graphics g) //method นี้จะถูกเรียกทุกครั้งที่มีการกดปุ่ม(ในที่นี้เหมือนเรียกใหม่ทุกครั้งที่เรียก repaint() ใน ControlAdapter)
	{ 
		 super.paintComponent(g); //เป็นการป้องกันการทำงานที่ผิดพลาดจากการวาดภาพ		 
			
		 //ส่วนกำหนดการวาดภาพแท่งไม้	 		 
		 g.setColor(Color.lightGray); //กำหนดสีของแท่งไม้ที่จะวาด
		 
		 //วาดแท่งไม้ที่ 1
		 g.fillRect(70,40,20,160);
		//วาดแท่งไม้ที่ 2
		 g.fillRect(230,40,20,160);
		//วาดแท่งไม้ที่ 3
		 g.fillRect(390,40,20,160);
		 
		 //ทำการวาดจานที่อยู่ในแต่ล่ะแท่งไม้ แต่ล่ะอัน(วาดตามข้อมูลที่เก็บใน Stack แต่ล่ะอัน)
		 tower1.drawBlock(g, 30);
		 tower2.drawBlock(g, 190);
		 tower3.drawBlock(g, 350);
		 
		 //ส่วนวาดจานที่จะหยิบออก
		 if(carriedBlock != null)  //เช็คกรณีที่มีการหยิบจาน
		 {
			 
			 int xPos = 0; //ประกาศตัวแปรที่จะใช้คำนวนค่า coordinate ของ X 
			 
			 if(isSelectedTower1)//กรณีหยิบจากแท่งไม้ 1
				 {
					 xPos = 30+(100-carriedBlock.getLength())/2;	
				 }
			 else if(isSelectedTower2)//กรณีหยิบจากแท่งไม้ 2
				 {
					 xPos = 190+(100-carriedBlock.getLength())/2;	
				 }
			 else if(isSelectedTower3)//กรณีหยิบจากแท่งไม้ 3
				 {
					 xPos = 350+(100-carriedBlock.getLength())/2;		
				 }
			 
			 g.fillRect(xPos,20,carriedBlock.getLength(),19); //ทำการวาดจานที่หยิบขึ้นมา
			 
		 }
		 //ส่วนวาดตัวบอก Selector
		 g.setColor(Color.red); //กำหนดสีเส้นที่จะวาดเป็น Selector
		 
		 if(isSelectedTower1) {  //กรณีหยิบจากแท่งไม้ 1
			//คำสั่ง drawRect จะวาดเส้นเป็นรูปสีเหลี่ยม(การใช้งานคล้ายกับ fillRect())
			 g.drawRect(1,20,158,190); 
		 }
		 else if(isSelectedTower2) { //กรณีหยิบจากแท่งไม้ 2
			 g.drawRect(161,20,158,190);	
		 }
		 else if(isSelectedTower3) { //กรณีหยิบจากแท่งไม้ 3
			 g.drawRect(321,20,158,190);	
		 }
		 
	} //จบ method paintComponent()
	
	private void checkMove() throws FileNotFoundException {  //method ที่เช็คว่าจบเกมแล้วหรือยัง
		
		if(tower2.count() == 4 || tower3.count() == 4)  //เช็คกรณีที่แท่งไม้ที่ 2 หรือ 3 มีจานวางครบกัยทั้ง 4 จาน
		{
			String name;
			while(true)
        	{
                name = JOptionPane.showInputDialog("Your name"); //ให้ผู้เล่นกรอกชื่อ
                if(name.equalsIgnoreCase("")) //กรณีผู้เล่นไม่กรอกอะไรมา
                {
                    JOptionPane.showMessageDialog(null, "Please enter your name"); //ให้วน loop บอกให้ผู้เล่นกรอกชื่อ
                }
                else
                {
                    break; //ถ้าผู้เล่นกรอกชื่อมาแล้วให้ทำการออกจาก loop และไปบันทึกข้อมูล
                }
        }
			JOptionPane.showMessageDialog(pnlGamePlay.this, "Congrat "+name+" Your move is "+num_move); //ให้แสดงข้อความว่า You win!
		}		
		
	}//จบ method checkMove()
	public class ControlAdapter extends KeyAdapter //ตั้งค่าตัวจัดการ KeyEvent
	{ 
		@Override
		public void keyPressed(KeyEvent e) { //เป็น method ที่ใช้ตรวจจับ Event ที่เกิดจากการกดปุ่ม
			
			int key = e.getKeyCode(); //ดึงค่า KeyCode ของ KeyEvent ที่เกิดขึ้นมาเพื่อที่จะใช้สำหรับเช็คว่า KeyEvent ที่เกิดขึ้นเป็น KeyEvent อะไร
			
			if(key == KeyEvent.VK_RIGHT) //กรณีที่กดปุ่มลูกศรทางขวา(ให้เลื่อน Selector ไปทางขวา)
			{ 
				if(isSelectedTower1)  //กรณีที่แต่เติมเลือกหยิบจากแท่งไม้แรกอยู่
					{ 
						isSelectedTower2 = true; //ให้เปลี่ยน Selector ปัจจุบันเป็นแท่งไม้ 2 แทน
						isSelectedTower1 = false;
					}
				else if(isSelectedTower2) //กรณีที่แต่เติมเลือกหยิบจากแท่งไม้ 2 อยู่
					{ 
						isSelectedTower3 = true; //ให้เปลี่ยน Selector ปัจจุบันเป็นแท่งไม้ 3 แทน
						isSelectedTower2 = false;
					}			
			} //จบเช็คกรณีปุ่มลูกศรทางขวา	
			
			if(key == KeyEvent.VK_LEFT) //กรณีที่กดปุ่มลูกศรทางซ้าย(ให้เลื่อน Selector ไปทางซ้าย)
			{
				if(isSelectedTower2) //กรณีที่แต่เติมเลือกหยิบจากแท่งไม้ 2 อยู่
					{ 
						isSelectedTower1 = true; //ให้เปลี่ยน Selector ปัจจุบันเป็นแท่งไม้แรกแทน
						isSelectedTower2 = false;
					}
				else if(isSelectedTower3) //กรณีที่แต่เติมเลือกหยิบจากแท่งไม้ 3 อยู่
					{
						isSelectedTower2 = true; //ให้เปลี่ยน Selector ปัจจุบันเป็นแท่งไม้ 2 แทน
						isSelectedTower3 = false;
					}
			}//จบเช็คกรณีปุ่มลูกศรทางซ้าย
			
			if(key == KeyEvent.VK_UP) //กรณีที่กดปุ่มลูกศรขึ้น(ให้ทำการหยิบจานขึ้น)
			{
				if(carriedBlock == null)// เช็คว่าไม่มีจานที่หยิบขึ้นอยู่ก่อน(การทำแบบนี้จะทำให้หยิบได้ทีล่ะจานเท่านั้น ถ้าจะหยิบใหม่ต้องวางก่อน)
				{
					if(isSelectedTower1) //กรณีหยิบจากแท่งไม้อันที่ 1
						{
							carriedBlock = tower1.pop(); //ให้ดึงเอาค่าออกจาก Stack แท่งไม้อันที่ 1
						}
					else if(isSelectedTower2) //กรณีหยิบจากแท่งไม้อันที่ 2
						{
							carriedBlock = tower2.pop(); //ให้ดึงเอาค่าออกจาก Stack แท่งไม้อันที่ 2
						}
					else if(isSelectedTower3) //กรณีหยิบจากแท่งไม้อันที่ 3
						{
							carriedBlock = tower3.pop(); //ให้ดึงเอาค่าออกจาก Stack แท่งไม้อันที่ 3
						}
				}				
			}//จบเช็คกรณีที่กดปุ่มลูกศรขึ้น
			
			if(key == KeyEvent.VK_DOWN) //กรณีที่กดปุ่มลูกศรลง(ให้ทำการวางจานลงในแท่งไม้ที่เลือก)
			{
				if(carriedBlock != null) //เช็คว่ามีจานที่ถูกหยิบขึ้นมาไหม
				{
					if(isSelectedTower1) //กรณีที่เลือกที่จะวางในแท่งไม้ที่ 1
						{
							if(tower1.peek() == null || tower1.peek().getLength()>carriedBlock.getLength()) //เช็คว่าจานที่จะใส่ลงไป ขนาดเล็กกว่าจานบนสุดของแท่งไม้นั้นหรือม่
								{
									tower1.push(carriedBlock); //ใส่จานที่หยิบมาลงในแท่งไม้(เป็นการเพิ่มค่าลง Stack)
									num_move += 1; //ทันทีที่วางจานลงจะเพิ่มจำนวนครั้งการ move
									count_move.setText("Move : "+Integer.toString(num_move));	//อัปเดตข้อความที่แสดงจำนวนครั้งการ move
									carriedBlock = null; //ลบภาพจานที่หยิบออกมาออก
								}						
						}
					else if(isSelectedTower2) //กรณีที่เลือกที่จะวางในแท่งไม้ที่ 2
						{
							if(tower2.peek() == null || tower2.peek().getLength()>carriedBlock.getLength()) //เช็คว่าจานที่จะใส่ลงไป ขนาดเล็กกว่าจานบนสุดของแท่งไม้นั้นหรือม่
								{
									tower2.push(carriedBlock);  //ใส่จานที่หยิบมาลงในแท่งไม้(เป็นการเพิ่มค่าลง Stack)
									num_move += 1; //ทันทีที่วางจานลงจะเพิ่มจำนวนครั้งการ move
									count_move.setText("Move : "+Integer.toString(num_move));	//อัปเดตข้อความที่แสดงจำนวนครั้งการ move
									carriedBlock = null; //ลบภาพจานที่หยิบออกมาออก
								}		
						}
					else if(isSelectedTower3) //กรณีที่เลือกที่จะวางในแท่งไม้ที่ 3
						{
							if(tower3.peek() == null || tower3.peek().getLength()>carriedBlock.getLength())  //เช็คว่าจานที่จะใส่ลงไป ขนาดเล็กกว่าจานบนสุดของแท่งไม้นั้นหรือม่
								{
									tower3.push(carriedBlock); //ใส่จานที่หยิบมาลงในแท่งไม้(เป็นการเพิ่มค่าลง Stack)
									num_move += 1; //ทันทีที่วางจานลงจะเพิ่มจำนวนครั้งการ move
									count_move.setText("Move : "+Integer.toString(num_move));	//อัปเดตข้อความที่แสดงจำนวนครั้งการ move
									carriedBlock = null; //ลบภาพจานที่หยิบออกมาออก
								}		
						}					
				}					
			}//จบเช็คกรณีที่กดปุ่มลูกศรลง
			
			repaint();//เป็นเหมือนการสั่งให้วาดภาพองค์ประกอบใหม่ทั้งหมด
			try {
				checkMove();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} //เช็คว่าชนะเกมหรือยัง
			
		} //จบการตั้งค่า keyPressed()
	} //จบ class ControlAdapter
}
