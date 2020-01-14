package tech.flandia_yingm.ticker;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A class that provides util methods for {@link ThreadFactory}.
 *
 * @author Flandia Yingman
 * @version 1.0
 */
public class ThreadFactories {

    /**
     * Creates a new thread factory of a fixed name of the thread.
     *
     * @param name The fixed name of the thread.
     * @return The created new thread factory.
     */
    public static ThreadFactory ofFixedName(String name) {
        return r -> new Thread(r, name);
    }

    /**
     * Creates a new thread factory of a counting name of the thread.
     * <p>
     * Use {@code {}} to identify where the count should put. (e.g., if it is the first invoking, {@code gui-thread{}}
     * is {@code gui-thread0}, if it is the second call, it is {@code gui-thread1}, etc.)
     *
     * @param name The counting name of the thread.
     * @return The created new thread factory.
     */
    public static ThreadFactory ofCountingName(String name) {
        return new ThreadFactory() {

            private final AtomicLong counter = new AtomicLong();

            @Override
            public synchronized Thread newThread(Runnable runnable) {
                long count = counter.getAndIncrement();
                String threadName = name.replaceAll("\\{}", Long.toString(count));
                return new Thread(runnable, threadName);
            }

        };
    }

}
