import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.OptionalInt;

public class Exercise7 {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Comparator<String[]> valueComparator = (o1, o2) -> Integer.compare(Integer.valueOf(o2[2].trim()),
				Integer.valueOf(o1[2].trim()));
		try (FileInputStream fis = new FileInputStream(args[0]);
				InputStreamReader isr = new InputStreamReader(fis, "utf-8");
				BufferedReader br = new BufferedReader(isr);) {
			switch (args[1]) {
			case "1":
				//This is also work
				Files.lines(Paths.get(args[0]), Charset.forName("utf-8")).map(e -> {
					return e.split(",");
				}).sorted(valueComparator).limit(20).forEach(
						str_array -> System.out.println(str_array[0] + ", " + str_array[1] + ", " + str_array[2]));

//				br.lines().map(e -> {
//					return e.split(",");
//				}).sorted(valueComparator).limit(20).forEach(
//						str_array -> System.out.println(str_array[0] + ", " + str_array[1] + ", " + str_array[2]));
				break;
			case "2":
				OptionalInt sum = br.lines().mapToInt(e -> {
					return Integer.valueOf(e.split(",")[2].trim());
				}).reduce(Integer::sum);
				System.out.println(sum.getAsInt());
				break;
			case "3":
				long count = br.lines().count();
				System.out.println(count);
				break;
			case "4":
				Optional<String[]> maxChar = br.lines().map(e -> {
					return e.split(",");
				}).min(valueComparator);
				System.out.println(maxChar.get()[0]);
				break;
			default:
				System.out.println("Invalid args[1]");
			}

		}
	}
}
