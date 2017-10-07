package beetl.core.va;


import org.cheetah.BeetlParser;
import org.cheetah.core.ParserContext;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class VirtualAttributeTestNew
{

	@Test
	public void testVirtualAttribute() throws Exception
	{

		try {
			InputStream is = Thread.currentThread().getContextClassLoader().
					getResourceAsStream("beetl/template/va/va_simple_template.html");
			BeetlParser parser = new BeetlParser(is);

			ParserContext context = new ParserContext();
			context.bindVar("user", new User("somebody"));

            parser.setContext(context);

            String asdf = parser.parse();


			StringBuffer sbuf = new StringBuffer();
			System.out.println("ooooooooooo");

			System.out.println(asdf);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


//	@Test
//	public void testVirtualAttribute() throws Exception {
//
//		try {
//			InputStream is = Thread.currentThread().getContextClassLoader().
//					getResourceAsStream("beetl/template/va/va_simple_template.html");
//
//
//			BeetlParser par
//
//			BeetlParser parser = new BeetlParser(is);
//
//			ParserContext context = new ParserContext();
//			context.bindVar("user", new User("somebody"));
//
//			parser.setContext(context);
//
//			String asdf = parser.parse();
//
//
//			StringBuffer sbuf = new StringBuffer();
//
//			System.out.println(asdf);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

//	@Test
//	public void testVirtualClasAttribute() throws Exception
//	{

//		gt.registerVirtualAttributeClass(User.class, new VirtualClassAttribute() {
//
//			@Override
//			public String eval(Object o, String attributeName, Context ctx)
//			{
//				User user = (User) o;
//				if (user.getAge() < 10)
//				{
//					return "young";
//				}
//				else
//				{
//					return "old";
//				}
//
//			}
//
//		});
//
//		List list = User.getTestUsers();
//		User user = User.getTestUser();
//		Template t = gt.getTemplate("/va/class_virtual_template.html");
//		this.bindVar(t, "list", list, "user", user);
//		String str = t.render();
//		AssertJUnit.assertEquals(this.getFileContent("/va/class_virtual_expected.html"), str);
//
//		t = gt.getTemplate("/va/class_virtual_template.html");
//		this.bindVar(t, "list", list, "user", user);
//		str = t.render();
//		AssertJUnit.assertEquals(this.getFileContent("/va/class_virtual_expected.html"), str);

//	}

}
