import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

//เป็น class JFrame ของหน้า Menu เกม สร้างโดยใช้ Windowbuilder
public class Menu extends JFrame {

	//ประกาศ JLabel 2 ตัว สำหรับใช้ set ภาพ background พื้นหลัง กับ แสดง icon ชื่อเกม  
	JLabel background_Image,heading;
	//ประกาศ JButton 3 ตัว สำหรับเป็นปุ่ม play เกม , ปุ่มดู Leader scoreboard , ปุ่มดูหน้าอธิบายการเล่น
	JButton playbutton,scorebutton,exbutton;
	
	public static void main(String[] args) {		
		
			//ประกาศ object Menu และเป็นการเรียก constructor ของ Menu
			Menu frame = new Menu(); 
			//set ให้หน้าจอ Menu แสดงผล
			frame.setVisible(true);			
	}


	//ส่วนกำหนด constructor ของ Menu() 
	public Menu() 
		{
		setSize(500,300); //กำหนดขนาดหน้าจอ
		setLocationRelativeTo(null); //set ให้แสดงหน้าต่างกลางจอ
		setResizable(false); //set ให้ห้าม Resize หน้าจอ
		setDefaultCloseOperation(EXIT_ON_CLOSE); //กำหนดกรณีที่กดเครื่องหมาย X แล้วให้ทำการปิด Windows ลง			
		setTitle("Tower of Hanoi"); //set Title ของหน้าต่างแสดงผล	
		setLayout(null); //set Layout แบบ AbsoluteLayout (แบบกำหนดพิกัดเอง)	
        
		/*ส่วนจัดการปุ่มไปหน้าหน้าเล่นเกม*/
        playbutton = new JButton(); //สร้างปุ่ม play
        playbutton.addActionListener(new ActionListener() //สิ่งที่ให้ทำหลังกด ปุ่ม play
        {
            public void actionPerformed(ActionEvent e) 
            {            	
				try 
					{
						//ประกาศ object frmGame และเป็นการเรียก constructor ของ frmGame
						frmGame m = new frmGame();  
						m.setVisible(true); //set ให้หน้าจอ frmGame แสดงผล
						dispose(); //ปิดหน้าจอ Menu					
					} 
				catch (Exception e1) 
					{				    	
						e1.printStackTrace();
					}
            }
        });        
        playbutton.setBounds(135,90,100,70); //ตำแหน่งกับขนาด ปุ่ม play
        playbutton.setIcon(new ImageIcon(getClass().getResource("icon/play-button.png"))); //set icon ของปุ่ม;
		playbutton.setContentAreaFilled(false); //เอาพื้นหลังของปุ่มออก
		playbutton.setBorder(null);
        add(playbutton);  //เพิ่มปุ่ม  play ไปที่หน้าจอ      
        
        exbutton = new JButton(); //สร้างปุ่ม exit
        exbutton.addActionListener(new ActionListener()  //สิ่งที่ให้ทำหลังกด ปุ่ม exit
        {
            public void actionPerformed(ActionEvent e) 
            {            	
            	try 
				{
					dispose(); //ปิดหน้าจอ MainMenu					
				} 
			catch (Exception e1) 
				{					
					e1.printStackTrace();
				}
            }
        });        
        exbutton.setBounds(270,90,100,70); //ตำแหน่งกับขนาด ปุ่ม exit
		exbutton.setIcon(new ImageIcon(getClass().getResource("icon/logout.png"))); //set icon ของปุ่ม;
		exbutton.setContentAreaFilled(false); //เอาพื้นหลังของปุ่มออก
		exbutton.setBorder(null);
        add(exbutton); 
    }     
        
}
