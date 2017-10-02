package beetl.core.va;


import beetl.core.User;
import org.cheetah.BeetlParser;
import org.cheetah.core.ParserContext;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class VirtualAttributeTest
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

			System.out.println(asdf);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		gt.registerVirtualAttributeEval(new VirtualAttributeEval() {
//
//			@Override
//			public Object eval(Object o, String attributeName, Context ctx)
//			{
//				// TODO Auto-generated method stub
//				return attributeName;
//			}
//
//			@Override
//			public boolean isSupport(Class c, String attributeName)
//			{
//				if (attributeName.equals("hello"))
//					return true;
//				else
//					return false;
//			}
//
//		});

//		List list = User.getTestUsers();
//		Template t = gt.getTemplate("/va/va_virtual_template.html");
//		this.bindVar(t, "list", list, "user", new Object());
//		String str = t.render();
//		AssertJUnit.assertEquals(this.getFileContent("/va/va_virtual_expected.html"), str);
//
//		t = gt.getTemplate("/va/va_virtual_template.html");
//		this.bindVar(t, "list", list, "user", new Object());
//		str = t.render();
//		AssertJUnit.assertEquals(this.getFileContent("/va/va_virtual_expected.html"), str);

	}

	@Test
	public void testVirtualClasAttribute() throws Exception
	{

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

	}

}
