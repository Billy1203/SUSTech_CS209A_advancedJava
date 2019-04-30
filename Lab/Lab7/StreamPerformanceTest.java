import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamPerformanceTest {

	public static void main(String[] args) {
		// generate some random integer
		int n = 10000000;

		// Stream
		 streamFilter(n);

		// StreamParallel
		 streamFilterParallel(n);

		// IntStream
		streamFilterToInt(n);

		// IntStream Parallel
		streamFilterToIntParallel(n);

		// collection
		collectionFilter(n);
	}

	public static void streamFilter(int n) {
		long timeStart = System.currentTimeMillis();
		// Object[] res = Stream.generate(Math::random).map(i -> (int) (i *
		// n)).limit(n).filter(e -> e % 2 == 0)
		// .toArray();
		Stream.generate(Math::random).map(i -> (long) (i * n)).limit(n).reduce(0L, Long::sum);

		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		System.out.println("Stream:" + timeCost);

	}

	public static void streamFilterParallel(int n) {
		long timeStart = System.currentTimeMillis();
		// Object[] res = Stream.generate(Math::random).map(i -> (int) (i *
		// n)).limit(n).parallel().filter(e -> e % 2 == 0)
		// .toArray();
		Stream.generate(Math::random).map(i -> (long) (i * n)).limit(n).parallel().reduce(0L, Long::sum);

		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		System.out.println("StreamParallel:" + timeCost);

	}

	public static void streamFilterToInt(int n) {
		long timeStart = System.currentTimeMillis();
		// int[] res = Stream.generate(Math::random).mapToInt(i -> (int) (i *
		// n)).limit(n).filter(e -> e % 2 == 0)
		// .toArray();
		Stream.generate(Math::random).mapToLong(i -> (long) (i * n)).limit(n).reduce((i, j) -> i + j);

		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		System.out.println("IntStream:" + timeCost);

	}

	public static void streamFilterToIntParallel(int n) {
		long timeStart = System.currentTimeMillis();
		// int[] res = Stream.generate(Math::random).mapToInt(i -> (int) (i *
		// n)).limit(n).parallel().filter(e -> e % 2 == 0)
		// .toArray();
		Stream.generate(Math::random).mapToLong(i -> (long) (i * n)).limit(n).parallel().reduce((i, j) -> i + j);

		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		System.out.println("IntStreamParallel:" + timeCost);

	}

	public static void collectionFilter(int n) {
		long timeStart = System.currentTimeMillis();
		ArrayList<Integer> arrayList = new ArrayList<Integer>(n);
		generateArrayList(n, arrayList);
		arrayList.removeIf(e -> e % 2 != 0);
		long timeEnd = System.currentTimeMillis();
		long timeCost = timeEnd - timeStart;
		System.out.println("Collection:" + timeCost);
	}

	public static void generateArrayList(int n, ArrayList<Integer> arrayList) {

		for (int i = 0; i < n; i++) {
			arrayList.add((int) (Math.random() * n));
		}

	}

}
