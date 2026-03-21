<script lang="ts">
  import Footnote from '$lib/components/Footnote.svelte';
  import FootnoteRef from '$lib/components/FootnoteRef.svelte';
</script>

<template lang="flat-html">
  <div anchor-button id="meaning">
    <h1>WHAT DOES COMPILING TO MULTIPLE TARGETS REALLY MEAN?</h1>
    <p>
      If you have not programmed in a language that allows compilation to multiple different
      compilation targets, then you probably are unaware of the benefits (and possible drawbacks) of
      using a language that offers this feature. Furthermore, you might not even know what exactly
      compiling to multiple targets means.
    </p>
    <p>
      When you think of traditional programming languages, there is a common process that you go
      through when compiling the source code that looks something like this:
    </p>
    <div class="flow-container">
      <p style="white-space: nowrap;">SOURCE CODE</p>
      <img class="down-arrow" src="/images/down-arrow.svg" /><br />
      <p class="flow-stage">COMPILER</p>
      <br />
      <img class="down-arrow" src="/images/down-arrow.svg" /><br />
      <p style="white-space: nowrap;">EXECUTABLE or SHARED LIBRARY</p>
    </div>
    <p>
      You feed your source code into a compiler, and that compiler transforms it into an executable
      or shared library of some sort. You go from a high-level source code file, to a binary
      executable or shared library<FootnoteRef id="gcc-asm" />. Generally, this is how it has been
      done for a long time.
    </p>
    <p>
      The Flat compiler works a little different. It supports outputting to any of the implemented
      output sources. For example: C, Java, and C# are depicted in the chart below.
    </p>
    <table class="flow-container" style="margin: 40px auto;">
      <tr>
        <td colspan="3">
          <p style="white-space: nowrap;">SOURCE CODE</p>
        </td>
      </tr>
      <tr>
        <td colspan="3">
          <img class="down-arrow" src="/images/down-arrow.svg" /><br />
          <p class="flow-stage">COMPILER</p>
        </td>
      </tr>
      <tr>
        <td>
          <img
            class="down-arrow"
            src="/images/down-arrow.svg"
            style="transform: rotate(45deg);"
          /><br />
          <p class="flow-stage" style="min-width: 80px;">C</p>
        </td>
        <td>
          <img class="down-arrow" src="/images/down-arrow.svg" /><br />
          <p class="flow-stage" style="min-width: 80px;">JAVA</p>
        </td>
        <td>
          <img
            class="down-arrow"
            src="/images/down-arrow.svg"
            style="transform: rotate(-45deg);"
          /><br />
          <p class="flow-stage" style="min-width: 80px;">C#</p>
        </td>
      </tr>
      <tr>
        <td>
          <img class="down-arrow" src="/images/down-arrow.svg" /><br />
          <p style="white-space: nowrap;">EXECUTABLE</p>
        </td>
        <td>
          <img class="down-arrow" src="/images/down-arrow.svg" /><br />
          <p style="white-space: nowrap;">EXECUTABLE</p>
        </td>
        <td>
          <img class="down-arrow" src="/images/down-arrow.svg" /><br />
          <p style="white-space: nowrap;">EXECUTABLE</p>
        </td>
      </tr>
    </table>
    <p>
      The end result of the compilation process is the same as the traditional compilation process,
      except that you get multiple executables. The significance of generating multiple executables
      is explained later in the <a href="#benefits">benefits</a> section. You can choose which output
      targets to compile to depending on your needs.
    </p>
    <table class="flow-container" style="margin: 40px auto;">
      <tr>
        <td colspan="3">
          <p style="white-space: nowrap;">SOURCE CODE</p>
        </td>
      </tr>
      <tr>
        <td colspan="3">
          <img class="down-arrow" src="/images/down-arrow.svg" /><br />
          <p class="flow-stage">COMPILER</p>
        </td>
      </tr>
      <tr>
        <td class="disabled">
          <img
            class="down-arrow"
            src="/images/down-arrow.svg"
            style="transform: rotate(45deg);"
          /><br />
          <p class="flow-stage" style="min-width: 80px;">C</p>
        </td>
        <td class="disabled">
          <img class="down-arrow" src="/images/down-arrow.svg" /><br />
          <p class="flow-stage" style="min-width: 80px;">JAVA</p>
        </td>
        <td>
          <img
            class="down-arrow"
            src="/images/down-arrow.svg"
            style="transform: rotate(-45deg);"
          /><br />
          <p class="flow-stage" style="min-width: 80px;">C#</p>
        </td>
      </tr>
      <tr>
        <td class="disabled">
          <img class="down-arrow" src="/images/down-arrow.svg" /><br />
          <p style="white-space: nowrap;">EXECUTABLE</p>
        </td>
        <td class="disabled">
          <img class="down-arrow" src="/images/down-arrow.svg" /><br />
          <p style="white-space: nowrap;">EXECUTABLE</p>
        </td>
        <td>
          <img class="down-arrow" src="/images/down-arrow.svg" /><br />
          <p style="white-space: nowrap;">EXECUTABLE</p>
        </td>
      </tr>
    </table>
  </div>

  <div anchor-button id="benefits">
    <h1>BENEFITS OF MULTIPLE COMPILATION TARGETS</h1>
    <div anchor-button id="performance-analysis">
      <h3>Better performance analysis</h3>
      <p>
        When you have more targets available to compile to, you have more data points on the
        performance of your programs from different perspectives. This allows for a whole new area
        of performance analysis that has not been touched. The ability to compare performance of a
        program from different languages gives insite comparable to adding a whole new dimension to
        the field. Building off of the previous example, consider the following compilation process:
      </p>
      <table class="flow-container" style="margin: 40px auto;">
        <tr>
          <td colspan="3">
            <p style="white-space: nowrap;">SOURCE CODE</p>
          </td>
        </tr>
        <tr>
          <td colspan="3">
            <img class="down-arrow" src="/images/down-arrow.svg" /><br />
            <p class="flow-stage">COMPILER</p>
          </td>
        </tr>
        <tr>
          <td>
            <img
              class="down-arrow"
              src="/images/down-arrow.svg"
              style="transform: rotate(45deg);"
            /><br />
            <p class="flow-stage" style="min-width: 80px;">C</p>
          </td>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" /><br />
            <p class="flow-stage" style="min-width: 80px;">JAVA</p>
          </td>
          <td>
            <img
              class="down-arrow"
              src="/images/down-arrow.svg"
              style="transform: rotate(-45deg);"
            /><br />
            <p class="flow-stage" style="min-width: 80px;">C#</p>
          </td>
        </tr>
        <tr>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" /><br />
            <p style="white-space: nowrap;">EXECUTABLE</p>
          </td>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" /><br />
            <p style="white-space: nowrap;">EXECUTABLE</p>
          </td>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" /><br />
            <p style="white-space: nowrap;">EXECUTABLE</p>
          </td>
        </tr>
      </table>
      <p>Now say that you time the execution speed of each of the outputted executables:</p>
      <table class="flow-container" style="margin: 40px auto;">
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 120px;">C - 120ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">JAVA - 210ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">C# - 220ms</p>
          </td>
        </tr>
      </table>
      <p>
        The top level execution speed is only so helpful, so looking at each of the executable's
        function calls might give some extra insite to where the timing discrepencies are coming
        from:
      </p>
      <table class="flow-container" style="margin: 40px auto;">
        <tr>
          <td style="vertical-align: top;">
            <p class="flow-stage" style="min-width: 120px;">C - 120ms</p>
            <br />
            <img class="down-arrow" src="/images/down-arrow.svg" /><br />
            <p class="flow-stage" style="min-width: 120px;">a() - 30ms</p>
            <br />
            <p class="flow-stage" style="min-width: 120px;">b() - 40ms</p>
            <br />
            <p class="flow-stage" style="min-width: 120px;">c() - 50ms</p>
          </td>
          <td style="vertical-align: top;">
            <p class="flow-stage" style="min-width: 120px;">JAVA - 210ms</p>
            <br />
            <img class="down-arrow" src="/images/down-arrow.svg" /><br />
            <p class="flow-stage" style="min-width: 120px;">a() - 35ms</p>
            <br />
            <p class="flow-stage" style="min-width: 120px;">b() - 45ms</p>
            <br />
            <p class="flow-stage" style="min-width: 120px;">c() - 130ms</p>
          </td>
          <td style="vertical-align: top;">
            <p class="flow-stage" style="min-width: 120px;">C# - 220ms</p>
            <br />
            <img class="down-arrow" src="/images/down-arrow.svg" /><br />
            <p class="flow-stage" style="min-width: 120px;">a() - 35ms</p>
            <br />
            <p class="flow-stage" style="min-width: 120px;">b() - 45ms</p>
            <br />
            <p class="flow-stage" style="min-width: 120px;">c() - 140ms</p>
          </td>
        </tr>
      </table>
      <p>
        So with a deeper look, you can see that the discrepency is largely caused by function c().
        This gives you information that you would not have had otherwise if you had only been
        analyzing the performance based off of a single executable. With this information you can
        pinpoint with more certainty what parts of your software are bottlenecks created by the
        algorithms they use, or whether it is caused by the language architecture itself.
      </p>
      <div anchor-button id="bottleneck-types" style="margin-left: 25px; margin-bottom: 20px;">
        <h4>Algorithmic bottlenecks</h4>
        <p>
          Algorithmic bottlenecks are caused by the <i>program's</i> implementation in contrast to
          the <i>language's</i> implementation. These bottlenecks would detect areas of code that
          might use
          <a target="_blank" href="https://en.wikipedia.org/wiki/Bubble_sort">bubble sort</a>
          instead of a faster sorting algorithm like
          <a target="_blank" href="https://en.wikipedia.org/wiki/Quicksort">quicksort</a>.
        </p>
        <h4>Language architecture bottlenecks</h4>
        <p>
          Language architecture bottlenecks are caused by how the <i>language</i> was implemented that
          code was compiled by. For example, Java and C# are not as good at dealing with numbers and
          computation compared to C. You cannot get around the fact that crunching numbers in Java and
          C# are going to be slower than in C, and this is a language architecture bottleneck.
        </p>
      </div>
      <p>
        The ability to tell the difference between the types of bottlenecks is extremely helpful
        when analyzing the performance of the program. You are able to see if it is user error
        (algorithmic), or if it is out of your ability to address (language architecture), well <i
          >only sometimes</i
        >. When you find language architecture bottlenecks, you could try other language constructs
        that allow you to do the same job and see if it performs better.
      </p>
      <p>
        A lot of the time, these language architecture bottlenecks can have simple solutions. For
        example, in java using primitive arrays instead of ArrayLists can give a significant
        performance boost, especially if the ArrayList is autoboxing each operation that it is
        involved in. Simply converting that code to use a primitive array can speed the code up
        significantly.
      </p>
      <p>
        And the ability to see what areas of the language have performance issues compared to the
        areas that do not can be automated with the aforementioned benchmarker. By taking the
        greated absolute runtime difference from each function between the executables, you are able
        to see which areas are more likely to be caused by language architecture bottlenecks. The
        higher the time difference, the more likely it is to be caused by the language architecture:
      </p>
      <table class="flow-container" style="margin: 40px auto;">
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 120px;">C - 120ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">JAVA - 210ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">C# - 220ms</p>
          </td>
        </tr>
        <tr>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 120px;">a() - 30ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">a() - 35ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">a() - 35ms</p>
          </td>
          <td>
            <p>35ms - 30ms = <span style="color: green;">5ms</span></p>
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 120px;">b() - 40ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">b() - 45ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">b() - 45ms</p>
          </td>
          <td>
            <p>45ms - 40ms = <span style="color: green;">5ms</span></p>
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 120px;">c() - 50ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">c() - 130ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">c() - 140ms</p>
          </td>
          <td>
            <p>140ms - 50ms = <span style="color: red;">90ms</span></p>
          </td>
        </tr>
      </table>
      <p>
        Seeing the differences shows that function c() in C# has a much higher (240% increase)
        difference in runtime than it does in C.
      </p>
      <p>Now, what if there is another function d() that has a high runtime in all languages?</p>
      <table class="flow-container" style="margin: 40px auto;">
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 120px;">C - 320ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">JAVA - 410ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">C# - 420ms</p>
          </td>
        </tr>
        <tr>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 120px;">a() - 30ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">a() - 35ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">a() - 35ms</p>
          </td>
          <td>
            <p>35ms - 30ms = <span style="color: green;">5ms</span></p>
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 120px;">b() - 40ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">b() - 45ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">b() - 45ms</p>
          </td>
          <td>
            <p>45ms - 40ms = <span style="color: green;">5ms</span></p>
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 120px;">c() - 50ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">c() - 130ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">c() - 140ms</p>
          </td>
          <td>
            <p>140ms - 50ms = <span style="color: red;">90ms</span></p>
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 120px;">d() - 200ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">d() - 200ms</p>
          </td>
          <td>
            <p class="flow-stage" style="min-width: 120px;">d() - 200ms</p>
          </td>
          <td>
            <p>200ms - 200ms = <span style="color: green;">0ms</span></p>
          </td>
        </tr>
      </table>
      <p>
        First of all, we can conclude that because the runtime is consistent for function d() across
        all of the languages, it is probably not a lanugage architecture bottleneck. Furthermore,
        because this function takes considerably longer to run than the other functions, it is most
        likely an algorithm bottleneck. The combination of <i>similar</i> and <i>long</i> runtimes across
        languages is how you can filter the algorithmic bottlenecks from non-algorithmic bottlenecks
        with automation.
      </p>
    </div>
    <div anchor-button id="better-compatibility">
      <h3>Better compatibility</h3>
      <p>
        Compiling to multiple targets gives you better compatiblity. You are able to access all of
        the compatibility that the target language has. You can run your programs on low-level micro
        controllers that C can compile to, use Flat code coupled with microsoft code with ASP.NET,
        and much more.
      </p>
      <p>
        You gain the ability to integrate all of your Flat code with the target language. This is
        extremely powerful when it comes to writing general libraries in Flat. Any library you write
        is accessible from any of the available target languages. Here is an illustration of this
        point:
      </p>
      <pre class="centered">
        <code style="margin-top: 40px;">
          {`
            class SomeLibrary {
              public static distance(Point a, Point b) -> Double {
                // ... calculate distance ...
              }
            }
          `}
        </code>
      </pre>
      <table class="flow-container" style="margin: 40px auto; margin-top: 0;">
        <tr>
          <td colspan="3">
            <img class="down-arrow" src="/images/down-arrow.svg" /><br />
            <p class="flow-stage">COMPILER</p>
          </td>
        </tr>
        <tr>
          <td>
            <img
              class="down-arrow"
              src="/images/down-arrow.svg"
              style="transform: rotate(45deg);"
            /><br />
            <p class="flow-stage" style="min-width: 80px;">C</p>
          </td>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" /><br />
            <p class="flow-stage" style="min-width: 80px;">JAVA</p>
          </td>
          <td>
            <img
              class="down-arrow"
              src="/images/down-arrow.svg"
              style="transform: rotate(-45deg);"
            /><br />
            <p class="flow-stage" style="min-width: 80px;">C#</p>
          </td>
        </tr>
      </table>
      <p>
        After compiling <span class="pre type">SomeLibrary</span> to each of the target languages, you
        are able to access the functionality from that target language:
      </p>
      <pre class="centered">
        <code style="margin: 40px 0;">
          {`
            // ======================= C ===========================

            Point* a = Point_construct(5, 6);
            Point* b = Point_construct(10, 6);

            double distance = SomeLibrary_distance(a, b); // 5.0

            // ====================== JAVA =========================

            Point a = Point(5, 6);
            Point b = Point(10, 6);

            double distance = SomeLibrary.distance(a, b); // 5.0

            // ======================= C# ==========================

            var a = Point(5, 6);
            var b = Point(10, 6);

            var distance = SomeLibrary.distance(a, b); // 5.0
          `}
        </code>
      </pre>
      <p>
        This means that any code you write in Flat is reusable in any of the languages that you can
        compile to. This makes programming something in Flat risk-free. You can export the code you
        have writtin in Flat to any of the available target languages and continue on with the
        software without having to manually translate any of the codebase.
      </p>
    </div>
  </div>

  <div anchor-button id="drawbacks">
    <h1>DRAWBACKS OF MULTIPLE COMPILATION TARGETS</h1>
    <p>
      When you deal with multiple targets, you can add the complexity of each language along with
      it. The Flat language is designed to make this as cheap as possible, but there is always going
      to be some work required when adding another compilation target, especially if the target
      language is a considerably different paradigm.
    </p>
    <p>
      Aside from the compiler implementation of the target language, you may need to code special
      functionality for a specific language target to work as needed. An example of this is
      multi-threaded programs with Javascript. Javascript does not natively support multi-threading.
      For this reason, any programs that are heavily based upon multi-threading need some special
      attention in order for the Javascript target to work. A simple solution to this is to have
      threads run synchronously, but this can create undesirable results from a program that is
      expected to run in parallel. To deal with this you can: not compile to Javascript altogether;
      use a non-standard multi-threading library for Javascript (<a
        target="_blank"
        href="http://keithwhor.github.io/multithread.js">multithread.js</a
      > maybe?).
    </p>
  </div>

  <div anchor-button id="conclusion">
    <h2>Conclusion</h2>
    <p>
      With both sides of the argument given, I believe that the benefits greatly outweigh the
      possible drawbacks. The extra dimension that multiple targets adds to performance analysis
      alone is something worth investigating. Time will tell if multiple targets is as useful in
      practice as it is in theory.
    </p>
  </div>

  <div anchor-button id="footnotes">
    <h4>Footnotes:</h4>
    <Footnote id="gcc-asm"
      >Some compilers, <a
        target="_blank"
        href="http://stackoverflow.com/questions/137038/how-do-you-get-assembler-output-from-c-c-source-in-gcc"
        >like GCC</a
      >, allow output of equivalent assembly code.</Footnote
    >
  </div>
</template>
