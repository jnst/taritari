package japanese;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class NameChecker {

	/*
	 * ァ-ヶ は中点や長音を含まない
	 */
	
	// 黒龍がなぜかNG
	private static final String NAME_PATTERN_STRING1= "[^0-9a-zA-Zぁ-ヶ亜-黑]";
	private static final Pattern NAME_PATTERN1 = Pattern.compile(NAME_PATTERN_STRING1);
	// 簡体字と繁体字もなぜかOK(音引きもOK)
	private static final String NAME_PATTERN_STRING2 = "[^0-9a-zA-Zぁ-龥]";
	private static final Pattern NAME_PATTERN2 = Pattern.compile(NAME_PATTERN_STRING2);
	// 簡体字と繁体字もなぜかOK(音引きもOK)
	private static final String NAME_PATTERN_STRING3 = "[^0-9a-zA-Zぁ-龠]";
	private static final Pattern NAME_PATTERN3 = Pattern.compile(NAME_PATTERN_STRING3);
	//日本の漢字NG,簡体字OKになるぽい
	private static final String NAME_PATTERN_STRING4 = "[^0-9a-zA-Zぁ-煕]";
	private static final Pattern NAME_PATTERN4 = Pattern.compile(NAME_PATTERN_STRING4);
	// 簡体字と繁体字もなぜかOK
	private static final String NAME_PATTERN_STRING5 = "[^\\p{Alnum}\\p{InHiragana}\\p{InKatakana}\\p{InCJKUnifiedIdeographs}]";
	private static final Pattern NAME_PATTERN5 = Pattern.compile(NAME_PATTERN_STRING5);
	// ヶと々と音引きに対応、中黒や濁点や半濁点はNG
	private static final String NAME_PATTERN_STRING6 = "[^0-9a-zA-Zぁ-んァ-ヶ一-龠々ー]";
	private static final Pattern NAME_PATTERN6 = Pattern.compile(NAME_PATTERN_STRING6);

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		// 半角数字
		check("0123456789");
		// 半角英小文字
		check("abcdefghijklmnopqrstuvwxyz");
		// 半角英大文字
		check("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		// 全角ひらがな
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
		// 全角ひらがな小文字
		check("ぁぃぅぇぉ");
		check("ゃゅょ");
		// 全角カタカナ
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
		// 全角カタカナ小文字
		check("ァィゥェォ");
		check("ャュョ");
		check("ヶ");
		// 音引き
		check("ー");
		// 漢字
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
		
		System.out.println("---------- 以下NG ----------");
		
		// 全角数字
		check("０１２３４５６７８９");
		// 全角英小文字
		check("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ");
		// 全角英大文字
		check("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ");
		// 半角カタカナ
		check("ｧｨｩｪｫ");
		check("ｶｷｸｹｺ");
		check("ｻｼｽｾｿ");
		check("ﾀﾁﾂﾃﾄ");
		check("ﾅﾆﾇﾈﾉ");
		check("ﾊﾋﾌﾍﾎ");
		check("ﾏﾐﾑﾒﾓ");
		check("ﾔﾕﾖ");
		// 中黒
		check("・");
		// 濁音、半濁音
		check("゜゛");
		// 空白
		check(" 　	");
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
		// 繰り返し記号
		check("〃ゝゞヽヾ");
		// 機種依存文字
		check("①②③④⑤⑥⑦⑧⑨⑩");
		// 特殊文字
		check("†聖天使猫姫†");
		// ハングル
		check("아가다오쓰나는");
		// キリル文字
		check("АБВГҐДЃЂЕЄЀЁЖЗЅИІЇЙЍЈКЛЉМНЊОӨПРСТ");
		check("абвгґдѓђеєѐёжзѕиіїйѝјклљмнњоөпрст");
		// アラビア語
		check("غة العربي");
		
		System.out.println((System.currentTimeMillis() - start) + "ms");
	}

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
