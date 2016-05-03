/*
 * Name: Steven Lee
 * Student ID: 4643483
 * 
 */
package secureChannel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.Map.Entry;

public class FileIO {
	public static String readFile(String inputFileName)
	{
		String originalText=null;
		FileReader inputFile;
		try
		{
			if(!new File(inputFileName).exists())
			{
				System.err.println("file does not exist!!");
				System.exit(1);
			}
			inputFile = new FileReader(inputFileName);
			BufferedReader bufferReader;
			try
			{
				bufferReader = new BufferedReader(inputFile);
				originalText = bufferReader.readLine();
				inputFile.close();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return originalText;
	}
	
	
}
