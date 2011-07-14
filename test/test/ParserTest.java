package test;

import java.io.File;
import java.util.ArrayList;

import org.kpi.campus.convert.parser.Convert;
import org.kpi.campus.convert.parser.Database;
import org.kpi.campus.convert.parser.Parser;


public class ParserTest {

	public static void main(String[] args) throws Exception  {

		File task = new File("test/res/task.xml");

		Parser p = new Parser(Parser.class.
				getResource("/res/schema.xsd"));

		ArrayList<Database> dbs = null;
		dbs = p.parseTask(task);
		print(dbs);

	}

	public static void print(ArrayList<Database> dbs) {
		for (Database db : dbs) {
			System.out.println(db.getCsFrom());
			System.out.println(db.getCsTo());
			System.out.println(db.getDbTypeFrom() + "  =>  " + db.getDbTypeTo());
	
			for (int i = 0; i < db.getConvertsNumber(); i++) {
				Convert convert = db.getConvert(i);
				System.out.println("\tConvert");
				System.out.println("\t"+convert.getTableNameFrom() + "  =>  "
						+ convert.getTableNameTo());
	
				for (int j = 0; j < convert.getColumnsNumber(); j++) {
					System.out.println("\t\t" + convert.getColumnFrom(j).getName()
							+ " " + convert.getColumnFrom(j).getModifier()
							+ " " + convert.getColumnFrom(j).getType()
							+ "  =>  " + convert.getColumnTo(j).getName()
							+ " " + convert.getColumnTo(j).getModifier()
							+ " " + convert.getColumnTo(j).getType());
				}
			}
		}
	}
	
}
