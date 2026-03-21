<template lang="flat-html">
  <div anchor-button id="working-with-external-apis">
    <h1>WORKING WITH EXTERNAL APIS</h1>
    <p>
      When dealing with multiple targets you are often required to call upon external APIs. There
      are a couple ways you could do this:
    </p>
    <ul>
      <li>Call them directly with external code blocks</li>
      <li>Create bindings to the API in Flat</li>
    </ul>
    <div anchor-button id="direct-calling">
      <h3>CALLING EXTERNAL CODE DIRECTLY</h3>
      <p>
        The quickest (and dirtiest) way of working with external libraries is to call the external
        code directly. Flat provides external blocks that are used to achieve this functionality:
      </p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            myFunc() {
              external c {
                printf("IN EXTERNAL C!\\n");
              }
            }
          `}
        </code>
      </pre>
      <p>
        This is really only helpful if you can pass variables and functions to and from the external
        code. This example shows how to pass a variable to the external code:
      </p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            myFunc() {
              Int myInt = 100

              external c {
                // The number is 100!
                printf("The number is %d!\\n", #{myInt});
              }
            }
          `}
        </code>
      </pre>
      <p>
        Passing variables from external code to Flat is done by assigning the Flat variable inside
        the external code:
      </p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            myFunc() {
              Int myInt

              external c {
                #{myInt} = 100;

                // The number is 100!
                printf("The number is %d!\\n", #{myInt});
              }

              // The number is 100 in Flat too!
              Console.writeLine("The number is #myInt in Flat too!")
            }
          `}
        </code>
      </pre>
      <p>You can also run Flat statements inside external code:</p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            getMyInt() -> Int {
              Int myInt

              external c {
                #{myInt} = 100

                #{return myInt * 2};
              }
            }
          `}
        </code>
      </pre>
    </div>
    <div anchor-button id="binding-apis">
      <h3>CREATING BINDINGS TO API</h3>
      <p>
        Calling external code through external blocks is the basis of how creating bindings to an
        API works in Flat. When creating an API for a language, you typically do it by hand. For
        example, if you were to create bindings for some std output functions in C, you could do it
        like:
      </p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            public atoi(String s) -> Int {
              external c {
                return atoi(#{s.chars.data});
              }
            }

            public random() -> Long {
              external c {
                return rand();
              }
            }

            public currentTimeMillis() -> Long {
              external c {
                return current_time_millis();
              }
            }
          `}
        </code>
      </pre>
      <p>
        This creates bindings to the <span class="pre">atoi</span>, <span class="pre">random</span>,
        and <span class="pre">current_time_millis</span> functions in C. Doing this by hand can be very
        tedious and susceptible to errors. Because of this, automated importing of APIs would be immensely
        useful.
      </p>
    </div>
  </div>
  <div anchor-button id="automated-api-importing">
    <h1>AUTOMATED API IMPORTING</h1>
    <p>
      Because you can <a href="/posts/compiler-design#parser-uses">write custom parsers</a>, adding
      API importing functionality will be surprisingly simple. If a parser for a language is
      available, instead of translating it to Flat, you could pass an -api argument to the compiler
      to have it generate the API binding for the functions in the input library. For example, say
      you want to import the C standard library. Using a C header file parser, you could import all
      stdlib functions with this command:
    </p>
    <code class="language-bash" style="margin: 40px 0;">
      flatc path/to/c/stdlib -input c-header -api -d flat-c-stdlib
    </code>
    <p>
      This would output the translated API binding for the c stdlib into a folder named
      flat-c-stdlib. You could then reference this binding in your project like the following:
    </p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          import static "stdlib"

          class MyClass {
            public static main(String[] args) {
              let path = getenv("PATH")

              let myStringPath = String(path)

              Console.writeLine("Path: #myStringPath")
            }
          }
        `}
      </code>
    </pre>
    <p>
      The first line <span class="pre modifier"
        >import static <span class="text">"stdlib"</span></span
      >
      imports our API generated binding's static functions (all C functions are considered static in
      the context of Flat). Then we use the external binding at
      <span class="pre">let path = getenv(<span class="text">"PATH"</span>)</span>. Because getenv
      returns a native char array, you have to convert it to a String before you can output it as
      one at
      <span class="pre">Console.writeLine(<span class="text">"Path: #myStringPath"</span>)</span>.
    </p>
    <p>
      To include the API binding in your project's compilation process, you would simply include
      flat-c-stdlib as one of your compilation sources:
    </p>
    <code class="language-bash" style="margin: 40px 0;">
      flatc MyProject flat-c-stdlib -o MyExecutable
    </code>
    <p>This will compile your project and output your executable as MyExecutable.</p>
  </div>

  <div anchor-button id="conclusion">
    <h1>CONCLUSION</h1>
    <p>
      Automated importing of external APIs plays an integral part in making compilation to
      multiple-targets more powerful. It alleviates many of the issues that normally come up when
      writing programs that interface with multiple different languages. Automated importing of
      external APIs <b>expidiates</b> the process of multi-target development, while making it
      <b>safe</b>. Both of which are fundamental to the design of the Flat language.
    </p>
  </div>
</template>
