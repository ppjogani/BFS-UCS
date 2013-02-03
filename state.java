/**
 * 
 */
//package HW1;
//import java.util.Comparable;
/**
 * @author Parin
 *
 */

class Point {
  int x;
	int y;
	
	Point(){
		
	}
	
	Point(int X, int Y){
		this.x=X;
		this.y=Y;
	}
	
	int getX(){
		return x;
	}

	int getY(){
		return y;
	}
	
	void setX(int X){
		this.x=X;
	}
	
	void setY(int Y){
		this.y=Y;
	}
	
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Point))return false;
	    Point otherPoint = (Point)other;
		return (this.x==otherPoint.getX() && this.y==otherPoint.getY() )? true : false;
	}

}


public class state implements Comparable<state>{
	Point a;
	Point b;
	private int path_cost=0;
	String path="";
	
	state(){
		this.path_cost=0;
	}
	
	state(Point posA, Point posB, int path_costA){
		this.a=posA;
		this.b=posB;
		this.path_cost=path_costA;
		this.path="("+this.a.getX()+","+this.a.getY()+")";
	}
			
	int getPathCost() {
		return this.path_cost;
	}
	
	Point getPosA(){
		return this.a;
	}
	
	Point getPosB(){
		return this.b;
	}
	
	String getPath(){
		return this.path;
	}

	void concatPath(String parent){
		this.path="("+this.a.getX()+","+this.a.getY()+")-"+parent;
	}
	
	
//	@Override
	public int compareTo(state B){
		return (this.path_cost < B.path_cost) ? -1 : (this.path_cost > B.path_cost) ? 1 : 0;
	}
	
//	@Override
	public boolean equals(Object other){
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof state))return false;
	    state otherState = (state)other;
		return ((this.a).equals(otherState.a))? true : false;
	}
}
