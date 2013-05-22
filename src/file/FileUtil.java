package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static void main(String[] args) {
		File f = new File("src/file/data/sample.txt");
		List<String> lines = readLine(f);
		List<String> filterdLines = filterNot(lines, "(株)");
		println(filterdLines);
	}

	public static List<String> readLine(File f) {
		List<String> list = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while (br.ready()) {
				list.add(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<String> filter(List<String> list, String word) {
		return filter(list, word, false);
	}

	public static List<String> filterNot(List<String> list, String word) {
		return filter(list, word, true);
	}

	private static List<String> filter(List<String> list, String word, boolean isIgnore) {
		List<String> filterdList = new ArrayList<>();
		if ((list != null) && (word != null)) {
			for (String s : list) {
				if ((s != null) && !s.isEmpty()) {
					if (isIgnore && !s.contains(word)) {
						filterdList.add(s);
					} else if (!isIgnore && s.contains(word)){
						filterdList.add(s);
					}
				}
			}
		}
		return filterdList;
	}

	public static void println(File f) {
		println(readLine(f));
	}

	public static void println(List<String> list) {
		for (String s : list) {
			System.out.println(s);
		}
	}

}
