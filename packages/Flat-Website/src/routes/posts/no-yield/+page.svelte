<script lang="ts">
  import Footnote from '$lib/components/Footnote.svelte';
  import FootnoteRef from '$lib/components/FootnoteRef.svelte';
</script>

<template lang="flat-html">
  <div anchor-button id="what-is-yield">
    <h1>WHAT IS YIELD?</h1>
    <p>
      Yield is a keyword in several different languages that is used to create free-form enumerable
      types. That is, types that that can be iterated over. They are often used instead of map and
      filter functionality in favor of saving memory and being more performant. Instead of creating
      a new list data structure to hold a temporary set of data that is only used to iterator over,
      you simply return a specific element at a specific position until some condition is met. This
      is in contrast to mapping and filtering, where a new list data structure would be created for
      each type of iteration that is needed.
    </p>
    <div anchor-button id="essence">
      <h3>THE ESSENCE AND INNER WORKINGS OF YIELD</h3>
      <p>Here is an example in C# of the yield keyword:</p>
      <pre>
        <code class="language-csharp" style="margin: 40px 0;">
          {`
            static void Main(string[] args)
            {
              foreach (int fib in Fibs(6))
              {
                Console.WriteLine(fib + " ");
              }
            }

            static IEnumerable<int> Fibs(int fibCount)
            {
              for (int i = 0, prevFib = 0, currFib = 1; i < fibCount; i++)
              {
                yield return prevFib;

                int newFib = prevFib + currFib;

                prevFib = currFib;
                currFib = newFib;
              }
            }
          `}
        </code>
      </pre>
      <p>
        In this example, the foreach loop uses the <span class="pre">IEnumerable</span> returned
        from the <span class="pre">Fibs</span> function to iterate a specified number of Fibonacci numbers.
      </p>
      <p>In Flat, you can achieve the same result with the following code:</p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            public static main(String[] args) {
              for (fib in fibs(6)) {
                Console.writeLine("#fib ")
              }
            }

            static fibs(Int fibCount) -> Int[] {
              var prevFib = 0
              var currFib = 1

              return Int[fibCount].map({
                let value = prevFib

                prevFib = currFib
                currFib += value

                return value
              })
            }
          `}
        </code>
      </pre>
      <p>
        The difference between the two code examples is that in the C# example, no data structure is
        allocated to iterate over the Fibonacci numbers. The implementation of the yield keyword
        under the hood is essentially a complex state-machine that waits to be iterated over. The
        back-end code can be visualized as something comparable to the next code example. This is a
        vast over-simplification of the state-machine, but it gets the point across<FootnoteRef
          id="csharp-yield-backend"
        />:
      </p>
      <pre>
        <code class="language-csharp" style="margin: 40px 0;">
          {`
            static void Main(string[] args)
            {
              foreach (int fib in Fibs(6))
              {
                Console.WriteLine(fib + " ");
              }
            }

            private static sealed class MyStateMachine :
                IEnumerable<int>, IEnumerator<int>
            {
              int current;
              int state;

              int i = 0;
              int prevFib = 0;
              int currFib = 1;
              int fibCount = 6;

              public MyStateMachine(int fibCount)
              {
                state = 0;

                this.fibCount = fibCount;
              }

              public bool MoveNext()
              {
                switch (state)
                {
                  case 0:
                    state = 1;

                    while (i < fibCount)
                    {
                      current = prevFib;

                      int newFib = prevFib + currFib;

                      prevFib = currFib;
                      currFib = newFib;

                      state = 2;
                      return true;
                    Label_1:
                      state = 1;
                      i++;
                    }

                    break;
                  case 2:
                      goto Label_1;
                }

                return false;
              }

              public int Next()
              {
                return current;
              }
            }

            static MyStateMachine Fibs(int fibCount)
            {
              return MyStateMachine(6);
            }
          `}
        </code>
      </pre>
      <p>
        I trimmed the state-machine down to it's conceptual essence, so this example does not have
        all the components necessary to compile and run. The whole state-machine revolves around the <span
          class="pre">state</span
        >
        field, and the switch in the <span class="pre type">MoveNext</span> method. This example of a
        state-machine contains 3 different states:
      </p>
      <ul>
        <li><b>0</b>: The state-machine has just been initialized</li>
        <li><b>1</b>: The state-machine is currently processing a result</li>
        <li><b>2</b>: The state-machine has processed and returned a result</li>
      </ul>
      <p>
        These states give the illusion of continuity between jumping in and out of the
        state-machine, without having to allocate memory to store temporary data generated from a
        call to map or filter.
      </p>
      <p>
        The main idea to take away from the state-machine implementation is that there is <i
          >no allocation</i
        > for a list.
      </p>
    </div>
    <div anchor-button id="drawbacks">
      <h3>YIELD DRAWBACKS</h3>
      <p>
        Although you save memory from not generating a temporary buffer, there are some drawbacks to
        this approach.
      </p>
      <p>
        You add an extra keyword to the language vocabulary. Adding keywords to languages is not
        necessarily a bad thing, if they are reducing the amount of work you would have otherwise
        done in place of the keyword. In the case of the yield keyword, there is no substantial
        reduction of code that results from using it over mapping or filtering functionality. The
        benefit it gives is <i>performance</i>. You aren't allocating unnecessary memory, and thus
        the program runs faster.
      </p>
      <p>
        Another drawback is that because the yield keyword does some sneaky things behind the
        scenes, the state-machine construct can create some obscure bugs as well<FootnoteRef
          id="obscure-behavior"
        />.
      </p>
    </div>
  </div>
  <div anchor-button id="solution">
    <h1>FLAT'S SOLUTION</h1>
    <p>
      If the compiler were able to somehow know that the list of Fibonacci numbers generated from
      the Fib function were only used in place at that one location, then you wouldn't need to use
      the yield keyword to gain this performance, the compiler could optimize the map and filter
      functions to run in place. This is the solution that Flat will use. Take a look at the use of
      the <span class="pre">map</span> function in the Flat example again:
    </p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          public static main(String[] args) {
            for (fib in fibs(6)) {
              Console.writeLine("#fib ")
            }
          }

          static fibs(Int fibCount) -> Int[] {
            var prevFib = 0
            var currFib = 1

            return Int[fibCount].map({
              let value = prevFib

              prevFib = currFib
              currFib += value

              return value
            })
          }
        `}
      </code>
    </pre>
    <p>Now lets focus on the following section of the code:</p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          return Int[fibCount].map({
            let value = prevFib

            prevFib = currFib
            currFib += value

            return value
          })
        `}
      </code>
    </pre>
    <p>
      First off, that there is an explicit Int[] allocation, yet none of the values of the array are
      ever accessed or set. It is just used to specify the size of the returning list, and to call
      the map function that generates the list of Fibonacci numbers. This can be optimized out by
      inlining the contents of the map function from the Array class and replacing the foreach
      constraint with the size-constraint. The resulting code could look something like:
    </p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          let array = Int[fibCount]

          // Replaced with size constraint instead of "for (element in this)"
          for (i in 0..fibCount) {
            let value = prevFib

            prevFib = currFib
            currFib += value

            array.add(value)
          }

          return array
        `}
      </code>
    </pre>
    <p>Now lets see it all together again:</p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          public static main(String[] args) {
            for (fib in fibs(6)) {
              Console.writeLine("#fib ")
            }
          }

          static fibs(Int fibCount) -> Int[] {
            var prevFib = 0
            var currFib = 1

            let array = Int[fibCount]

            // Replaced with size constraint instead of "for (element in this)"
            for (i in 0..fibCount) {
              let value = prevFib

              prevFib = currFib
              currFib += value

              array.add(value)
            }

            return array
          }
        `}
      </code>
    </pre>
    <p>
      This took care of the first Int[] allocation and inlined the map lambda, but there is still
      one Int[] allocation left remaining. At this point, if we remove the remaining array, we loose
      the return value and thus change the meaning of the function. What if someone wanted to
      actually receive an array from the fibs function? To address this, Flat will create an
      Iterator not too different than how the state-machine is built. This iterator will be used in
      place of calling the fibs function.
    </p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          public static main(String[] args) {
            for (fib in FibsIterator(6)) {
              Console.writeLine("#fib ")
            }
          }

          class FibsIterator implements Iterator<Int> {
            var Int prevFib = 0
            var Int currFib = 1

            Int fibCount

            public construct(Int fibCount) {
              this.fibCount = fibCount
            }

            visible Bool hasNext => i < fibCount

            visible Int next {
              get {
                let value = prevFib

                prevFib = currFib
                currFib += value

                i++

                return value
              }
            }
          }
        `}
      </code>
    </pre>
    <p>
      As how functions that make use of the yield keyword in C# are used, The FibsIterator call will
      only replace the calls to fibs that are strictly used to <i>iterate over the values</i> and not
      to store them in a data structure.
    </p>
    <p>
      With these optimizations in place, you get the same performance you would get from using
      yield's state-machine, but you stay within the realm of maps and filters. The real goal and
      aspiration of this solution is to keep programmers within the realm of using map and filter.
      The more often these functions and data structures are used, the more libraries are compatible
      and consistent with each other.
    </p>
  </div>

  <div anchor-button id="conclusion">
    <h1>Conclusion</h1>
    <p>
      While in C#, using the yield keyword can give a performance boost based on less allocation, if
      the compiler could optimize out the allocation in the first place, there would be no need for
      such a keyword. Adding a keyword adds complexity to the language, and as stated before, the
      only benefit the yield keyword gives is performance. Because Flat can optimize out map and
      filter calls to the same performance of having used the yield keyword, there is no point in
      adding it to the language. This is why there will never be a yield keyword in Flat.
    </p>
    <p>
      And as a side note, there are even more optimizations that will be made to Iterators in the
      future to where the next and hasNext accessor properties will be inlined inside of the calling
      code. This will give another significant performance boost that even the C# state-machine
      cannot replicate. The point is, map and filter functionality can run <i>really</i> fast without
      having to introduce some special keyword that puts burden on the programmer to have to use in order
      to receive the performance boost.
    </p>
  </div>

  <div anchor-button id="footnotes">
    <h4>Footnotes:</h4>
    <Footnote id="csharp-yield-backend"
      >A more in-depth explanation of the back-end implementation of the C# yield return state
      machine can be found <a
        target="_blank"
        href="https://startbigthinksmall.wordpress.com/2008/06/09/behind-the-scenes-of-the-c-yield-keyword"
        >here</a
      >.</Footnote
    >
    <Footnote id="obscure-behavior"
      ><a target="_blank" href="http://www.daedtech.com/getting-too-cute-with-c-yield-return"
        >Obscure behavior</a
      > with C# yield return.</Footnote
    >
  </div>
</template>
