package file;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

/**
 * https://codeiq.jp/ace/joboffer_apli/q343
 */
public class ThumbnailMaker {

	private static final String INPUT_DIR = "src";
	private static final String OUTPUT_FILE = "dst.html";
	private static final String IMAGE_FORMAT = "jpeg";
	private static final String[] TARGET_EXTENTIONS = new String[] {"jpg", "jpeg", "png", "gif", "bmp", "tif"};

	public static void main(String[] args) {
		File targetDir = new File(INPUT_DIR);
		File[] images = targetDir.listFiles();
		for (File f : images) {
			if (isTargetFile(f)) {
				byte[] binaryData = toBinaryData(f);
				String encoded = Base64.encodeBase64String(binaryData);
				String imgTag = createImgTag(encoded);
				writeHtml(OUTPUT_FILE, imgTag);
			}
		}
	}

	private static boolean isTargetFile(File f) {
		if ((f != null) && f.exists() && f.isFile()) {
			String fileName = f.getName().toLowerCase();
			for (String ext : TARGET_EXTENTIONS) {
				if (fileName.endsWith(ext)) {
					return true;
				}
			}
		}
		return false;
	}

	private static byte[] toBinaryData(File imageFile) {
		ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
		BufferedOutputStream output = new BufferedOutputStream(bytesOutput);
		try {
			BufferedImage input = ImageIO.read(imageFile);
			BufferedImage resizedInput = resize(input);
			ImageIO.write(resizedInput, IMAGE_FORMAT, output);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytesOutput.toByteArray();
	}

	private static BufferedImage resize(BufferedImage input) {
		Image image = input.getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING );
		BufferedImage resized = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resized.createGraphics();
		g.drawImage(image, 0, 0, null);
		return resized;
	}

	private static String createImgTag(String str) {
		if (str == null)
			str = "";
		StringBuilder sb = new StringBuilder(str.length() + 100);
		sb.append("<img src=\"data:image/jpeg;base64,");
		sb.append(str);
		sb.append("\" />");
		return sb.toString();
	}

	private static void writeHtml(String fileName, String text) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(new File(fileName), true));
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
