package time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unix 時間を扱うユーティリティ
 * 
 * @author sato_jun
 */
public class UnixTimeUtil {

	// テストコード
	public static void main(String[] args) throws Exception {
		long time = toUnixTime("1970/01/01 12:00:00");
		String str = toDateString(time);
		System.out.printf("%s (%d)%n", str, time);
	}

	/**
	 * Unix 時間を yyyy/MM/dd HH:mm:ss 形式に変換して出力します。
	 * 
	 * @param time
	 */
	public static void print(long time) {
		System.out.println(toDateString(time));
	}

	/**
	 * yyyy/MM/dd HH:mm:ss 形式の文字列を Unix 時間に変換して出力します。
	 * 
	 * @param str
	 */
	public static void print(String str) {
		System.out.println(toUnixTime(str));
	}

	/**
	 * Unix 時間を yyyy/MM/dd HH:mm:ss 形式の文字列に変換します。
	 * 
	 * @param time
	 * @return
	 */
	public static String toDateString(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date = sdf.format(new Date(time));
		return date;
	}

	/**
	 * "2011/01/06 16:06:06" 形式の文字列を Unix 時間に変換します。
	 * 
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static long toUnixTime(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * Unix 時間を時分秒で表して出力します。
	 * 
	 * @param time
	 */
	public static void printHMS(long time) {
		long seconds = time / 1000L;
		long minutes = seconds / 60L;
		long hours = minutes / 60L;
		long days = hours / 24L;
		System.out.printf("%d秒 / %d分 / %d時間 / %d日%n", seconds, minutes, hours, days);
	}

}
