package Depth_FS;

/*
* File: GraphMethod.java
* Author: Kolger Hajati
* Date: March 7, 2019
* Purpose: Using depth first search to handle input from text file.
*/

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class GraphMethod <T> {

	//Variables
	private int vert = 0;
	private StringBuilder valueOut;
	
	//Array, Map, Linked
	private Map <T, Integer> valueMap;
	private ArrayList <LinkedList <Integer>> valueList;
	private List<Integer> nextN;

	//Constructor
	GraphMethod() {
		valueList = new ArrayList<>();
		valueMap = new HashMap<>();
	}
    
	//Scanner to read text file, and split into ArrayList
	ArrayList<String[]> fileToken(String fileName) throws Exception {
		String row;
		File file = new File(fileName);
		//Scanner
		Scanner sc = new Scanner(file);
		sc.useDelimiter("\n");
		//ArrayList
		ArrayList<String[]> fileArray = new ArrayList<>();
		int index = 0;
		//While loop to handle input
		while (sc.hasNext()) {
			row=sc.next();
			String[] lineArray = row.split("\\s+");
			fileArray.add(index, lineArray);
			index++;
			
		}
		sc.close();
		return fileArray;
	}
    
	//Adds to either VertAdd or EdgeAdd for token created
    void tokenOrg(ArrayList<T[]> tokensArray) {
        for (T[] tokensLine : tokensArray) {
        	
        	System.out.println(Arrays.deepToString(tokensArray.toArray()));
            for (int i = 0; i < tokensLine.length; i++) {
            	VertAdd(tokensLine[i]);                   
                if (i != 0) {  
                	EdgeAdd(tokensLine[0], tokensLine[i]);
                }
            }
        }
    }
    
    //Vertex to valueMap and LinkedList for edge
    private void VertAdd(T className) {
        if (!valueMap.containsKey(className)) { 
        	valueMap.put(className, vert);
            LinkedList<Integer> edgeV = new LinkedList<>();
            valueList.add(vert, edgeV);
            vert++;
        }
    }
    //Edge for connecting two vertex
	private void EdgeAdd(T vertexFrom, T vertexTo) {
		int from = valueMap.get(vertexFrom);
		int to = valueMap.get(vertexTo);
		valueList.get(from).add(to);
	}
	//Grabs vertex index value from valueMap
	private String indexKey(int vertex) {
		for (T k : valueMap.keySet()) {
			if (valueMap.get(k).equals(vertex)) {
				return k.toString();
			}
		}
		return "";
	}
	//Method to handle Depth-first for every vertex to StringBuilder
	private void depthSearch (int v) throws CycleExp {
		valueOut.append(indexKey(v)).append(" ");
		for (Integer x : valueList.get(v)) {
			if (nextN.contains(x)) {
				throw new CycleExp();
			}
			nextN.add(x);
			depthSearch(x);
		}
	}
	//Output for StringBuilder
	String topOrdGeneration(T startVertex) throws NameExp, CycleExp {
		if (valueMap.get(startVertex) == null) {
			throw new NameExp();
		}

		valueOut = new StringBuilder();
		nextN = new ArrayList<>();
		depthSearch(valueMap.get(startVertex));

		return valueOut.toString();
	}
}

//Exception to handle Cycle
@SuppressWarnings("serial")
class CycleExp extends Exception {
	CycleExp() {
	}
}
//Exception to handle Invlid Name
@SuppressWarnings("serial")
class NameExp extends Exception {
	NameExp() {
	}
}
