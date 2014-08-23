package com.shwang.practice;

import java.io.IOException;

/**
 * Hello world!
 *
 */
import jline.console.*;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
			ConsoleReader reader = new ConsoleReader();
			String line = reader.readLine();
			System.out.println( "Hello World!  " + line); 
    }
}
