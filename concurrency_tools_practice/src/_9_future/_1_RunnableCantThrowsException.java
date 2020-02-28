package _9_future;

/**
 * 描述：     在run方法中无法抛出checked Exception
 */
public class _1_RunnableCantThrowsException {

    public void ddd() throws Exception {
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


    }
}
