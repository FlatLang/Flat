package net.fathomsoft.nova.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Utility methods for File operations, such as: reading, writing, and
 * manipulating file extensions.
 * 
 * @author	Braden Steffaniak
 * @since	v0.1 Jan 19, 2014 at 1:51:23 AM
 * @version	v0.2 Apr 5, 2014 at 3:32:39 PM
 */
public class FileUtils
{
	/**
	 * Read the contents of a File instance and return the contents in
	 * a String instance.
	 * 
	 * @param file The File to read from.
	 * @return The contents of the File.
	 * @throws IOException Thrown if there was an error reading from the
	 * 		file.
	 */
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
	
	/**
	 * Write the given 'source' text to a new file created in the current
	 * working directory.
	 * 
	 * @param fileName The name of the file to write the contents in.
	 * @param source The contents to write into the new file.
	 * @return The newly written File instance.
	 * @throws IOException Thrown if there is an error writing the file.
	 */
	public static File writeFile(String fileName, String source) throws IOException
	{
		File parent = new File(System.getProperty("user.dir"));
		
		return writeFile(fileName, parent, source);
	}
	
	/**
	 * Write the given 'source' text to the file at the specified location.
	 * 
	 * @param fileName The name of the file to write the contents in.
	 * @param parentDir The directory to write the file inside of.
	 * @param source The contents to write into the new file.
	 * @return The newly written File instance.
	 * @throws IOException Thrown if there is an error writing the file.
	 */
	public static File writeFile(String fileName, File parentDir, String source) throws IOException
	{
		File file = new File(parentDir, fileName);
		
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		
		writer.print(source);
		
		writer.close();
		
		return file;
	}
	
	/**
	 * Remove the extension from a file name.<br>
	 * <br>
	 * For example: An input of "file.txt" would return "file"
	 * 
	 * @param filename The name of the file to remove the extension of.
	 * @return The filename without an extension.
	 */
	public static String removeFileExtension(String filename)
	{
		int lastIndex = 0;
		
		if ((lastIndex = filename.lastIndexOf('.')) < 0)
		{
			return filename;
		}
		
		return filename.substring(0,  lastIndex);
	}

	public static boolean deleteDirectory(File directory)
	{
		if (directory.exists())
		{
			File[] files = directory.listFiles();
			
			if (files != null)
			{
				for (int i=0; i < files.length; i++)
				{
					if (files[i].isDirectory())
					{
						deleteDirectory(files[i]);
					}
					else
					{
						files[i].delete();
					}
				}
			}
		}
		
		return directory.delete();
	}
}