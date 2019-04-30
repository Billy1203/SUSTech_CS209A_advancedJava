import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IntegerFilter2 {

	public static void main(String[] args) {
		Predicate<Integer> isEven = (e) -> e % 2 == 0;
		Predicate<Integer> isOdd = (e) -> e % 2 != 0;
		Predicate<Integer> isPrime = (e) -> {
			if (e == 1)
				return false;
			for (int i = 2; i <= Math.sqrt(e); i++) {
				if (e % i == 0)
					return false;
			}
			return true;
		};
		Predicate<Integer> isBiggerThanFive = (e) -> e > 5;

		Scanner in = new Scanner(System.in);

		int n = in.nextInt();
		ArrayList<Integer> arrayList = new ArrayList<>();
		for (int i = 0; i < n; i++) {

			arrayList.add(in.nextInt());
		}
//		List<Integer> res = arrayList.stream().filter(isEven).collect(Collectors.toList());
//		res.forEach(System.out::println);
		arrayList.stream().filter(isEven).forEach(System.out::println);
		
		// even number
		// arrayList.removeIf(isOdd);

		// odd number
		// arrayList.removeIf(isEven);

		// prime
		// arrayList.removeIf(isPrime.negate());

		// prime and bigger than 5
		// arrayList.removeIf(isPrime.and(isBiggerThanFive).negate());

		// Consumer
		// arrayList.forEach(System.out::println);

		in.close();
	}

}
