//Author: Tyler Stratton
//Purpose: The purpose of this program is to allow the user to create and manipulate a heap, including displaying it, adding and removing values, and searching for a particular value.
package csc.pkg310.homework.pkg8;
import java.util.Scanner;

class HeapNode{
    private int key;
    
    public HeapNode(int k){ //constructor
        key = k;
    }
    
    public int getKey(){ //getter
        return key;
    }
    
    public void setKey(int k){ //setter
        key = k;
    }
}

class Heap{
    private HeapNode[] heapArray;
    private int maxSize;
    private int currentSize;
    
    public Heap(int max){ //constructor
        maxSize = max;
        currentSize = 0;
        heapArray = new HeapNode[maxSize];
    }
    
    public void buildHeap(){ //use bottom-up method to build heap
        Scanner in = new Scanner(System.in);
        int data;
        
        do{ //create the nonheap array
            System.out.println("Enter a value to add to the heap (-1 to exit)");
            data = in.nextInt();
            HeapNode newNode = new HeapNode(data);
            heapArray[currentSize] = newNode;
            currentSize++;
        }while(currentSize < maxSize && data != -1);
        
        for(int i = currentSize; i >= 0; i--){
            heapDown((i-1)/2);
        }
    }
    
    public boolean isEmpty(){
        return currentSize == 0;
    }
    
    public boolean insert(int key){
        if(currentSize == maxSize){ //heap is full
            return false;
        }
        HeapNode newNode = new HeapNode(key);
        heapArray[currentSize] = newNode;
        heapUp(currentSize++);
        return true;
    }
    
    public void heapUp(int index){
        int parent = (index-1)/2;
        HeapNode bottom = heapArray[index];
        while(index > 0 && heapArray[parent].getKey() < bottom.getKey()){
            heapArray[index] = heapArray[parent];
            index = parent;
            parent = (parent-1)/2;
        }
        heapArray[index] = bottom;
    }
    
    public HeapNode remove(){ //deletes the largest node
        HeapNode root = heapArray[0];
        heapArray[0] = heapArray[--currentSize];
        heapDown(0);
        return root;
    }
    
    public void heapDown(int index){
        int largerChild;
        HeapNode top = heapArray[index];
        while(index < currentSize/2){
            int leftChild = 2*index+1;
            int rightChild = leftChild+1;
            
            if(rightChild < currentSize && heapArray[leftChild].getKey() < heapArray[rightChild].getKey()){
                largerChild = rightChild;
            }else{
                largerChild = leftChild;
            }
            
            if(top.getKey() >- heapArray[largerChild].getKey()){
                break;
            }
            
            heapArray[index] = heapArray[largerChild];
            index = largerChild;
        }
        heapArray[index] = top;
    }
    
    public void printHeap(){
        
        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0;
        
        while(currentSize > 0){
            if(column == 0){
                for(int k = 0; k < nBlanks; k++){
                    System.out.print(' ');
                }
            }
            System.out.print(heapArray[j].getKey());
            if(++j == currentSize){
                break;
            }
            
            if(++column == itemsPerRow){
                nBlanks /= 2;
                itemsPerRow *= 2;
                column = 0;
                System.out.println();
            }else{
                for(int k = 0; k < nBlanks*2-2; k++){
                    System.out.print(' ');
                }
            }
        }
    }
    
    public int searchHeap(int key){ //Sequential search is NOT ideal, but since heaps are not sorted in a way that can be searched with binary/interpolation search, it is our only option
        for(int i = 0; i < currentSize; i++){
            if(heapArray[i].getKey() == key){
                return i;
            }
        }
        return -1; //didn't find it
    }
}

public class HeapProject {
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int choice;
        
        System.out.println("Enter the maximum size of the heap: ");
        int max = in.nextInt();
        Heap myHeap = new Heap(max);
        
        do{
            System.out.println("1. Build a heap.");
            System.out.println("2. Print the heap.");
            System.out.println("3. Insert a node.");
            System.out.println("4. Delete the largest node.");
            System.out.println("5. Search the heap for a key.");
            System.out.println("6. Exit.");
            choice = in.nextInt();
            
            switch(choice){
                case 1:
                    myHeap.buildHeap();
                    break;
                case 2:
                    myHeap.printHeap();
                    break;
                case 3:
                    System.out.println("Enter the value to insert: ");
                    int key = in.nextInt();
                    myHeap.insert(key);
                    break;
                case 4:
                    myHeap.remove();
                    break;
                case 5:
                    System.out.println("Enter the key to search for: ");
                    int val = in.nextInt();
                    myHeap.searchHeap(val);
                    break;
                case 6:
                    System.out.println("Goodbye");
                    break;
                default:
                    System.out.println("You have entered an invalid value. Please try again.");
            }
        }while(choice != 6);
    }
}
