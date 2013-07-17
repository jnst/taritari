package format;

/**
 * JSONのフォーマッター
 * 
 * @author sato_jun
 */
public class JsonFormatter {

	private static final String BLANK = "  ";

	public static void main(String[] args) {
		String json = "{\"a_id\":\"01010101\",\"other\":null,\"platformId\":null,\"id\":\"smlk1j2j198s812\",\"special\":{\"gacha_type\":\"ticket\",\"gacha_id\":\"2005\",\"gacha_name\":\"ガチャ\",\"payment_type\":\"ticket\",\"current_count\":121,\"total_count\":142},\"datetime\":\"2013-07-11T14:41:04+0900\",\"common\":{\"UA\":\"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1200.1  Iron/21.0.1200.0  Safari/537.1\",\"server_ip\":\"127.0.0.1\",\"app_type\":0,\"user_id\":\"50001\",\"device_id\":1,\"client_ip\":\"127.0.0.1\"},\"type\":\"gacha_reset\",\"service\":\"kskjflkjl1jl2kj128ksjfo\"}";
		System.out.println(format(json));
	}

	/**
	 * 見やすく改行した文字列にする
	 * 
	 * @param json JSON
	 * @return 整形されたJSON
	 */
	public static String format(String json) {
		if ((json == null) || json.length() == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder(json.length() + 255);
		int count = 0;
		boolean isIntoString = false;
		
		for (char c : json.toCharArray()) {
			if (c == '"') {
				isIntoString = !isIntoString;
			}
			
			if (c == '}') {
				if (!isIntoString && (0 < count)) {
					count--;
				}
				sb.append(toRightBraceCaseString(c, count, isIntoString));
			} else if (c == ',') {
				sb.append(toCommaCaseString(c, count, isIntoString));
			} else if (c == '{') {
				if (!isIntoString) {
					count++;
				}
				sb.append(toLeftBraceCaseString(c, count, isIntoString));
			} else if (c == ':') {
				sb.append(toSemicolonCaseString(c, isIntoString));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	private static String toRightBraceCaseString(char c, int count, boolean isIntoString) {
		StringBuilder sb = new StringBuilder();
		if (!isIntoString) {
			sb.append('\n');
			sb.append(getSpace(count));
		}
		sb.append(c);
		return sb.toString();
	}

	private static String toCommaCaseString(char c, int count, boolean isIntoString) {
		StringBuilder sb = new StringBuilder();
		sb.append(c);
		if (!isIntoString) {
			sb.append('\n');
			sb.append(getSpace(count));
		}
		return sb.toString();
	}

	private static String toLeftBraceCaseString(char c, int count, boolean isIntoString) {
		StringBuilder sb = new StringBuilder();
		sb.append(c);
		if (!isIntoString) {
			sb.append('\n');
			sb.append(getSpace(count));
		}
		return sb.toString();
	}

	private static String toSemicolonCaseString(char c, boolean isIntoString) {
		StringBuilder sb = new StringBuilder();
		sb.append(c);
		if (!isIntoString) {
			sb.append(' ');
		}
		return sb.toString();
	}

	private static String getSpace(int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(BLANK);
		}
		return sb.toString();
	}

}
