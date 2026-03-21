<script lang="ts">
  import Footnote from '$lib/components/Footnote.svelte';
  import FootnoteRef from '$lib/components/FootnoteRef.svelte';
</script>

<template lang="flat-html">
  <div anchor-button id="what-are-generic-types">
    <h1>WHAT IS THREAD-LOCAL STORAGE (TLS)?</h1>
    <p>
      Thread-local storage<FootnoteRef id="tls" /> is global and static memory, but instead of being
      shared between threads, it is local to individual threads. To better explain this, I will briefly
      go over the types of memory in a program<FootnoteRef id="types-of-memory" />:
    </p>
    <ul>
      <li><b>Data</b>: preinitialized modifiable static and global data</li>
      <li><b>BSS</b>: uninitialized static and global data</li>
      <li><b>Heap</b>: shared static and global data</li>
      <li><b>Stack</b>: local variables and parameters</li>
      <li><b>Registers</b>: really fast CPU memory</li>
    </ul>
    <p>The types of memory that are shared among threads include: Data, BSS, and Heap.</p>
    <p>
      This poses a problem for when you want a variable that is static or global, but not shared
      among threads (i.e. each thread has its own copy of the variable). Different languages have
      different solutions to this problem.
    </p>
  </div>
  <div anchor-button id="implementations">
    <h1>LANGUAGE IMPLEMENTATIONS</h1>
    <div anchor-button id="pthread-implementation">
      <h3>PThread Implementation</h3>
      <p>
        <a target="_blank" href="http://stackoverflow.com/a/15101240/1305997"
          >Pthread implementations in C</a
        >
        have <span class="pre">pthread_key_create</span> and
        <span class="pre">pthread_key_delete</span>
        to allocate and deallocate space on a thread, and
        <span class="pre">pthread_getspecific</span>
        and <span class="pre">pthread_setspecific</span> to retrieve and set the data.
      </p>
      <pre>
        <code class="language-c" style="margin: 40px 0;">
          {`
            #include <stdio.h>
            #include <stdlib.h>
            #include <pthread.h>

            #define NUMTHREADS 4

            pthread_key_t glob_var_key;

            void do_something()
            {
              //get thread specific data
              int* glob_spec_var = pthread_getspecific(glob_var_key);
              printf("Thread %d before mod value is %d\n", (unsigned int)pthread_self(), *glob_spec_var);
              *glob_spec_var += 1;
              printf("Thread %d after mod value is %d\n", (unsigned int)pthread_self(), *glob_spec_var);
            }

            void* thread_func(void *arg)
            {
              int *p = malloc(sizeof(int));
              *p = 1;
              pthread_setspecific(glob_var_key, p);
              do_something();
              do_something();
              pthread_setspecific(glob_var_key, NULL);
              free(p);
              pthread_exit(NULL);
            }

            int main(void)
            {
              pthread_t threads[NUMTHREADS];
              int i;

              pthread_key_create(&amp;glob_var_key,NULL);
              for (i=0; i < NUMTHREADS; i++)
                pthread_create(threads+i,NULL,thread_func,NULL);

              for (i=0; i < NUMTHREADS; i++)
                pthread_join(threads[i], NULL);

              return 0;
            }
          `}
        </code>
      </pre>
    </div>
    <div anchor-button id="swift-implementation">
      <h3>Swift Implementation</h3>
      <p>
        <a target="_blank" href="https://gist.github.com/kristopherjohnson/6f14a50006127424faf3"
          >Swift's implementation</a
        >
        uses a dictionary called <span class="pre">threadDictionary</span>.
      </p>
      <pre>
        <code class="language-javascript" style="margin: 40px 0;">
          {`
            public func checkThreadLocal<T: AnyObject>(key: String, create: () -> T) -> T {
              let threadDictionary = NSThread.currentThread().threadDictionary

              if let cachedObject = threadDictionary[key] as T? {
                return cachedObject
              } else {
                let newObject = create()
                threadDictionary[key] = newObject
                return newObject
              }
            }

            func getFormatter() -> NSDateFormatter {
              return checkThreadLocal("SomeName") {
                println("This block will only be executed once")
                let enUSPOSIXLocale = NSLocale(localeIdentifier: "en_US_POSIX")
                let formatter = NSDateFormatter()
                formatter.locale = enUSPOSIXLocale
                formatter.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'"
                formatter.timeZone = NSTimeZone(forSecondsFromGMT: 0)
                return formatter
              }
            }

            let x1 = getFormatter()
            let x2 = getFormatter()
            let x3 = getFormatter()
          `}
        </code>
      </pre>
      <p>
        In the above code example, the <span class="pre">checkThreadLocal</span> function checks the
        threadDictionary to see if an entry with the given <span class="pre">key</span> value has
        been added to the current thread's threadDictionary. If it has, then it simply returns the
        already created object. Otherwise it creates the object (using the create() lambda) and
        returns it. <span class="pre">x1</span>, <span class="pre">x2</span>, and
        <span class="pre">x3</span> all reference the same variable in the current running thread's memory.
      </p>
      <p>
        However, if you were to run <span class="pre">getFormatter()</span> once again in a new thread,
        you would create a separate instance of the NSDateFormatter:
      </p>
      <pre>
        <code class="language-javascript" style="margin: 40px 0;">
          {`
            let x1 = getFormatter() // NEW INSTANCE CREATED!!
            let x2 = getFormatter() // References the instance created by x1
            let x3 = getFormatter() // References the instance created by x1

            DispatchQueue.main.async {
              let x4 = getFormatter() // NEW INSTANCE CREATED!!
              let x5 = getFormatter() // References the instance created by x4
              let x6 = getFormatter() // References the instance created by x4
            }

            let x7 = getFormatter() // References the instance created by x1
          `}
        </code>
      </pre>
      <p>
        This is because inside the <span class="pre">DispatchQueue.main.async</span> block, you are inside
        a separate thread with its own threadDictionary.
      </p>
    </div>
    <div anchor-button id="java-implementation">
      <h3>Java Implementation</h3>
      <p>
        <a target="_blank" href="http://tutorials.jenkov.com/java-concurrency/threadlocal.html"
          >Java's implementation</a
        > uses a data structure that is near identical to what Flat uses. Java uses a ThreadLocal object
        that takes a generic argument for the type of data that is being stored, and you use get/set/remove
        functions to manage that memory within the thread.
      </p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            public class ThreadLocalExample {
              public static class MyRunnable extends Thread {
                private static ThreadLocal<Integer> threadLocal = ThreadLocal<Integer>();

                public void run() {
                  threadLocal.set((int)(Math.random() * 100));

                  try {
                      Thread.sleep(2000);
                  } catch (InterruptedException e) {}

                  System.out.println(threadLocal.get());
                }
              }

              public static void main(String[] args) {
                Thread thread1 = MyRunnable();
                Thread thread2 = MyRunnable();

                thread1.start();
                thread2.start();

                thread1.join(); // wait for thread 1 to terminate
                thread2.join(); // wait for thread 2 to terminate
              }
            }
          `}
        </code>
      </pre>
    </div>
    <div anchor-button id="flat-implementation">
      <h3>Flat Implementation</h3>
      <p>The same code in Flat would look like:</p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            class ThreadLocalExample {
              static class MyRunnable extends Thread {
                static ThreadLocal<Int> threadLocal = ThreadLocal()

                public run() {
                  threadLocal.set((Int)(Math.random() * 100))

                  Thread.sleep(2000)

                  Console.writeLine(threadLocal.get())
                }
              }

              public static main(String[] args) {
                let thread1 = MyRunnable()
                let thread2 = MyRunnable()

                thread1.start()
                thread2.start()

                thread1.join() // wait for thread 1 to terminate
                thread2.join() // wait for thread 2 to terminate
              }
            }
          `}
        </code>
      </pre>
      <p>
        They are nearly identical. However, this is not the only way that Flat allows you to declare
        thread locals. With a little syntax sugar from the <span class="pre">thread_local</span>
        modifier (or <span class="pre">[ThreadLocal]</span> annotation), you are able to achieve the
        same result with minimal change to how you would program it if it were not necessary to be local
        to the thread:
      </p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            class ThreadLocalExample {
              static class MyRunnable extends Thread {
                static thread_local Int threadLocal

                public run() {
                  threadLocal = (Int)(Math.random() * 100)

                  Thread.sleep(2000)

                  Console.writeLine(threadLocal)
                }
              }

              public static main(String[] args) {
                let sharedRunnableInstance = MyRunnable()

                thread1.start()
                thread2.start()

                thread1.join() // wait for thread 1 to terminate
                thread2.join() // wait for thread 2 to terminate
              }
            }
          `}
        </code>
      </pre>
      <p>
        The thread_local modifier also allows for more platform specific optimizations to take
        place. For instance, when compiled to C, the compiler will output the thread_local fields
        using the <a
          target="_blank"
          href="https://gcc.gnu.org/onlinedocs/gcc-3.3/gcc/Thread-Local.html">__thread</a
        >
        modifier, which offers a performance boost. Flat's <span class="pre">thread_local</span> modifier
        will be available in version 0.3.7 and up.
      </p>
    </div>
  </div>

  <div anchor-button id="conclusion">
    <h1>Conclusion</h1>
    <p>
      Considering the different approaches that the different languages in this article showcased,
      Flat decided to use a ThreadLocal data structure because of its clean implementation with the <span
        class="pre">thread_local</span
      >
      modifier. There needed to be a seamless way to define thread-local data without using some sort
      of key/value pair to keep track of it. Instead of requiring you to keep track of the thread ID
      <i>and</i>
      the variable itself, the <span class="pre">thread_local</span> modifier consolidates the user's
      focus on the variable itself.
    </p>
  </div>

  <div anchor-button id="footnotes">
    <h4>Footnotes:</h4>
    <Footnote id="tls"
      >More information on thread-local storage can be found <a
        target="_blank"
        href="https://en.wikipedia.org/wiki/Thread-local_storage">here</a
      >.</Footnote
    >
    <Footnote id="types-of-memory"
      >More information can be found <a
        target="_blank"
        href="https://en.wikipedia.org/wiki/Data_segment">here</a
      >.</Footnote
    >
  </div>
</template>
