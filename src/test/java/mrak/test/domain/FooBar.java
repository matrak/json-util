package mrak.test.domain;

import java.util.List;

import mrak.utils.json.Ignore;
import mrak.utils.json.Name;

public class FooBar {
	
	private Integer number;
	private String string;
	
	@Name("foo_test_list")
	private List<Foo> fooList;
	private List<Bar> barList;
	
	private List<Integer> intList;
	
	@Ignore
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

	// equals
	// ======

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FooBar other = (FooBar) obj;
		
		// FIXME is this case null list and empty list are equal
		if (barList == null) {
			if (other.barList != null && other.barList.size() > 0)
				return false;
		} else {
			if(other.barList != null && !other.barList.equals(barList))
				return false;
		}
		
		if (fooBar == null) {
			if (other.fooBar != null)
				return false;
		} else if (!fooBar.equals(other.fooBar))
			return false;
		if (fooList == null) {
			if (other.fooList != null)
				return false;
		} else if (!fooList.equals(other.fooList))
			return false;
		if (intList == null) {
			if (other.intList != null)
				return false;
		} else if (!intList.equals(other.intList))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (string == null) {
			if (other.string != null)
				return false;
		} else if (!string.equals(other.string))
			return false;
		return true;
	}
	
}
