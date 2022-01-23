package Project;


import javax.swing.JFrame;
public class Main extends JFrame {

	
	
	public static void main(String[] args)  {
		
		JFrame frame = new JFrame();
		
		Window window = new Window();
		
		
		frame.add(window);
		frame.setSize(600, 600);
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	}
	
	
