
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")

//เป็น class JFrame หลักที่จะใช้แสดงผล สร้างโดยใช้ Windowbuilder
public class frmGame extends JFrame {
	
	public static void main(String[] args) {				
					frmGame frame = new frmGame(); //ประกาศ object_frmGame และเป็นการเรียก constructor ของ frmGame()
					frame.setVisible(true); //set ให้หน้าจอ frmGame แสดงผล
	}
	
//ส่วนกำหนด constructor ของ frmGame()
	public frmGame() { 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //กำหนดกรณีที่กดเครื่องหมาย X แล้วให้ทำการปิด Windows ลง	
		setSize(500,300); //กำหนดขนาดของหน้าจอ		
		setTitle("Tower of Hanoi"); //set Title ของหน้าต่างแสดงผล
		setLocationRelativeTo(null); //set ให้แสดงหน้าต่างกลางจอ
		setContentPane(new pnlGamePlay()); //กำหนดให้แสดงหน้าต่าง JPanel ของ pnlGamePlay() บนหน้าจอ Windows JFrame นี้
		setResizable(false);
		setLayout(null);

		JButton Home_Button = new JButton();
	    Home_Button.addActionListener(new ActionListener() //ตั้งค่าสิ่งที่จะให้ทำหลังกดปุ่ม
	    {
	    	public void actionPerformed(ActionEvent e) {
	    		try {		    			
					Menu m = new Menu();  //เรียกหน้าจอ MainMenu
					m.setVisible(true);	 //ให้แสดงหน้าจอ MainMenu			
					dispose(); //ปิดหน้าจอ MainGame()	
				} catch (Exception e1) {							
					e1.printStackTrace(); //แสดงข้อความ error กรณีเกิด error
				}		    	
	    	}
	    });
		Home_Button.setIcon(new ImageIcon(getClass().getResource("icon/home.png"))); //set icon ของปุ่ม           
		Home_Button.setContentAreaFilled(false); //เอาพื้นหลังของปุ่มออก
		Home_Button.setBorder(null);
	    Home_Button.setBounds(420, 210, 70, 50); // set ตำแหน่งและขนาดที่จะแสดงของ JButton
	    add(Home_Button);
	}

}
