package mrak.test.json;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mrak.test.domain.Bar;
import mrak.test.domain.Foo;
import mrak.test.domain.FooBar;
import mrak.utils.json.JSONUtil;

import org.json.JSONObject;
import org.junit.Test;

public class SerializeTest {
	
	@Test
	public void testSerialize()
	{
		List<Foo> fooList = newTestFooList();
		List<Bar> barList = newTestBarList();
		List<Integer> intList = new ArrayList<>();
		String testStringValue = "test string value";
		FooBar foobar = new FooBar(123, testStringValue, fooList, barList, intList);
		
		JSONObject jsonFoobar = new JSONObject(foobar);

		FooBar restoredFoobar = JSONUtil.get(jsonFoobar, FooBar.class);
		assertTrue(restoredFoobar.equals(foobar));
	}
	
	/*
	public void serializeTest() throws Exception 
	{
		List<Foo> fooList = newTestFooList();
		List<Bar> barList = newTestBarList();
		List<Integer> intList = new ArrayList<>();
		FooBar foobar = new FooBar(123, "Test", fooList, barList, intList);
		
		JSONObject foobarJSON = new JSONObject(foobar);
		System.out.println("FooBar -> JSON");
		System.out.println(foobarJSON.toString());
		
//		JSONObject fooBarJSON = new JSONObject(foobar);
//		System.out.println(fooBarJSON.toString());
		
		String json = readJSONTestFile("test_json_1.txt");
		System.out.println(json);
		
		System.out.println("JSON -> FooBar");
		JSONObject jsonObject = new JSONObject(json);
		
		System.out.println(jsonObject.toString());
		
		FooBar converted = JSONUtil.get(jsonObject, FooBar.class);
		System.out.println(converted);
	}
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
	
	public static String readJSONTestFile(String fileName) throws Exception {
		 
		BufferedReader br = null;
		StringBuilder bu = new StringBuilder();
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
			File file = new File(url.getPath());
			
			String line;
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				bu.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return bu.toString();
 
	}
	
	private String getJSONTestFile(String fileName) throws Exception
	{
		
		URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
		File file = new File(url.getPath());
		FileReader fileReader = new FileReader(file);
		
		InputStream is = SerializeTest.class.getResourceAsStream(fileName);
		
		try(Scanner s = new Scanner(is))
		{
			s.useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		}
		catch (Exception e) {
			throw new Error(e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String test = "CamelCaseToSomethingElseFOOBARaaafFF_DDaassAAA";
		
		System.out.println(JSONUtil.toUnderscoreName_v1(test));
		System.out.println(JSONUtil.toUnderscoreName_v2(test));
        System.out.println(JSONUtil.toUnderscoreName(test));
		
       new SerializeTest().testSerialize();
	}
	
}
