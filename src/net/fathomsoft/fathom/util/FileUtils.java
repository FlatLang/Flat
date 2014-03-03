package net.fathomsoft.fathom.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * 
 * @author	Braden Steffaniak
 * @since	Jan 19, 2014 at 1:51:23 AM
 * @since	v
 * @version	Jan 19, 2014 at 1:51:23 AM
 * @version	v
 */
public class FileUtils
{
	public static String readFile(File file) throws IOException
	{
		StringBuilder  source = new StringBuilder();
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String         line   = reader.readLine();
		
		while (line != null)
		{
			source.append(line).append('\n');
			
			line = reader.readLine();
		}
		
		reader.close();
		
		return source.toString();
	}
	
	public static File writeFile(String fileName, String source) throws IOException
	{
		File parent = new File(System.getProperty("user.dir"));
		
		return writeFile(fileName, parent, source);
	}
	
	public static File writeFile(String fileName, File parentDir, String source) throws IOException
	{
		File file = new File(parentDir, fileName);
		
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		
		writer.print(source);
		
		writer.close();
		
		return file;
	}
	
	public static String removeFileExtension(String filename)
	{
		int lastIndex = 0;
		
		if ((lastIndex = filename.lastIndexOf('.')) < 0)
		{
			return filename;
		}
		
		return filename.substring(0,  lastIndex);
	}
}