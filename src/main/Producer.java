package main;

import java.util.List;

class Producer implements Runnable {
	private final List<Integer> taskQueue;
	private final int MAX_CAPACITY;

	public Producer(List<Integer> sharedQueue, int size) {
		this.taskQueue = sharedQueue;
		this.MAX_CAPACITY = size;
	}

	@Override
	public void run() {
		int counter = 0;
		for (int i = 1; i <= 10; i++) {
			try {
				produce(counter++);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void produce(int i) throws InterruptedException {
		synchronized (taskQueue) {
			while (taskQueue.size() == MAX_CAPACITY) {
				System.out.println("Queue is full " + Thread.currentThread().getName() + " is waiting , size: "
						+ taskQueue.size());
				taskQueue.wait();
			}
			Thread.sleep(500);
			taskQueue.add(i);
			System.out.println("Produced: " + i + " size: " + taskQueue.size());
			taskQueue.notifyAll();
		}
	}
}