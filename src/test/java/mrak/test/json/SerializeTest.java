package mrak.test.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mrak.test.domain.Bar;
import mrak.test.domain.Foo;
import mrak.test.domain.FooBar;
import mrak.utils.json.Config;
import mrak.utils.json.JSONUtil;

import org.json.JSONObject;
import org.junit.Test;

public class SerializeTest {
	
	@Test
	public void testDeserialize()
	{
		List<Foo> fooList = newTestFooList();
		List<Bar> barList = newTestBarList();
		List<Integer> intList = new ArrayList<>();
		String testStringValue = "test string value";
		FooBar foobar = new FooBar(123, testStringValue, fooList, barList, intList);
		
		JSONObject jsonFoobar = new JSONObject(foobar);
		
		FooBar restoredFoobar = JSONUtil.get(jsonFoobar, FooBar.class, new Config(false, false));
		
		assertEquals(foobar.getNumber(), restoredFoobar.getNumber());
		assertEquals(foobar.getBarList(), restoredFoobar.getBarList());
		assertEquals(foobar.getString(), restoredFoobar.getString());
		
		// FooBar has @Name annotation for the foo list, so JSONUtil expects to get the list for 
		// "foo_test_list" name instead of fooList.
		assertNotEquals(foobar.getFooList(), restoredFoobar.getFooList());
	}
	
	@Test
	public void testDeserializeFromFile() throws Exception
	{
		String foobar = readJSONTestFile("test_foobar_json.txt");
		JSONObject jsonFoobar = new JSONObject(foobar);
		
		FooBar restoredFoobar = JSONUtil.get(jsonFoobar, FooBar.class);
		
		assertEquals(new Integer(123), restoredFoobar.getNumber());
		assertEquals(5, restoredFoobar.getFooList().size());
		assertEquals(5, restoredFoobar.getBarList().size());
		assertEquals("Test", restoredFoobar.getString());
	}
	
	/*
	 * =================
	 * Utilities methods
	 * =================
	 */
	
	private static List<Foo> newTestFooList() {
		List<Foo> fooList = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			fooList.add(new Foo("foo_" + i));
		}
		return fooList;
	}

	private static List<Bar> newTestBarList() {
		List<Bar> barList = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			barList.add(new Bar(i));
		}
		return barList;
	}
	
	private static String readJSONTestFile(String fileName) throws Exception
	{
		InputStream is = SerializeTest.class.getResourceAsStream("/" + fileName);
		try(Scanner s = new Scanner(is))
		{
			s.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		}
		catch (Exception e) {
			throw new Error(e);
		}
	}	
}
