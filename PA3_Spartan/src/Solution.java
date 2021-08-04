import java.io.*;
import java.util.*;

class Soldier {
	private String name;
	private long score;
	private int pos;
	
	public Soldier(String name, long score, int pos) {
		this.name = name;
		this.score = score;
		this.pos = pos; 
	}

	public long getScore() {
		return score;
	}
	
	public String getName() {
		return name;
	}

	public void addScore(long add) {
		score = score + add;
	}

	public int getPos() {
		return pos;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
}

class minHeap {
	
	private static Soldier[] heap; 
    private static int size; 
    private static int capacity; 
	
	public minHeap(int capacity) {
		
		this.capacity = capacity;
		this.setSize(0); 
		heap = new Soldier[this.capacity]; 
		
	}
	
	static int getParent(int index) {
		return (index-1)/2;
	}
	
	static int getLeft(int index) {
		return 2*index+1;
	}
	
	static int getRight(int index) {
		return 2*index+2;
	}
	
	static void swap(int fpos, int spos) {
		
		//swap their position values
		heap[fpos].setPos(spos);
		heap[spos].setPos(fpos);

		
		//swap in heap
		Soldier temp = heap[fpos]; 
		heap[fpos] = heap[spos];
		heap[spos] = temp;
	}
	
	static void minHeapify(int index) {
		
		if(getParent(index) >= 0 && heap[getParent(index)].getScore() > heap[index].getScore()) {
			
			swap(getParent(index), index);
            minHeapify(getParent(index));
		}
	}
	
	void deleteMin() throws Exception {
		if(getSize() == 0) {
			throw new Exception("heap is empty");
		}
		swap(0, getSize()-1);
        setSize(getSize() - 1);
        heapifyDown(0);
	}

	void insert(Soldier data) throws Exception {
		if(getSize() == capacity) {
			throw new Exception("heap is full");
		}
		heap[getSize()] = data;
		data.setPos(getSize());
		setSize(getSize() + 1);
		minHeapify(getSize()-1);
	}
	
	void heapifyDown(int index) {
		int minIndex = index;
		if(getLeft(index) < getSize() && heap[getLeft(index)].getScore() < heap[minIndex].getScore()) {
			minIndex = getLeft(index);
		}
		if(getRight(index) < getSize() && heap[getRight(index)].getScore() < heap[minIndex].getScore()) {
			minIndex = getRight(index);
		}
		if(minIndex != index) {
			swap(minIndex, index);
			heapifyDown(minIndex);
		}
	}
	
	void printHeap() {
		for(int i=0;i<getSize();i++){
	        for(int j=0;j<Math.pow(2,i)&&j+Math.pow(2,i)<=getSize();j++){
	            System.out.print(heap[j+(int)Math.pow(2,i)-1].getScore()+" ");
	        }
	        System.out.println();
	    }
	}
	
	void printHeap2() {
		for(int i=0;i<getSize();i++){
	        for(int j=0;j<Math.pow(2,i)&&j+Math.pow(2,i)<=getSize();j++){
	            System.out.print(heap[j+(int)Math.pow(2,i)-1].getName()+" ");
	        }
	        System.out.println();
	    }
	}
	long getMinScore() throws Exception {
		if(getSize() == 0) {
			throw new Exception("heap is empty");
		}
		return heap[0].getScore();
	}

	public int getSize() {
		return size;
	}
	
	private static void setSize(int i) {
		size = i;
	}
}

public class Solution {
	
	public static void main(String[] args) throws NumberFormatException, Exception {
		
		Scanner sc = new Scanner(new File("input07.txt"));
	    //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out, "ASCII"), 4096);
		
		int num = Integer.parseInt(sc.nextLine());
		minHeap heap = new minHeap(num);
		HashMap<String, Soldier> soldiers = new HashMap<String, Soldier>();

		for(int i = 0; i<num; i++) {
			String[] s = (sc.nextLine()).split(" ");
			Soldier x = new Soldier(s[0], Long.parseLong(s[1]), 0);
			heap.insert(x);
			soldiers.put(s[0], x);
		}
		
		int num2 = Integer.parseInt(sc.nextLine());
		for(int i = 0; i<num2; i++) {
			String[] s = (sc.nextLine()).split(" ");
	        if(s[0].equals("1")) {
	            soldiers.get(s[1]).addScore(Long.parseLong(s[2]));
	            heap.heapifyDown(soldiers.get(s[1]).getPos());
	        }
	        if(s[0].equals("2")) {
	        	while(heap.getSize() >= 0 && heap.getMinScore() < Long.parseLong(s[1])) {
	        		heap.deleteMin();
	        	}
	        	System.out.println(heap.getSize());
	        }
		}    
	}
}
