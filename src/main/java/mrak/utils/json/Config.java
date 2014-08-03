package mrak.utils.json;

public final class Config {
	
	public static final Config DEFAULT_CONFIG = new Config(true, false);
	
	private final boolean convertCamelCaseToLCaseUnderscore;
	private final boolean throwExceptionOnMissingFieldsInJSON;
	
	public Config(boolean convertCamelCaseToLCaseUnderscore, boolean throwExceptionOnMissingFieldsInJSON) {
		this.convertCamelCaseToLCaseUnderscore = convertCamelCaseToLCaseUnderscore;
		this.throwExceptionOnMissingFieldsInJSON = throwExceptionOnMissingFieldsInJSON;
	}
	
	public boolean convertCamelCaseToLCaseUnderscore() {
		return convertCamelCaseToLCaseUnderscore;
	}
	
	boolean throwExceptionOnMissingFieldsInJSON() {
		return throwExceptionOnMissingFieldsInJSON;
	}
	
}
