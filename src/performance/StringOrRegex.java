package performance;

import java.util.Random;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class StringOrRegex {

	private static final String STRING_IPHONE = "iPhone";
	private static final String PATTERN_IPHONE = ".*iPhone.*";
	private static final String SAMPLE_USER_AGENT = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_2 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8H7 Safari/6533.18.5";
	private static final Random R = new Random();
	private static final int COUNT = 10000;

	/*
	 * テストコード
	 * ※同時実行すると後者が高速になるのはなぜ？
	 */
	public static void main(String[] args) {
		// String -> 40ms
//		testString();
		// Regex -> 40ms
		testRegex();
	}

	// String.contains の速度テスト
	private static void testString() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			checkByString(generateRandomUserAgent());
		}
		System.out.printf("String: %dms%n", (System.currentTimeMillis() - start));
	}

	// Regex の速度テスト
	private static void testRegex() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < COUNT; i++) {
			checkByString(generateRandomUserAgent());
		}
		System.out.printf("Regex: %dms%n", (System.currentTimeMillis() - start));
	}

	// String.contains による判定
	private static boolean checkByString(String userAgent){
		if ((userAgent != null) && userAgent.contains(STRING_IPHONE)) {
			return true;
		}
		return false;
	}

	// Regex による判定
	private static boolean checkByRegex(String ua){
		if (Pattern.compile(PATTERN_IPHONE).matcher(ua).find()) {
			return true;
		}
		return false;
	}

	private static String generateRandomUserAgent() {
		return SAMPLE_USER_AGENT + R.nextInt(100);
	}

}
