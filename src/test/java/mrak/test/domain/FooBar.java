package mrak.test.domain;

import java.util.List;

import mrak.utils.json.JSONIgnore;
import mrak.utils.json.JSONName;

public class FooBar {
	
	private Integer number;
	private String string;
	
	@JSONName("foo_test_list")
	private List<Foo> fooList;
	private List<Bar> barList;
	
	private List<Integer> intList;
	
	@JSONIgnore
	private String fooBar;
	
	// constructors
	// ============
	
	public FooBar() {
	}
	
	public FooBar(
			Integer number, 
			String string, 
			List<Foo> fooList, 
			List<Bar> barList,
			List<Integer> intList) {
		
		this.number = number;
		this.string = string;
		this.fooList = fooList;
		this.barList = barList;
	}
	
	// methods
	// =======
	
	public String getString() {
		return string;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public List<Foo> getFooList() {
		return fooList;
	}
	
	public List<Bar> getBarList() {
		return barList;
	}
	
	public List<Integer> getIntList() {
		return intList;
	}
	
}
