/**
 * Package for modifiers.
 */
package org.kpi.campus.convert.modifiers;

/**
 * Interface definition for a modifier.
 * @author Igor Kokhanevych
 */
public interface Modifier {
	/**
	 * 
	 * @param obj The <code>Object</code> to be modified.
	 * @param args The arguments for a modifier.
	 * @return The modified object.
	 */
	Object modify(Object obj, String... args);
}
