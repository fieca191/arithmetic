/*
  Author: callum Fielding-Woodmass
*/

import java.util.*;
import java.lang.Math;

public class Arith{

   public static void printResult(char op, String perm, String inp){

	char[] p = perm.toCharArray();
	char[] input = inp.toCharArray();
	int loc = 0;
	String out = "";

	for(int i = 0; i < input.length; i++){

	    out += input[i];

	    if(input[i] == ' '){
		input[i] = p[loc];
		loc++;
		out += input[i];
	    }

	    if(input[i] == '+' || input[i] == '*'){
		out += " "; 
	    }
  
	}

	System.out.println(op + " " + out);
    }
    
     /*
      Recursively builds up an arraylist of all the permutations
      of + * that can exist
    */
    public ArrayList<String> makeBin(String soFar, int iter){

	ArrayList<String> combinations = new ArrayList<String>();
	
	if(iter == 0){
	    combinations.add(soFar);
	    return combinations;
	}else{
	    combinations.addAll(makeBin(soFar + "+", iter - 1));
	    combinations.addAll(makeBin(soFar + "*", iter - 1));
	    return combinations;
	}
    }

    public int leftToRight(int[] nums, String perm){

	char[] per = perm.toCharArray();
	int loc = 0;
	int out = nums[0];

	//System.out.println("perm: " + perm);

	for(int i = 0; i < nums.length - 1; i++){

	    if(per[loc] == '+'){
		out += nums[i + 1];
		loc++;
	    }else if(per[loc] == '*'){
		out *= nums[i + 1];
		loc++;
	    }

	    //System.out.println("out = " + out);
	}
	return out;	
    }

    public int bedmas(int[] nums, String perm){

	char[] per = perm.toCharArray();
	int total = nums[0];
	int temp = 0;
	int loc = 0;

	for(int i = 0; i < nums.length; i++){

	    if(per[loc] == '*'){
		temp = 1;
		temp *= nums[i];
		temp *= nums[i + 1];

		nums[i] = 0;
		nums[i + 1] = 0;
		loc++;
	    }
	}

	
	for(int i = 0; i < nums.length; i++){
	    total += nums[i];
	}

	total += temp;

	return total;
    }


    /*
      Main method
     */
    public static void main(String[] args){

        Scanner input = new Scanner(System.in);
        int[] nums;
        int number = 1;//number of numbers
	Double posib;
	int pos;
	Arith arith = new Arith();

	while(input.hasNextLine()){
	

	    //System.out.println("enter numbers:");

	    String in = input.nextLine();

	    for(int i = 0; i < in.length(); i++){
		if(in.charAt(i) == ' '){
		    number++;
		}
	    }

	    //calulate the number of posibiilities
	    posib = Math.pow(2, number - 1);
	    pos = posib.intValue();

	    //System.out.println("Enter target value and order of operations:");
	    String ops = input.nextLine();
        
        
	    String[] split = in.split(" ");//the list of numbers
	    String[] order = ops.toUpperCase().split(" ");//target & order of ops
	    int target = Integer.parseInt(order[0]);
	    char op = order[1].charAt(0);
	    nums = new int[number];//new array for the numbers

	    //System.out.println("your entered numbers:");

	    //filling out the array with the entered numbers
	    //also debugging - printing them out for now
	    for(int i = 0; i < split.length; i++){
		nums[i] = Integer.parseInt(split[i]);
		//System.out.print(nums[i] + " ");
	    }
	    //System.out.println();

	    ArrayList<String> list = new ArrayList<String>();
	    list = arith.makeBin("", number - 1);

	    // System.out.println("Permutations:");
	    // for(int i = 0; i < list.size(); i++){
	    // 	System.out.println(list.get(i));
	    // }

	    int result = 0;
	    boolean solved = false;

	

	    if(op == 'L'){
	    
		for(int i = 0; i < list.size(); i++){
		    result = arith.leftToRight(nums, list.get(i));

		    if(result == target){
			//System.out.println("Is this your number? " + result);
			printResult(op, list.get(i), in);
			solved = true;
			break;
		    }
		}
	    }else if(op == 'N'){
	    
		for(int i = 0; i < list.size(); i++){
		    result = arith.bedmas(nums, list.get(i));

		    if(result == target){
			//System.out.println("Is this your number? " + result);
			printResult(op, list.get(i), in);
			solved = true;
			break;
		    }
		}


	    }
	

	    if(solved == false){
		System.out.println("impossible");
	    }

	    //debugging statements
	    // System.out.println("target value: " + target);
	    // System.out.println("order of operations: " + op);
	    // System.out.println("posibilities: " + pos);
	}
    }
}
