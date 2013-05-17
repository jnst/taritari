package japanese;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日本語の名前チェック処理
 * 半角、全角、英数字、ひらがな、カタカナ、漢字、記号についてどこまでを許可するかを判断するためのテストケース
 * 
 * @author jnst
 */
@SuppressWarnings("unused")
public class NameChecker {

	/*
	 * 漢字には UTF-8 の並び順的に日本語の漢字以外にも簡体字と繁体字も混在するため、
	 * "ぁ-龠"指定では中国語を除外できない。
	 * しかし一般的に中国語を除外しているケースをみないため問題ないと考えている。
	 * "ァ-ヶ"指定は中点(なかてん)や長音(ちょうおん)を含まない。
	 * "々"や"ー"(長音)は個別指定する必要がある。
	 */

	// 日本語の漢字もNG、濁点と半濁点がOK
	private static final Pattern NAME_PATTERN1 = Pattern.compile("[^0-9a-zA-Zぁ-ヶ亜-黑]");
	// 濁点と半濁点と中点と長音OK、々がNG
	private static final Pattern NAME_PATTERN2 = Pattern.compile("[^0-9a-zA-Zぁ-龥]");
	private static final Pattern NAME_PATTERN3 = Pattern.compile("[^0-9a-zA-Zぁ-龠]");
	private static final Pattern NAME_PATTERN4 = Pattern.compile("[^\\p{Alnum}\\p{InHiragana}\\p{InKatakana}\\p{InCJKUnifiedIdeographs}]");
	// 漢字の判定が独特
	private static final Pattern NAME_PATTERN5 = Pattern.compile("[^0-9a-zA-Zぁ-煕]");
	// ヶと々と長音OK、中点や濁点や半濁点はNG
	private static final Pattern NAME_PATTERN6 = Pattern.compile("[^0-9a-zA-Zぁ-んァ-ヶ一-龠々ー]");

	/**
	 * テストケース
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		// 数字
		checkNumeric();
		// アルファベット
		checkAlphabet();
		// ひらがな
		checkKatakana();
		// カタカナ
		checkKatakana();
		// 漢字
		checkKanji();
		// 記号全般
		checkKigou();
		// 外国語
		checkForeignLanguage();
		System.out.println((System.currentTimeMillis() - start) + "ms");
	}

	/**
	 * ひらがなをチェックする。
	 */
	public static void checkHiragana() {
		// 全角大文字
		check("あいうえお");
		check("かきくけこ");
		check("さしすせそ");
		check("たちつてと");
		check("なにぬねの");
		check("はひふへほ");
		check("まみむめも");
		check("やゆよ");
		check("らりるれろ");
		check("わゐうゑをん");
		check("がぎぐげご");
		check("ざじずぜぞ");
		check("だぢづでど");
		check("ばびぶべぼ");
		check("ぱぴぷぺぽ");
		// 全角小文字
		check("ぁぃぅぇぉ");
		check("ゃゅょ");
	}

	/**
	 * カタカナをチェックする。
	 */
	public static void checkKatakana() {
		// 全角大文字
		check("アイウエオ");
		check("カキクケコ");
		check("サシスセソ");
		check("タチツテト");
		check("ナニヌネノ");
		check("ハヒフヘホ");
		check("マミムメモ");
		check("ヤユヨ");
		check("ラリルレロ");
		check("ワヰウヱヲンヴ");
		check("カギグゲゴ");
		check("ザジズゼゾ");
		check("ダヂヅデド");
		check("バビブベボ");
		check("パピプペポ");
		// 全角小文字
		check("ァィゥェォ");
		check("ャュョ");
		check("ヶ");
		// 半角小文字
		check("ｧｨｩｪｫ");
		check("ｶｷｸｹｺ");
		check("ｻｼｽｾｿ");
		check("ﾀﾁﾂﾃﾄ");
		check("ﾅﾆﾇﾈﾉ");
		check("ﾊﾋﾌﾍﾎ");
		check("ﾏﾐﾑﾒﾓ");
		check("ﾔﾕﾖ");
	}

	/**
	 * 漢字をチェックする。
	 */
	public static void checkKanji() {
		// 日本の漢字
		check("亜々赤白憂鬱幽霊");
		check("嘉緒翠京桜靖ビス湖とっぽ束生夏陽夏照空々");
		check("神生理美依羅炎皇斗幻の銀侍沙利菜愛利江留");
		check("野風平蔵重親愛海月夢杏一将来織田信長");
		check("邪王炎殺黒龍波");
		// 簡体字
		check("北京微梦创科网络技术有限公司");
		check("对写处圆");
		// 繁体字
		check("亞假勛龍龜");
		check("世棒經典賽王建民媽媽嘴大樂透劉真莫那魯道壽司");
	}

	/**
	 * 数字をチェックする。
	 */
	public static void checkNumeric() {
		// 全角
		check("０１２３４５６７８９");
		// 半角
		check("0123456789");
	}

	/**
	 * アルファベットをチェックする。
	 */
	public static void checkAlphabet() {
		// 全角小文字
		check("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ");
		// 全角大文字
		check("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ");
		// 半角小文字
		check("abcdefghijklmnopqrstuvwxyz");
		// 半角大文字
		check("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}

	/**
	 * 外国語をチェックする。
	 */
	public static void checkForeignLanguage() {
		// ハングル
		check("아가다오쓰나는");
		// キリル文字
		check("АБВГҐДЃЂЕЄЀЁЖЗЅИІЇЙЍЈКЛЉМНЊОӨПРСТ");
		check("абвгґдѓђеєѐёжзѕиіїйѝјклљмнњоөпрст");
		// アラビア
		check("غة العربي");
	}

	/**
	 * 記号全般をチェックする。 
	 */
	public static void checkKigou() {
		// 記号
		check("!#$%&*+,-.:;=?@^_`|~");
		check("！＃＄％＆（）＊＋，．―／ ０-９：；＜＝＞？＠Ａ-Ｚ［］＾＿｀ａ-ｚ｛｜｝￣");
		check("()[]{}<>");
		check("\"'");
		check("\\");
		check("○△□◇◎●▲■◆");
		check("~～");
		check("-－");
		check("♪♫♬");
		check("※〒¶");
		check("…‥。、∴∵,.");
		check("↑→↓←⇐⇒⇔");
		// 音引き
		check("ー");
		// 中黒
		check("・");
		// 濁音、半濁音
		check("゜゛");
		// 繰り返し記号
		check("〃ゝゞヽヾ");
		// 機種依存文字
		check("①②③④⑤⑥⑦⑧⑨⑩");
		// 特殊文字
		check("†聖天使猫姫†");
	}

	/**
	 * 空白をチェックする。
	 */
	public static void checkWhiteSpace() {
		check(" 　	");
	}

	/**
	 * 文字列に含まれるすべての文字を判定する。
	 * 
	 * @param name 任意の文字列
	 */
	public static void check(String name) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, len = name.length(); i < len; i++) {
			String s = name.substring(i, i + 1);
			Matcher matcher = NAME_PATTERN6.matcher(s);
			if(matcher.find()){
				sb.append(s);
			}
		}
		print(name, sb);
	}

	private static void print(String name, StringBuilder sb) {
		if (sb.length() == 0) {
			System.out.printf("OK: %s%n", name);
		} else {
			System.out.printf("NG: %d/%d %s [%s]%n", sb.length(), name.length(), name, sb.toString());
		}
	}

}
