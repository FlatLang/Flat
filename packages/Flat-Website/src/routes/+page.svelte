<script lang="ts">
  import Footer from '$lib/components/Footer.svelte';

  import { slide } from '$lib/slide';
</script>

<svelte:head>
  <title>Flat Programming Language</title>

  <link
    href="/styles/home.css"
    rel="stylesheet"
    type="text/css"
  />
</svelte:head>

<template lang="flat-html">
  <div use:slide id="splashscreen" class="full slide">
    <div class="centered-absolute logo-container">
      <img
        id="logo-main"
        class="glow-logo"
        src="/images/hex-transparent-white.svg"
        alt="Flat Logo"
        width="100%"
        height="100%"
      />
      <h1 class="bold splash-header" style="margin: 5vh 0 2.5vh 0;">FLAT PROGRAMMING LANGUAGE</h1>
      <h2 class="motto splash-subheader" style="margin: 2.5vh 0 3vh 0;">
        A high-level multi-paradigm programming language used as an intermediary between
        transpilation
      </h2>
      <div class="download">
        <a class="neon-shadow download-button-link" href="/download">
          <h1 style="margin-top: 0;" class="download-button">DOWNLOAD FLAT</h1>
        </a>
      </div>
    </div>
    <a href="/">
      <h1 class="flat-text" style="color: rgba(0,0,0,0);">FLAT</h1>
      <h1 class="flat-text">FLAT</h1>
    </a>
    <div class="home-navigation">
      <a class="posts-link" href="/posts">POSTS</a>
      <a class="documentation-link" href="/docs">DOCUMENTATION</a>
    </div>
  </div>
  {#if false}
    <div use:slide id="code-example" class="slide">
      <h1 class="bold">FLAT SYNTAX</h1>
      <div class="example tree">
        <div class="description">
          <h2>Trees and Lists</h2>
          <p>
            Flat's List data structure makes processing lists of data easy. With the use of lambda
            expressions, the code to perform operations can be concise. Like many modern programming
            languages, Flat lets you use lambda expressions and functions as first class values.
          </p>
          <p>
            In the code example on the right, you can see string interpolation in action. The
            strings passed to <code class="language-flat">log.info()</code>
            show this. String interpolation makes injecting variable values into strings effortless.
            You just use <code class="language-flat">{`#{ expression }`}</code> within a string literal
            and the compiler does the rest for you.
          </p>
          <p>
            One cool thing about the way List's are implemented in Flat is that because it is a
            trait, any class can implement it and they only have to define an Iterator for the class
            for it to gain access to all of the functional capabilities of a List.
          </p>
        </div>
        <div class="code-container">
          <pre class="full">
            <code class="language-flat code dark-background indent-children">
              {`
                let tree = new BinaryTree(["F", "B", "A", "D", "C", "E", "G", "I", "H"])

                log.info("Preorder: #{tree.preorder().join(", ")}")

                // Preorder: [F, B, A, D, C, E, G, I, H]

                log.info("Inorder: #{tree.inorder().join(", ")}")

                // Inorder: [A, B, C, D, E, F, G, H, I]

                log.info("Postorder: #{tree.postorder().join(", ")}")

                // Postorder: [A, C, E, D, B, H, I, G, F]

                log.info("Levelorder: #{tree.levelorder().join(", ")}")

                // Levelorder: [F, B, G, A, D, I, C, E, H]
              `}
            </code>
          </pre>
        </div>
      </div>
      <div class="example traits">
        <div class="description">
          <h2>Class types</h2>
          <p>Flat contains three different types of class structures:</p>
          <ul>
            <li>Classes</li>
            <li>Interfaces</li>
            <li>Traits</li>
          </ul>
          <p>
            Each of these has their own strengths and weaknesses. For instance, you can only inherit
            from one class at most. In contrast, you can inherit from as many traits and interfaces
            as you want. Because of this, traits and interfaces cannot have formal constructors.
            Traits differ from interfaces in such that traits can define method bodies and class
            properties. Interfaces are just like traditional Java interfaces; they only contain
            abstract method declarations.
          </p>
          <p>
            Using the code shown, you can create an instance of <span class="pre type">MyClass</span
            >
            with an instantiation as such:
            <span class="pre"
              ><span class="type">let</span> <span class="identifier">myObj</span> =
              <span class="modifier">new</span> <span class="type">MyClass</span>()</span
            >. Then you can call any of the functions defined in the class or its ancestors (<span
              class="pre type">MyTrait</span
            >
            and <span class="pre type">MyInterface</span>). You could call:
          </p>
          <ul>
            <li>
              <span class="pre"
                ><span class="identifier">myObj</span>.<span class="identifier">saySomething</span
                >()</span
              >
              which would output <span class="pre">My value is: 'my value'</span>
            </li>
            <li>
              <span class="pre"
                ><span class="identifier">myObj</span>.<span class="identifier">calculateValue</span
                >()</span
              >
              which returns a String <span class="pre text">"my value"</span>
            </li>
            <li>
              <span class="pre"
                ><span class="identifier">myObj</span>.<span class="identifier">plus</span>(5)</span
              >
              which adds 5 to
              <span class="pre"
                ><span class="identifier">myObj</span>.<span class="identifier">id</span></span
              >
              and returns <span class="pre identifier">myObj</span>
            </li>
          </ul>
        </div>
        <div class="code-container">
          <div class="dark-background indent-children">
            <pre class="full">
              <code class="language-flat code dark-background indent-children">
                {`
                  class MyClass implements MyTrait, MyInterface {
                    public calculateValue() => "my value"

                    public saySomething() {
                      log.info("My value is: '#calculateValue()'")
                    }
                  }

                  trait MyTrait implements PlusOperator<Int, MyTrait> {
                    visible Int id

                    public plus(Int num) -> MyTrait {
                      id += num

                      return this
                    }
                  }

                  interface MyInterface {
                    public calculateValue() -> String
                  }
                `}
              </code>
            </pre>
          </div>
        </div>
      </div>
    </div>
    <div use:slide id="code-comparisons" class="dark-background slide">
      <h1>FLAT COMPILATION TARGETS</h1>
      <div class="comparison">
        <div class="explanation transition from-left time08 delay0">
          <h3>FLAT <span class="monospace">-&gt;</span> JAVASCRIPT</h3>
          <p>
            The Flat compiler generates Javascript code with a similar syntactical structure to the
            original source code. When using Flat to write Javascript code you get the benefit of a
            statically typed language with compile-time optimizations without losing the versatility
            of Javascript. This makes it safe and natural to write libraries in Flat and use them as
            libraries in plain Javascript.
          </p>
        </div>
        <div class="example-code">
          <div class="l bracket-container hack transition time08 delay0">
            <div class="l bracket" />
          </div>
          <div class="container">
            <div>
              <div class="transition from-bottom time08 delay05">
                <h4>FLAT CODE:</h4>
                <pre>
                  <code class="language-flat code dark-background indent-children">
                    {`
                      public static main() {
                        let names = ["Braden", "Ethan", "George"]

                        for (name in names) {
                          log.info("Hello, #name!")
                        }
                      }
                    `}
                  </code>
                </pre>
              </div>
            </div>
            <div>
              <hr class="white" />
            </div>
            <div>
              <div class="transition from-top time08 delay05">
                <h4>GENERATED JAVASCRIPT CODE:</h4>
                <pre>
                  <code class="language-js code dark-background indent-children">
                    {`
                      let names = new FlatArray(["Braden", "Ethan", "George"]);

                      let iterator = names.iterator();

                      while (iterator.hasNext()) {
                        let name = iterator.next();
                        log.info("Hello, " + name + "!");
                      }
                    `}
                  </code>
                </pre>
              </div>
            </div>
          </div>
          <div class="r bracket-container hack transition time08 delay0">
            <div class="r bracket" />
          </div>
          <div class="bracket-cover hack transition time08 delay0" />
        </div>
      </div>
      <div class="comparison">
        <div class="explanation transition from-right time08 delay02">
          <h3>FLAT <span class="monospace">-&gt;</span> JAVA</h3>
          <p>
            Flat's syntax takes a lot from Java's syntax. Because of this, the generated Java code
            is readable and works with existing Java libraries seamlessly. This allows for writing
            libraries for anything that deals with Java code (e.g., servers and mobile development).
            For instance, you can write Android compatible code in Flat. A benefit of writing
            Android compatible code in Flat is that when the Swift compilation target is written,
            you will be able to compile existing compatible code to both Android and Swift.
            Essentially you get the best of both worlds.
          </p>
        </div>
        <div class="example-code">
          <div class="l bracket-container hack transition time08 delay02">
            <div class="l bracket" />
          </div>
          <div class="container">
            <div>
              <div class="transition from-bottom time08 delay05">
                <h4>FLAT CODE:</h4>
                <pre>
                  <code class="language-flat code dark-background indent-children">
                    {`
                      public static main() {
                        let names = ["Braden", "Ethan", "George"]

                        for (name in names) {
                          log.info("Hello, #name!")
                        }
                      }
                    `}
                  </code>
                </pre>
              </div>
            </div>
            <div>
              <hr class="white" />
            </div>
            <div>
              <div class="transition from-top time08 delay05">
                <h4>GENERATED JAVA CODE:</h4>
                <pre>
                  <code class="language-java code dark-background indent-children">
                    {`
                      public static void main(String[] args) {
                        Array<String> names = new Array<>(
                          new String[] { "Braden", "Ethan", "George" }
                        );

                        Iterator<String> iterator = names.iterator();

                        while (iterator.hasNext()) {
                          String name = iterator.next();
                          log.info("Hello, " + name + "!");
                        }
                      }
                    `}
                  </code>
                </pre>
              </div>
            </div>
          </div>
          <div class="r bracket-container hack transition time08 delay02">
            <div class="r bracket" />
          </div>
          <div class="bracket-cover hack transition time08 delay02" />
        </div>
      </div>
      <div class="comparison">
        <div class="explanation transition from-left time08 delay04">
          <h3>FLAT <span class="monospace">-&gt;</span> C</h3>
          <p>
            Despite being a notably different paradigm, the Flat C compiler is currently the most
            mature of the compiler targets. The ability to compile to C allows compilation on a vast
            amount of high-level and low-level devices. All that is required is a C99 compiler to
            compile the generated C code.
          </p>
        </div>
        <div class="example-code">
          <div class="l bracket-container hack transition time08 delay04">
            <div class="l bracket" />
          </div>
          <div class="container">
            <div>
              <div class="transition from-bottom time08 delay05">
                <h4>FLAT CODE:</h4>
                <pre>
                  <code class="language-flat code dark-background indent-children">
                    {`
                      public static main() {
                        let names = ["Braden", "Ethan", "George"]

                        for (name in names) {
                          log.info("Hello, #name!")
                        }
                      }
                    `}
                  </code>
                </pre>
              </div>
            </div>
            <div>
              <hr class="white" />
            </div>
            <div>
              <div class="transition from-top time08 delay05">
                <h4>GENERATED C CODE:</h4>
                <pre>
                  <code class="language-c code dark-background indent-children">
                    {`
                      int main(int argc, char** argvs) {
                        flat_Array* names = flat_Array_construct(
                          flat_String_construct("Braden"),
                          flat_String_construct("Ethan"),
                          flat_String_construct("George")
                        );

                        flat_ArrayIterator* iterator = flat_Array_iterator(names);

                        while (flat_ArrayIterator_hasNext(iterator)) {
                          flat_String* name = (flat_String*)flat_ArrayIterator_next(iterator);

                          flat_Logger_info(
                            log,
                            flat_String_concat(
                              flat_String_concat(
                                flat_String_construct(0, "Hello, "),
                                name
                              ),
                              flat_String_construct(0, "!")
                            )
                          );
                        }

                        return 0;
                      }
                    `}
                  </code>
                </pre>
              </div>
            </div>
          </div>
          <div class="r bracket-container hack transition time08 delay04">
            <div class="r bracket" />
          </div>
          <div class="bracket-cover hack transition time08 delay04" />
        </div>
      </div>
    </div>
    <div use:slide id="target-benefits" class="white-background transparent slide">
      <h1>BENEFITS OF MULTIPLE COMPILATION TARGETS</h1>
      <div class="component-container">
        <div class="component">
          <h2>Improved performance analysis</h2>
          <p>
            The ability to compile and run the same program on multiple different devices and
            architectures allows more in-depth performance analysis to take place. Instead of
            strictly gauging the performance of the program based on the single language it was
            written in, you are able take a step back and compare the performance against all
            supported languages and the devices they can be run on. This capability changes the
            performance analysis from being focused on the specific language architecture to being
            focused on the performance of the algorithms used in the Flat program.
          </p>
        </div>
        <div class="component transition from-invisible time25 delay0" style="text-align: center;">
          <img
            loading="lazy"
            class="transition from-right time10 delay0"
            style="width: 90%; max-width: 400px;"
            src="/images/chart.svg"
            alt="Chart"
            width="100%"
            height="100%"
          />
        </div>
      </div>
      <div class="component-container">
        <div class="component">
          <h2>Improved compatibility</h2>
          <p>
            The potential to compile to multiple target languages eliminates much of the need to
            learn a new language and/or environment when tackling new devices for your software. Any
            libraries that are written in Flat are available for any device and architecture that
            you are focusing on for your product. Not only does this make it easier for existing
            developers of the product, it also makes it easier to integrate new developers into the
            workflow without needing to expose a whole new environment to them.
          </p>
        </div>
        <div class="component transition from-invisible time25 delay02" style="text-align: center;">
          <img
            loading="lazy"
            class="transition from-left time10 delay02"
            style="width: 90%; max-width: 400px;"
            src="/images/compatibility.svg"
            alt="Compatibility"
            width="100%"
            height="100%"
          />
        </div>
      </div>
    </div>
    <div use:slide id="stay-up-to-date2" class="stay-up-to-date dark-background monospace slide">
      <div class="content primary-border">
        <p class="header indent bold">
          <span class="secondary5">public</span> <span class="secondary4">stayUpToDate</span>(<span
            class="nowrap secondary1">String <span class="secondary4">name</span></span
          >, <span class="nowrap secondary1">String <span class="secondary4">email</span></span>)
          <span class="nowrap">-> <span class="secondary1">Email</span></span>
        </p>

        <hr class="primary-border" />
        <p class="comment bold">// keep updated with the latest news and releases</p>
        <p class="indent">
          <span class="secondary4">stayUpToDate</span>(<span class="nowrap"
            ><span class="secondary4">name</span>:
            <span class="secondary3"
              >"<span
                contenteditable="true"
                placeholder="your name"
                class="secondary3 secondary3-border"
              />"</span
            >,</span
          >
          <span class="nowrap"
            ><span class="secondary4">email</span>:
            <span class="secondary3"
              >"<span
                contenteditable="true"
                placeholder="your@email.com"
                class="secondary3 secondary3-border"
              />"</span
            ></span
          >)
        </p>
        <p class="comment bold">// hit enter to submit</p>
      </div>
    </div>
    <Footer />
  {/if}
</template>
