package ThreadCreate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadSample3 implements Callable<String> {
	String name;

	public ThreadSample3(String name) {
		this.name = name;
	}

	public String call() {
		// System.out.println(name + "running…… ");
		System.out.println(Thread.currentThread().getName());
		return (name + "running……　");
	}
}

public class MTTest3 {
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			try {
				System.out.println(Thread.currentThread().getName());
				System.out.println(service.submit(new ThreadSample3("thread" + i)).get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}