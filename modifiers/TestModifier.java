import org.kpi.campus.convert.modifiers.Modifier;

public class TestModifier implements Modifier{

	@Override
	public Object modify(Object obj, String... args) {
		System.out.println("Ok");
		return null;
	}

}
