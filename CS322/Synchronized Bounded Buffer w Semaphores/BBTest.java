class BBTest {

  final static int ITERATIONS = 1<<24;

  // This test program creates two threads: producer and consumer.  The producer
  // inserts the natural numbers into the shared bounded buffer.  The consumer
  // pops the values and verifies that they are in the correct order.
  public static void main(String[] args) {
    BBuffer bb = new BBuffer();
    Thread[] thds = new Thread[2];

    thds[0] = new Thread () {public void run() {producer(bb);}};
    thds[1] = new Thread () {public void run() {consumer(bb);}};

    thds[0].start();
    thds[1].start();

    try {
      thds[0].join();
      thds[1].join();
      System.out.printf("Good Job!\n");
    } catch (InterruptedException e) {
      System.err.printf("Caught unexpected exception\n");
    }
  }


  // Push the values 0..ITERATIONS-1 into the bounded buffer in order.
  //
  private static void producer (BBuffer bb) {
    try {
      for(int i = 0; i < ITERATIONS; ++i) {
	bb.push(i);
	}
    } catch (InterruptedException e) {
    }
  }

  // Verify that the numbers 0..ITERATIONS-1 are popped from the bounded buffer in
  // order.
  //
  private static void consumer (BBuffer bb) {
    try {
      for(int i = 0; i < ITERATIONS; ++i) {
	int datum = bb.pop();
        if( datum != i ) {
          System.err.printf("ERROR: expected %d, and got %d\n", i, datum);
          System.exit(1);
        }
      }
    } catch (InterruptedException e) {
    }
  }
}
