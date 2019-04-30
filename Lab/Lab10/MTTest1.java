package ThreadCreate;

class ThreadSample1 extends Thread {
	public ThreadSample1(String name) {
		super(name);
	}

	public void run() {
		System.out.println(getName() + "running……　");
	}
}

public class MTTest1 {
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());
		for (int i = 0; i < 10; i++) {
			ThreadSample1 thread = new ThreadSample1("thread" + i);

			thread.start();
		}

	}
}
