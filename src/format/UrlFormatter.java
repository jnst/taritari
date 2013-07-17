package format;

/**
 * URLのフォーマッター
 * クエリーストリングを見やすくする
 * 
 * @author sato_jun
 */
public class UrlFormatter {

	public static void main(String[] args) {
		String url = "https://example.jp/search?q=%E3%83%86%E3%82%B9%E3%83%88&oq=%E3%83%86%E3%82%B9%E3%83%88&aqs=chrome.0.69i57j0j69i60j69i61l2j0.1785j0&sourceid=chrome&ie=UTF-8";
		System.out.println(format(url));
	}

	/**
	 * 見やすく改行した文字列にする
	 * 
	 * @param url URL
	 * @return 整形されたURL
	 */
	public static String format(String url) {
		if ((url == null) || url.length() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder(url.length() + 100);
		for (char c : url.toCharArray()) {
			if (isTarget(c)) {
				sb.append("\n  ");
			}
			sb.append(c);
		}
		return sb.toString();
	}

	private static boolean isTarget(char c) {
		if ((c == '?' || (c == '&'))) {
			return true;
		}
		return false;
	}

}
