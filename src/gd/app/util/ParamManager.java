package gd.app.util;

public class ParamManager {

	private String[] args;
	private int cursor;

	public ParamManager(String[] args) {
		this.cursor = -1;
		this.args = args;
	}

	public String getNextParam() {
		if (++this.cursor < this.args.length)
			return this.args[this.cursor];

		return null;
	}

	public static boolean isAnOptionName(String arg) {
		return arg.charAt(0) == '-';
	}

	public static String getOptionName(String arg) {
		if (arg.charAt(1) == '-')
			return arg.substring(2);
		else if (arg.charAt(0) == '-')
			return arg.substring(1);
		else
			return arg;

	}

}
