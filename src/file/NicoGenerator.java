package file;

public class NicoGenerator {

	public static void main(String[] args) {
		System.out.println(createHtml(1, 999));
	}

	private static String createHtml(int offset, int limit) {
		StringBuilder sb = new StringBuilder(limit * 300);
		sb.append("<html>\n<head>\n<title>nico</title>\n</head>\n<body>\n");
		for (int i = offset; i <= limit; i++) {
			sb.append(createImgTag(i));
		}
		sb.append("</body>\n</html>\n");
		return sb.toString();
	}

	private static String createImgTag(int num) {
		StringBuilder sb = new StringBuilder(300);
		sb.append("  <img src=\"http://res.nimg.jp/img/base/head/icon/nico/");
		sb.append(String.format("%03d", num));
		sb.append(".gif\" width=\"48\" height=\"48\">");
		sb.append("\n");
		return sb.toString();
	}

}