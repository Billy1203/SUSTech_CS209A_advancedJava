import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

class Code {
	char c;
	byte[] code;
	int count;

	public Code(char c, byte[] code, int value) {
		this.c = c;
		this.code = new byte[code.length];
		System.arraycopy(code, 0, this.code, 0, code.length);
		this.count = value;

	}

	public void increase() {
		count++;
	}

	@Override
	public int hashCode() {
		return c;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Code) {
			Code e = (Code) o;
			return (this.c == e.c);
		}
		return false;
	}

	@Override
	public String toString() {
		String str = String.valueOf(c) + ", ";
		for (int i = 0; i < code.length; i++) {

			str += String.format("%02X", code[i]);

		}
		str += ", ";
		str += count;
		return str;

	}
}

class CharComparator implements Comparator<Code> {

	@Override
	public int compare(Code o1, Code o2) {
		return o1.c - o2.c;
	}

}

class CodeComparator implements Comparator<Code> {

	@Override
	public int compare(Code o1, Code o2) {

		if (o1.code == null) {
			return -1;
		}
		if (o2.code == null) {
			return 1;
		}

		if (o1.code.length > o2.code.length) {
			return 1;
		} else if (o1.code.length < o2.code.length) {
			return -1;
		}
		for (int i = 0; i < o1.code.length; i++) {
			int o1_value = o1.code[i] & 0x00FF;
			int o2_value = o2.code[i] & 0x00FF;
			if (o1_value > o2_value) {
				return 1;
			} else if (o1_value < o2_value) {
				return -1;
			} else {
				continue;
			}
		}
		return 0;
	}

}

class CountComparator implements Comparator<Code> {
	@Override
	public int compare(Code o1, Code o2) {
		return o2.count - o1.count;
	}
}

public class WordCount {
	static Comparator<Code> chooseComparator(String sortKey) throws Exception {
		// You can rewrite using lambda
		switch (sortKey) {
		case "char":
			return new CharComparator();
		case "code":
			return new CodeComparator();
		case "count":
			return new CountComparator();
		default:
			throw new Exception("Sort key is invalid!");
		}

	}

	public static void main(String[] args) {
		// args[0] filename
		// args[1] charset
		// args[2] target charset
		// args[3] sort key
		try {
			FileInputStream fis = new FileInputStream(args[0]);
			InputStreamReader isr = new InputStreamReader(fis, args[1]);
			BufferedReader bReader = new BufferedReader(isr);
			HashMap<Character, Code> wordCount = new HashMap<>();

			// word count
			while (true) {
				String str = bReader.readLine();
				if (null == str) {
					break;
				}
				for (char c : str.toCharArray()) {
					if (c >= 0x4E00 && c <= 0x9FA5) {// limit in the range of
														// 0x4e00~0x9fa5

						if (wordCount.containsKey(c)) {
							wordCount.get(c).increase();
						} else {
							// transform to target code
							Charset charset = Charset.forName(args[2]);
							ByteBuffer byteBuffer = charset.encode(Character.toString(c));
							int limit = byteBuffer.limit();
							byte[] encode = byteBuffer.array();
							byte[] valid_encode = new byte[limit];
							for (int i = 0; i < limit; i++) {
								valid_encode[i] = encode[i];
							}
							Code code = new Code(c, valid_encode, 1);
							wordCount.put(c, code);

						}

					}
				}
			}
			isr.close();

			// sort by sort key
			List<Code> list = new ArrayList<>(wordCount.values());
			Comparator<Code> currentComparator = chooseComparator(args[3]);
			list.sort(currentComparator);

			// write to a file
			BufferedWriter bWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(args[2] + "_Dict_From_" + args[0]), "utf-8"));
			for (Code code : list) {
				bWriter.write(code.toString());
				bWriter.newLine();
			}
			bWriter.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
