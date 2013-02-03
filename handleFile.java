//package HW1;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class handleFile {

  File file;
	PrintWriter out;

	handleFile() throws IOException{
		file = new File("output.txt");
		out = new PrintWriter(new FileWriter(file)); 
	}
	ArrayList<String> readFile(String file) throws IOException
	{
	    FileReader fileReader = new FileReader(file);
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    ArrayList<String> lines = new ArrayList<String>();
	    String line = null;
	    while ((line = bufferedReader.readLine()) != null) {
	        lines.add(line);
	    }
	    bufferedReader.close();
	    return lines;
	}
	
	void writeOutFile(List<String> fileOutData) throws IOException
	{
		int i=0;
		String[] temp;
		while (i<fileOutData.size()){
			temp=fileOutData.get(i).split(",");
			if (temp.length==1)
				out.println(temp[0]);
			else
		    out.println(temp[0].charAt(1)+" "+temp[1].charAt(0));
		    i++;		    
		}
		out.close();
	}
}
