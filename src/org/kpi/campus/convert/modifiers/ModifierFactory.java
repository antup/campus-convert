/**
 * Package for modifiers.
 */
package org.kpi.campus.convert.modifiers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

/**
 * A ModifierFactory used to obtain the desired modifier.
 * @author Igor Kokhanevych
 */
public class ModifierFactory {
	
	private static String DefaultURL = "";
	
	/**
	 * Return the desired modifier finding it in the default directory "modifiers".
	 * @param name The name of the required modifier.
	 * @return Modifier.
	 * @throws ClassNotFoundException 
	 * @throws MalformedURLException 
	 */
	public static Modifier getModifier(String name) throws ClassNotFoundException {
		try {
			return getModifier(name, DefaultURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Return the desired modifier loaded from the given URL.
	 * @param name The name of the required modifier.
	 * @param url The String to parse as a URL.
	 * @return Modifier.
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 */
	public static Modifier getModifier(String name, String url) throws MalformedURLException, ClassNotFoundException {
		URL modURL = new URL(url);
		URLClassLoader loader = new URLClassLoader(new URL[] { modURL });
		Properties list = new Properties();
		try {	
			InputStreamReader in = new InputStreamReader(new URL(modURL,"modifiers.properties").openStream());
			list.load(in);
			Class<?> cl = loader.loadClass(list.getProperty(name));
			return (Modifier) cl.newInstance();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static {
		if (new File(System.getProperty("java.class.path")).isAbsolute())
			DefaultURL = "file:///"+System.getProperty("java.class.path")+"/../modifiers/";
		else
			DefaultURL = "file:///"+System.getProperty("user.dir")+
			"/"+System.getProperty("java.class.path")+"/../modifiers/";
	}
}
