//package HW1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Collections;
import java.util.Hashtable;


public class traversal {
  
	int n,count=0, cost=0;
	Hashtable<String, Integer> cost_of_moves=new Hashtable<String, Integer>();
	String positionA,positionB;
	List<String> obstacles=new ArrayList<String>();
	boolean[][] visited;
	String[] coordinatesA,coordinatesB;

//***Constructor to set all the variables 	
	public traversal(ArrayList<String> input)
	{
		int i=0;

		n=Integer.parseInt(input.remove(i)); //size of board
		visited=new boolean[n][n];
		
		cost_of_moves.put("up", Integer.parseInt(input.remove(i)));
		cost_of_moves.put("down", Integer.parseInt(input.remove(i)));
		cost_of_moves.put("right", Integer.parseInt(input.remove(i)));
		cost_of_moves.put("left", Integer.parseInt(input.remove(i)));
				
		coordinatesA=input.remove(i).split("[ ]+"); 
		positionA="".concat("("+coordinatesA[0]+","+coordinatesA[1]+")");

		coordinatesB=input.remove(i).split("[ ]+"); 
		positionB="".concat("("+coordinatesB[0]+","+coordinatesB[1]+")");
		
		String[] coordinate_obs=new String[2];
		while (input.size()>0) {
			coordinate_obs=input.remove(i).split("[ ]+");
			obstacles.add("".concat("("+coordinate_obs[0]+","+coordinate_obs[1]+")"));
		}
		
	}

	//***checks whether Point is within the board
	boolean intervalContains(int low, int high, int n) {
	    return n >= low && n <= high;
	}
	//*** used to check whether Point A or Point B is not an obstacle
	boolean not_obstacle(int x, int y) {
		String xy="".concat("("+x+","+y+")");
		if (obstacles.contains(xy)) return false;
		return true;
	}
	
	//***check whether the position of POint A or B is valid taking boundary condition, obstacles and previously visited nodes into consideration
	boolean check_valid_position(int x_A,int y_A,int x_B,int y_B){
		if (intervalContains(1,n,x_A) && intervalContains(1,n,y_A) 
				&& intervalContains(1,n,x_B) && intervalContains(1,n,y_B))
			if (not_obstacle(x_A,y_A) && not_obstacle(x_B,y_B))
				if(visited[x_A-1][y_A-1]==false)
				return true;
		return false;
	}
	
	//solves the BFS for the problem and returns a List with path of A
	List<String> breadthFirstSoln() {
		Queue<String> qeA=new LinkedList<String>();
		Queue<String> qeB=new LinkedList<String>();
		boolean match=false,valid=false;
		String a,b,route="";
		int xA,yA,xB,yB,i=0,new_posA,new_posB;
		
		qeA.add(positionA);
		qeB.add(positionB);
		
		//checks whether input meets boundary condition
		if (!check_valid_position(Character.getNumericValue(positionA.charAt(1)),Character.getNumericValue(positionA.charAt(3)),
				Character.getNumericValue(positionB.charAt(1)),Character.getNumericValue(positionB.charAt(3)))){
				qeA.poll();}

		visited[Integer.parseInt(coordinatesA[0])-1][Integer.parseInt(coordinatesA[1])-1]=true;

		//checks if input is goal state	
		if(positionA.equals(positionB))
			match=true;

		//until goal state is reached or no solution possible
		while (!match && !qeA.isEmpty()) {

			a=qeA.poll();
			b=qeB.poll();

			String[] pathA=a.split("-");
			xA=Character.getNumericValue(pathA[0].charAt(1));
			yA=Character.getNumericValue(pathA[0].charAt(3));
			
			xB=Character.getNumericValue(b.charAt(1));
			yB=Character.getNumericValue(b.charAt(3));
			
			while (i<4) {
				//create nodes for all valid moves from current Node
				switch(++i) {
				case 1: new_posA=yA-1;  //A moves up
						new_posB=xB+1;  //B moves right
						if (check_valid_position(xA,new_posA,new_posB,yB)){
							positionA="".concat("("+xA+","+new_posA+")");
							positionB="".concat("("+new_posB+","+yB+")");
							valid=true;
							visited[xA-1][new_posA-1]=true;
						}
						break;
				case 2: new_posA=yA+1;  //A moves down
						new_posB=xB-1;  //B moves left
						if (check_valid_position(xA,new_posA,new_posB,yB)){ //also check if original position is outside box
							valid=true;
							positionA="".concat("("+xA+","+new_posA+")");
							positionB="".concat("("+new_posB+","+yB+")");
							visited[xA-1][new_posA-1]=true;
						}
						break;
				case 3: new_posA=xA+1;  //A moves right
						new_posB=yB+1;  //B moves up
						if (check_valid_position(new_posA,yA,xB,new_posB)){ //also check if original position is outside box
							valid=true;
							positionA="".concat("("+new_posA+","+yA+")");
							positionB="".concat("("+xB+","+new_posB+")");
							visited[new_posA-1][yA-1]=true;
						}
						break;
				case 4: new_posA=xA-1;  //A moves left
						new_posB=yB-1;  //B moves down
						if (check_valid_position(new_posA,yA,xB,new_posB)){ //also check if original position is outside box
							valid=true;
							positionA="".concat("("+new_posA+","+yA+")");
							positionB="".concat("("+xB+","+new_posB+")");
							visited[new_posA-1][yA-1]=true;
						}
						break;
				}
				if (valid){
					//check for goal state
					if (positionA.equals(positionB)){
						route=positionA.concat("-"+a);
						match=true;
						break;
						}
					qeA.add(positionA.concat("-"+a));
					qeB.add(positionB);
					valid=false;
				}
			}i=0;
		}
		List<String> out= new ArrayList<String>();
		
		// no solution possible
		if (qeA.isEmpty()){out.add("-1"); return out;}
		else if (match)
		return createOutput(route,-1);
		
		out.add("-1"); return out;
	}

	
	//*** creates output to be printed in file
	List<String> createOutput(String route, int parameter){
		String[] output=route.split("-");
		Collections.reverse(Arrays.asList(output));
		String[] output_c=new String[output.length+1];
		if (parameter==-1)
			output_c[0]=Integer.toString(output.length-1);
		else
			output_c[0]=Integer.toString(parameter);
		System.arraycopy( output, 0, output_c, 1, output.length);
		return Arrays.asList(output_c);
		
	}
	
	
	//***checks for valid positions of UCS tree nodes for Point A and Point B
	boolean check_valid_position_UC(int x_A,int y_A,int x_B,int y_B){
		if (intervalContains(1,n,x_A) && intervalContains(1,n,y_A) 
				&& intervalContains(1,n,x_B) && intervalContains(1,n,y_B))
			if (not_obstacle(x_A,y_A) && not_obstacle(x_B,y_B))
				return true;
		return false;
	}
	
	
	//*** checks the open and closed Lists and pathCosts of the States from the rrot to decide the further course of action
	public void performAction(state child_Node, PriorityQueue<state> open, LinkedList<state> closed){
		Iterator<state> iter=open.iterator();
		Iterator<state> iterC=closed.iterator();
		state x=new state();
		boolean openContains=false,closedContains=false;

		if (open.contains(child_Node)) openContains=true;
		if (closed.contains(child_Node)) closedContains=true;

		if (!openContains && !closedContains)	
			open.add(child_Node);
		
		else if(openContains){
			while (iter.hasNext()){
				x=iter.next();
				if (x.equals(child_Node))break;
			}
			if (child_Node.getPathCost() < x.getPathCost()){
					iter.remove();
					open.add(child_Node);
			}
		}	
		
		else if(closedContains){
			while (iterC.hasNext()){
				x=iterC.next();
				if (x.equals(child_Node))break;
			}
			if (child_Node.getPathCost() < x.getPathCost()){
				iterC.remove();
				closed.add(child_Node);
			}			
		}
	}
	
	
	//*** 
	public List<String> UniformCost(){
		PriorityQueue<state> openNode= new PriorityQueue<state>();
		LinkedList<state> closed =new LinkedList<state>();
		state currNode=new state(),childState=new state();
		int i=0,new_posA,new_posB,new_cost=0;
		Point A,B,childPointA=new Point(),childPointB=new Point();
		boolean valid=false,equal=false;


		A=new Point(Integer.parseInt(coordinatesA[0]),Integer.parseInt(coordinatesA[1]));
		B=new Point(Integer.parseInt(coordinatesB[0]),Integer.parseInt(coordinatesB[1]));
		openNode.add(new state(A,B,0));
		
		if (!check_valid_position_UC(Character.getNumericValue(positionA.charAt(1)),Character.getNumericValue(positionA.charAt(3)),
				Character.getNumericValue(positionB.charAt(1)),Character.getNumericValue(positionB.charAt(3))))
			openNode.poll();
		
		while(!openNode.isEmpty()){
	
			currNode=openNode.poll();
			A=currNode.getPosA();
			B=currNode.getPosB();
			if (A.equals(B)) {equal=true;closed.add(currNode);break;}
			while(i<4){
				switch(++i) {
				case 1: new_posA=A.getY()-1;  //A moves up
						new_posB=B.getX()+1;  //B moves right
						new_cost=currNode.getPathCost() + (int)cost_of_moves.get("up");
						if (check_valid_position_UC(A.getX(),new_posA,new_posB,B.getY())){ //also check if original position is outside box
							valid=true;
							childPointA=new Point(A.getX(),new_posA);
							childPointB=new Point(new_posB,B.getY());
						}
						break;
				case 2: new_posA=A.getY()+1;  //A moves down
						new_posB=B.getX()-1;
						new_cost=currNode.getPathCost() + (int)cost_of_moves.get("down");
						if (check_valid_position_UC(A.getX(),new_posA,new_posB,B.getY())){ //also check if original position is outside box
							valid=true;
							childPointA=new Point(A.getX(),new_posA);
							childPointB=new Point(new_posB,B.getY());
						}
						break;
				case 3: new_posA=A.getX()+1;  //A moves right
						new_posB=B.getY()+1;
						new_cost=currNode.getPathCost() + (int)cost_of_moves.get("right");
						if (check_valid_position_UC(new_posA,A.getY(),B.getX(),new_posB)){ //also check if original position is outside box
							valid=true;
							childPointA=new Point(new_posA,A.getY());
							childPointB=new Point(B.getX(),new_posB);
						}
						break;
				case 4: new_posA=A.getX()-1;  //A moves left
						new_posB=B.getY()-1;
						new_cost=currNode.getPathCost() + (int)cost_of_moves.get("left");
						if (check_valid_position_UC(new_posA,A.getY(),B.getX(),new_posB)){ //also check if original position is outside box
							valid=true;
							childPointA=new Point(new_posA,A.getY());
							childPointB=new Point(B.getX(),new_posB);
						}
						break;
				}
				
				if (valid){
					childState=new state(childPointA,childPointB,new_cost);
					childState.concatPath(currNode.getPath());
					performAction(childState,openNode,closed);

				}
			}i=0;
			closed.add(currNode);
		}

		if (equal){
			return createOutput(currNode.getPath(),currNode.getPathCost());
		}
		List<String> out= new ArrayList<String>();
		if (!openNode.isEmpty()){
			out.add("-1"); return out;
		}		
		out.add("-1"); return out;		
	}
	
}
