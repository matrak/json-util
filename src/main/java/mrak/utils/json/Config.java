package mrak.utils.json;

public abstract class Config {
	
	public static final Config DEFAULT_CONFIG = new Config() {
		
		@Override
		boolean convertCamelCaseToLCaseUnderscore() {
			return true;
		}
		
		@Override
		boolean throwExceptionOnMissingFieldsInJSON() {
			return false;
		}
	};
	
	abstract boolean convertCamelCaseToLCaseUnderscore();
	abstract boolean throwExceptionOnMissingFieldsInJSON();
	
}
