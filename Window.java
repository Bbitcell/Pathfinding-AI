package Project;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import java.util.Arrays;



import javax.swing.JPanel;

import javax.swing.Timer;

import javax.swing.event.MouseInputListener;



public class Window extends JPanel implements ActionListener, MouseInputListener {
	
	Timer timer = new Timer(50, this);

	
	Rectangle2D ai_block;
	Rectangle2D obstacle_block;
	Rectangle2D food_block;
	Rectangle2D [] obstacle_blocks = {};
	Graphics2D game_board;
	
	
	int ai_x ;
	int ai_y ;
	
	int food_x ;
	int food_y ;
	
	int height = 600;
	int width = 600;
	
	int grid_size = 20;
	

	
	boolean new_target = true;
	
	int steps_ai_to_food_x= 0;
	int steps_ai_to_food_y= 0;
	
	int mouse_x_position;
	int mouse_y_position;
	
	int food_x_position = 550;
	int food_y_position = 260;
	
	int ai_x_position = 300;
	int ai_y_position = 300;
	
	int velocity_x_position = 0;
	int velocity_y_position = 0;
	


	String chosen_direction = "LEFT";
	String food_direction = "LEFT";
	String previous_block = null;
	
	
	boolean create_red_block = false;

	
	
	
	int [] [] game_board_matrix = new int [ height / grid_size] [width /grid_size];


	public Window () 
	{

		timer.start();



		



		setBackground(Color.BLACK);
        addMouseListener(this);

        

        for (int i = 0 ; i < game_board_matrix.length ; i++ )
        {
        	for (int j = 0 ; j < game_board_matrix[i].length ; j++)
        		
        	{
        		game_board_matrix [i] [j]  = 0;
        	}
        }
        

		
	}
	

	 @Override
	  public void paintComponent(Graphics object) {
	    super.paintComponent(object);
	    game_board = (Graphics2D) object;
	    
	    ai_block = new Rectangle2D.Double(ai_x_position, ai_y_position, 20, 20);

	    game_board.setPaint(Color.blue);
	    game_board.fill(ai_block);
	    
	    
	    

	    


	    




	    food_block = new Rectangle2D.Double(food_x_position, food_y_position , 20, 20);
	    game_board.setPaint(Color.green);
	    game_board.fill(food_block);

	    

	    

	    


	    
	    
	    if (create_red_block == true) {
	    obstacle_block = new Rectangle2D.Double(mouse_x_position, mouse_y_position, 20, 20);
	    game_board.setPaint(Color.red);
	    game_board.fill(obstacle_block);;
	    
	    create_red_block = false;

	    	
	    obstacle_blocks = Arrays.copyOf(obstacle_blocks, obstacle_blocks.length + 1);
	    obstacle_blocks[obstacle_blocks.length-1] = obstacle_block;

	    }
	    
	    
	    
	    

		 ai_x =  (int)Math.round(ai_block.getX()/20);
		 ai_y =  (int)Math.round(ai_block.getY()/20);
		
		 food_x =  (int)Math.round(food_block.getX()/20);
		 food_y =  (int)Math.round(food_block.getY()/20);


		steps_ai_to_food_x = ai_x - food_x;
		steps_ai_to_food_y = ai_y - food_y;
		
	    
	    if (obstacle_blocks != null)
	    {
	    	for (Rectangle2D block : obstacle_blocks)
	    	{
	    	    game_board.setColor(Color.red);
	    	    game_board.fill(block);
	    	    
	    	    
	    		int block_x =  (int)Math.round(block.getX()/20);
	    		int block_y =  (int)Math.round(block.getY()/20);
	    		
	    	    
		        for (int i = 0 ; i < game_board_matrix.length ; i++ )
		        {
		        	for (int j = 0 ; j < game_board_matrix[i].length ; j++)
		        		
		        	{
		        		if (block_x == j && block_y == i)
		        		{
		        			

		        		if (game_board_matrix [i] [j] == 0 || game_board_matrix [i] [j] == 5)
		        		{
		        			

			        		
			        		game_board_matrix [i] [j]  = 3;
			        		game_board_matrix [i-1] [j]  = 3;
			        		game_board_matrix [i] [j-1]  = 3;
			        		game_board_matrix [i+-1] [j-1]  = 3;
			        		
			        		game_board_matrix [i+1] [j+1]  = 3;
			        		game_board_matrix [i+1] [j]  = 3;
			        		game_board_matrix [i] [j+1]  = 3;
		        		}
		        		
		        		}
		        		
	        



		        		
		        	
		        }
	    	}
	    	

	    	
	    }
	    }
	    
	    
	    


	  }
	 
	 
	 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		

		

		move_to_food();



		repaint();
		
	}
	

	
	
	public void move_to_food ()
	{
		//System.out.println(close_block);

		

		
		detect_obstacles ();

		//System.out.println("-----------------------------------");


			
		direction_chooser(2);	
		
		direction_chooser(1);
		
		direction_chooser(5);
			


			

		

		
		
		

	


		//System.out.println(chosen_direction);


			switch (chosen_direction) {
			
			
			
			case ("RIGHT"):

				velocity_x_position = 1;
				velocity_y_position = 0;

				break;
			case ("LEFT"):

				velocity_x_position = -1;
				velocity_y_position = 0;
				break;
			case ("DOWN"):

				velocity_x_position = 0;
				velocity_y_position = 1;
				break;
			case ("UP"):

				velocity_x_position = 0;
				velocity_y_position = -1;
				break;

			case ("STOP"):
				
				
				velocity_x_position = 0;
				velocity_y_position = 0;
				break;
						
			}
			
			ai_x_position += velocity_x_position;
			ai_y_position += velocity_y_position;
			


			//System.out.println(chosen_direction);
			//System.out.println(steps_ai_to_food_x + "\t " + steps_ai_to_food_y);
			//System.out.println(optimal_steps_x);
			//System.out.println();




	}
	
	




	public void direction_chooser(int direction)
	{
		
		
		if ( (game_board_matrix [ai_y] [ai_x+1] ==  direction|| game_board_matrix [ai_y] [ai_x+1] == 4) )
		{
			chosen_direction = "RIGHT";
		}
		
		else if ((game_board_matrix [ai_y] [ai_x-1] == direction || game_board_matrix [ai_y] [ai_x-1] == 4))
		{
			chosen_direction = "LEFT";
		}
		else if ((game_board_matrix [ai_y+1] [ai_x] == direction || game_board_matrix [ai_y+1] [ai_x] == 4))
		{
			chosen_direction = "DOWN";

		}
		else if ((game_board_matrix [ai_y-1] [ai_x] == direction || game_board_matrix [ai_y-1] [ai_x] == 4 ))
		{
			chosen_direction = "UP";

		}
		
	}


	public void detect_obstacles ()
	
	{
		steps_ai_to_food_x = ai_x - food_x;
		steps_ai_to_food_y = ai_y - food_y;
		


		
        for (int i = 0 ; i < game_board_matrix.length ; i++ )
        {
        	

        	for (int j = 0 ; j < game_board_matrix[i].length ; j++)
        		
        	{
    			
        		if (game_board_matrix [i] [j] == 3)
        		{
        			
        			
        			
        			for (int n = -1; n <= 1; n++)
        			{
        				for (int m = -1; m<= 1; m++)
        				{
        					
                			if (game_board_matrix [i+n] [j+m] == 0 || game_board_matrix [i+n] [j+m] == 5)
    	        			{
                				if(n != 0 && m !=0) {
    		        		game_board_matrix [i+n] [j+m]  = 1;	
    	        			}
    	        			}

            			}
        					
        				}
        			
        			
        			
        			


        			
        		}
        		
        		if (ai_x == j && ai_y == i)
        		{
        			game_board_matrix[i][j] = 2;
        		}
        		
        		if (food_x == j && food_y == i)
        		{
        			
        			game_board_matrix[i][j] = 4;
        		}
        		

        		if (game_board_matrix [i] [j] == 2 && new_target == true)
	        		{
        			
        			
        			if (0 > steps_ai_to_food_x)
        			{


        			for (int w = 0 ; w >= steps_ai_to_food_x; w--)
        			{

        					game_board_matrix [i] [j-w] = 5;
        			


        			}
        			}
        			
        			else if (0 < steps_ai_to_food_x)
        			{

        			
        			for (int w = 0 ; w <= steps_ai_to_food_x; w++)
        			{

        			game_board_matrix [i] [j-w] = 5;
        				


        			}
        			}

	        		}
        		if (game_board_matrix [i] [j] == 4 && new_target == true)
        		{
        			
        			if (0 > steps_ai_to_food_y )
        			{


        			for (int w = 0 ; w > steps_ai_to_food_y; w--)
        			{
        				

        			game_board_matrix [i+w] [j] = 5;
        				


        			}
        			}
        			
        			else if (0 < steps_ai_to_food_y)
        			{

        			
        			for (int w = 0 ; w < steps_ai_to_food_y; w++)
        			{
        				

        			game_board_matrix [i+w] [j] = 5;
        				


        			}
        			
        			
        			}
        			
        			

        			System.out.println(steps_ai_to_food_x + "\t " + steps_ai_to_food_y);
        		
        		}
        		
        		

    			

        		}
        
        		

        		

        	}

		new_target = false;
        	
        }
	


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int mouse = e.getButton();
		if (mouse == MouseEvent.BUTTON1) {


		
		

		create_red_block = true;
		
		
		
		mouse_x_position = e.getX();
		mouse_y_position = e.getY();

		
		}
		else if (mouse == MouseEvent.BUTTON3)
			
		{
			

		
		for (int i = 0 ; i < game_board_matrix.length ; i++ )
		{
			for (int j = 0 ; j < game_board_matrix[i].length ; j++)

			{
				game_board_matrix [i] [j]  = 0;
			}
		}
        
                new_target = true;
        
		
		food_x_position = e.getX();
		food_y_position = e.getY();
		
		
		}
		
		else if (mouse == MouseEvent.BUTTON2)
			
		{
			
	        for (int i = 0 ; i < game_board_matrix.length ; i++ )
	        {
	        	for (int j = 0 ; j < game_board_matrix[i].length ; j++)
	        		
	        	{
	        		System.out.print( game_board_matrix [i] [j] + "\t");
	        	}
	        	System.out.println();
	        }
        	System.out.println();
		}
		
		


		

		

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	
}
