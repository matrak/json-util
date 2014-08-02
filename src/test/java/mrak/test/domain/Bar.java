package mrak.test.domain;

public class Bar {
	
	private Integer bar;
	
	public Bar() {
	}
	
	public Bar(Integer i) {
		this.bar = i;
	}
	
	public Integer getBar() {
		return bar;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bar other = (Bar) obj;
		if (bar == null) {
			if (other.bar != null)
				return false;
		} else if (!bar.equals(other.bar))
			return false;
		return true;
	}
	
	
	
}
