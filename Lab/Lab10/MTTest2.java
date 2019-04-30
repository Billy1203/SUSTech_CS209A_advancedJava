package ThreadCreate;

class ThreadSample2 implements Runnable {
	String name;

	public ThreadSample2(String name) {
		this.name = name;
	}

	public void run() {
		System.out.println(name + "running……　");
		System.out.println(Thread.currentThread().getName());
	}
}

public class MTTest2 {
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new ThreadSample2("thread" + i));
			System.out.println(Thread.currentThread().getName());
			thread.start();
		}

	}
}
