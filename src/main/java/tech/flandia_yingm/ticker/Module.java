package tech.flandia_yingm.ticker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * A abstract class that provides parallel start/stop operations.
 *
 * @author Flandia Yingman
 * @version 2.0
 */
public abstract class Module {

    /**
     * The logger of the implementation class.
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * The running flag.
     * <p>
     * This flag can be set by invoking {@link #setRunning(boolean)} and can be get by invoking {@link #isRunning()}.
     */
    private volatile boolean runningFlag = false;

    /**
     * The control lock.
     * <p>
     * This lock is locked when any method is called but in the runner thread.
     */
    protected final Object controlLock = new Object();


    /**
     * The executor that executes the runner thread.
     */
    private ExecutorService executor;

    /**
     * The thread factory that creates the runner threads.
     */
    private final ThreadFactory threadFactory = ThreadFactories.ofCountingName(getName() + "-{}");


    /**
     * Starts the module and set the running flag to {@code true}.
     * <p>
     * It creates a new runner thread, and starts {@link #run()} in the runner thread.
     */
    public final void start() {
        synchronized (controlLock) {
            if (!isRunning()) {
                log.info("{} - Start the module {}", this, getName());
                executor = Executors.newSingleThreadExecutor(threadFactory);
                executor.submit(this::wrappedRun);

                try {
                    waitStarting();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Stops the module and set the running flag to {@code false}.
     * <p>
     * It shutdowns the runner thread to stop {@link #run()}.
     */
    public final void stop() {
        synchronized (controlLock) {
            if (isRunning()) {
                log.info("{} - Stop the module {}", this, getName());
                executor.shutdownNow();
                executor = null;

                try {
                    waitStopping();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Waits for the running flag changed to {@code true}.
     * <p>
     * If the module is running when this method is called, it just returns.
     *
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    protected void waitStarting() throws InterruptedException {
        synchronized (controlLock) {
            if (isRunning()) {
                return;
            }
            while (!isRunning()) {
                controlLock.wait();
            }
        }
    }

    /**
     * Waits for the running flag changed to {@code false}.
     * <p>
     * If the module is not running when this method is called, it just returns.
     *
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    protected void waitStopping() throws InterruptedException {
        synchronized (controlLock) {
            if (!isRunning()) {
                return;
            }
            while (isRunning()) {
                controlLock.wait();
            }
        }
    }

    /**
     * Gets the running flag.
     *
     * @return the running flag
     */
    public final boolean isRunning() {
        synchronized (controlLock) {
            return runningFlag;
        }
    }

    /**
     * Sets the running flag.
     *
     * @param runningFlag the running flag
     */
    private void setRunning(boolean runningFlag) {
        log.trace("{} - Set the running flag to {}", this, runningFlag);
        synchronized (controlLock) {
            this.runningFlag = runningFlag;
            controlLock.notifyAll();
        }
    }


    /**
     * The wrapped {@link #run()}.
     * <p>
     * This method wraps {@link #run()}, it sets the running flag to {@code true} before {@link #run()} is invoked, and
     * sets the running flag to {@code false} after {@link #run()} is invoked.
     */
    private void wrappedRun() {
        log.trace("{} - Run run() method", this);
        try {
            try {
                setRunning(true);
                run();
            } catch (InterruptedException e) {
                log.debug("{} - The thread interrupted while running the module", this);
                //Ignored: thread interrupted
            } catch (Exception e) {
                log.warn("{} - An uncaught exception occurs while running the module, stop the module", this, e);
            } finally {
                setRunning(false);
            }
        } catch (Exception e) {
            log.error("{} - An JVM error occurs in the module", this, e);
        }
    }


    /**
     * The method that does the work.
     *
     * @throws InterruptedException if the thread is interrupted
     */
    protected abstract void run() throws InterruptedException;

    /**
     * Returns the name of this module.
     *
     * @return the name of this module
     */
    public abstract String getName();


    /**
     * Syntactic sugar for {@link Thread#sleep(long)}.
     */
    protected void sleep(long duration) throws InterruptedException {
        if (duration > 0) {
            Thread.sleep(duration);
        }
    }

    /**
     * Syntactic sugar for {@link Thread#isInterrupted()}.
     */
    protected boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + "()";
    }

}
