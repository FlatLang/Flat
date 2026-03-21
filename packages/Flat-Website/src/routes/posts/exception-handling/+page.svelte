<template lang="flat-html">
  <div anchor-button id="introduction">
    <h1>INTRODUCTION TO EXCEPTION HANDLING</h1>
    <p>
      Exception handling is the process of responding to the occurrence, during computation, of <i
        >exceptions</i
      >. Exception handling is one of the most common ways of dealing with errors within a program.
      Error handling is a big issue for programming languages. What do you do when a program doesn't
      act according to plan? Different languages have different answers to this problem.
    </p>
  </div>

  <div anchor-button id="methods">
    <h1>DIFFERENT METHODS OF ERROR HANDLING</h1>
    <div anchor-button id="return-values">
      <h3>RETURN VALUES</h3>
      <p>
        A common method of handling errors is to output whether a function was successful or not
        within the return value of the function. This is used in many lower-level languages such as
        C, C++, and Rust. Typically functional languages use this paradigm of error handling because
        it does not break the flow of the program. The newer langages that use this kind of error
        handling usually use a coupled structure as the return value that return the value, if
        available, and an error if there was one.
      </p>
      <p>
        Rust uses the Option data structure to declare that there might not be a result in some
        degenerative cases:
      </p>
      <pre>
          <code class="language-rust" style="margin: 40px 0;">
            {`
              fn file_path_ext_explicit(file_path: &str) -> Option<&str> {
                match file_name(file_path) {
                  None => None, // No result, so return None
                  Some(name) => match extension(name) { // Normal expected result
                    None => None,
                    Some(ext) => Some(ext),
                  }
                }
              }
            `}
          </code>
        </pre>
      <p>
        There is also the Result data structure used to declare that there might be an <i>error</i> in
        some degenerative cases:
      </p>
      <pre>
          <code class="language-rust" style="margin: 40px 0;">
            {`
              fn double_number(number_str: &str) -> Result<i32, ParseIntError> {
                match number_str.parse::<i32>() {
                  Ok(n) => Ok(2 * n), // Return normal expected result
                  Err(err) => Err(err) // This line returns the result as an error
                }
              }
            `}
          </code>
        </pre>
      <p>
        Many functions in languages like C will return a NULL value or 0 if there was an error
        during the function's execution. Type typical return values that indicate failure within a
        function's execution usually follow these rules:
      </p>
      <div anchor-button id="c-error-return-types" style="margin-left: 25px; margin-bottom: 20px;">
        <h4>For non-pointer return types</h4>
        <ul>
          <li><b>0</b>: success</li>
          <li><b>-1</b>: failure</li>
        </ul>
        <h4>For pointer return types</h4>
        <ul>
          <li><b>NULL</b>: failure</li>
          <li><b>Everything else</b>: success</li>
        </ul>
      </div>
      <p>
        The non-pointer return types can cause some ambiguity when the return values is supposed to
        be used for more than just error handling. For example, the atoi function in C is used to
        parse a string of text to an integer. The return type of the function is an int, but a
        return value of 0 indicates that the function failed. What if the input was a string value
        that represented 0? How would you differentiate "0" vs an error ocurring within the
        function? atoi does not include support for such functionality. This is why out parameters
        are used in many cases in C.
      </p>
    </div>
    <div anchor-button id="out-parameters">
      <h3>OUT PARAMETERS</h3>
      <p>
        Out parameters are parameters that are passed to a function by reference and require a value
        be assigned to them before the function is returned.
      </p>
      <p>
        This method is also common within C programs. Many functions have the following design
        pattern:
      </p>
      <pre>
          <code class="language-c" style="margin: 40px 0;">
            {`
              int my_function(char* success) {
                ...

                if (success) {
                  *success = 1;
                } else {
                  *success = 0;
                }

                ...
              }
            `}
          </code>
        </pre>
      <p>
        With this design pattern, you have to be a little more verbose with how you call functions:
      </p>
      <pre>
          <code class="language-c" style="margin: 40px 0;">
            {`
              char successful;

              int returnValue = my_function(&successful);

              if (!successful) {
                // deal with error
              }
            `}
          </code>
        </pre>
      <p>
        You are required to pass in the reference to a variable, which requires that you have a
        variable defined in the first place. While this addresses the ambiguity between return
        values vs errors, you pay the price in being verbose and the possible performance
        implications of having to allocate space for an extra variable used just for determining the
        success of a function.
      </p>
    </div>
    <div anchor-button id="global-variable">
      <h3>GLOBAL VARIABLE</h3>
      <p>
        Global variables are often to indicate the latest error in a program. An example of this is
        the errno library in C which is defined in the errno.h file. The errno library uses a single
        global integer variable <i>errno</i> to deal with errors in a program. The out parameter code
        we used before can be modified to follow this design pattern:
      </p>
      <pre>
          <code class="language-c" style="margin: 40px 0;">
            {`
              int my_function() {
                ...

                if (success) {
                  ... // do not set errno
                } else {
                  // some integer that corresponds to type of error
                  errno = 1;
                }

                ...
              }
            `}
          </code>
        </pre>
      <p>
        Instead of taking in a success out parameter, the function sets the global variable to a
        specific value <i>if</i> an error occurs. If no error occurs, then the errno global variable
        is not set. The function can be called without having to define a variable:
      </p>
      <pre>
          <code class="language-c" style="margin: 40px 0;">
            {`
              int returnValue = my_function();

              if (errno != 0) {
                // deal with error
              }
            `}
          </code>
        </pre>
      <p>
        This cleans up the function call some, but just as the other methods, it has some drawbacks
        to it as well. With a global variable being used, you have to be more careful in
        multi-threaded programs that are calling functions that read/write to the errno variable. To
        keep errno thread-safe, you have to use a mutex or semaphore. Both of those will impact the
        performance of the program.
      </p>
    </div>
    <div anchor-button id="design-by-contract">
      <h3>DESIGN BY CONTRACT</h3>
      <p>
        <a
          target="_blank"
          href="https://en.wikibooks.org/wiki/Computer_Programming/Design_by_Contract"
          >Design by Contract</a
        >
        (DbC) handles errors by requiring correct input and correct output. A function does this by specifying
        <b>pre-condition</b>
        and <b>post-condition</b> states that the function must fulfill to be considered a success.
      </p>
      <pre>
          <code class="language-flat" style="margin: 40px 0;">
            {`
              function myFunc(x: Float): Float
                pre-condition (x >= 0)
                post-condition (return >= 0)
              begin
                ...
              end
            `}
          </code>
        </pre>
      <p>
        This method of error handling is often coupled with exception handling. If a user were to
        call this function in such a way that violates the pre or post condition, an exception would
        be thrown. However, in some cases a pre/post condition can be validated at compile time. In
        these cases, a compile-time error can be used to prevent the function from returning an
        exception.
      </p>
      <p>
        Because DbC is often coupled with exception handling, it can be implemented in Flat. With
        annotations such as <span class="pre"
          >[PreCondition <span class="type">&lt;Bool expression&gt;</span>]</span
        >
        and
        <span class="pre">[PostCondition <span class="type">&lt;Bool expression&gt;</span>]</span>,
        DbC could have a place in Flat's future.
      </p>
      <pre>
          <code class="language-flat" style="margin: 40px 0;">
            {`
              [PreCondition x >= 0]
              [PostCondition return >= 0]
              myFunc(Float x) -> Float {
                ...
              }
            `}
          </code>
        </pre>
    </div>
    <div anchor-button id="thrown-exceptions">
      <h3>THROWN EXCEPTIONS</h3>
      <p>
        The standard Java/C# style of handling errors in code is to throw exceptions. This is done
        by defining an area in the code to <i>catch</i> exceptions, and an area to <i>throw</i> exceptions.
        A catch without any corresponding throws is useless. The common design pattern for try/catch/throw
        looks something like this:
      </p>
      <pre>
          <code class="language-flat" style="margin: 40px 0;">
            {`
              myFunc() {
                try {
                  sketchyFunction()
                } catch (Exception e) {
                  // deal with the exception
                }
              }

              sketchyFunction() {
                if (someInvalidCondition) {
                  throw Exception("Bad thing happened")
                } else {
                  // do whatever sketchyFunction is supposed to do
                }
              }
            `}
          </code>
        </pre>
      <p>
        However, unlike Java, exceptions are not required to be caught in Flat, so the following
        code is also valid.
      </p>
      <pre>
          <code class="language-flat" style="margin: 40px 0;">
            {`
              myFunc() {
                sketchyFunction()
              }

              sketchyFunction() {
                if (someInvalidCondition) {
                  throw Exception("Bad thing happened")
                } else {
                  // do whatever sketchyFunction is supposed to do
                }
              }
            `}
          </code>
        </pre>
      <p>
        This can be helpful for when you know the function won't have an error, or you just don't
        want to deal with any exceptions from it.
      </p>
    </div>
  </div>

  <div anchor-button id="flat-error-handling">
    <h1>FLAT'S ERROR HANDLING</h1>
    <p>
      Flat uses exceptions to handle errors. The reason for this choice stems from several factors:
    </p>
    <ul>
      <li>
        <b>Return values</b>, if not using a Result&lt;Type, Error&gt; or Optional&lt;Type&gt; type,
        can be ambiguous.
      </li>
      <li>
        <b>Return values</b>, if using a Result&lt;Type, Error&gt; or Optional&lt;Type&gt; type, can
        be verbose and impact performance (e.g. unwrapping, packaging return value, etc).
      </li>
      <li>
        <b>Out parameters</b> are not even supported in Flat because they almost always cause unnecessary
        complexities.
      </li>
      <li>
        <b>Global variables</b> are not thread-safe and are generally accepted as bad practice.
      </li>
      <li><b>Design by contract</b> is compatible with exception handling.</li>
    </ul>
    <div anchor-button id="toss">
      <h3>TOSS</h3>
      <p>
        In addition to traditional try/catch/throw functionality, Flat also includes "toss"
        functionality. Tossing an exception is analogous to throwing an exception. The difference is
        that when you toss an exception, it only breaks the execution flow if there is a catch block
        waiting for that specific type of exception. For example, you can replace the throw keyword
        with toss:
      </p>
      <pre>
          <code class="language-flat" style="margin: 40px 0;">
            {`
              myFunc() {
                sketchyFunction()
              }

              sketchyFunction() {
                if (someInvalidCondition) {
                  toss Exception("Bad thing happened")
                } else {
                  // do whatever sketchyFunction is supposed to do
                }

                // OTHER CALCULATIONS AND STUFF
              }
            `}
          </code>
        </pre>
      <p>
        The difference that this has on the execution flow is that instead of stopping at the <span
          class="pre">toss Exception("Bad thing happened")</span
        >
        as it would have with <span class="pre">throw Exception("Bad thing happened")</span>, the
        code will continue to execute. Now if we were to catch the exception, you would have a
        different execution flow:
      </p>
      <pre>
          <code class="language-flat" style="margin: 40px 0;">
            {`
              myFunc() {
                try {
                  sketchyFunction()
                } catch (Exception e) {
                  // deal with exception
                }
              }

              sketchyFunction() {
                if (someInvalidCondition) {
                  toss Exception("Bad thing happened")
                } else {
                  // do whatever sketchyFunction is supposed to do
                }

                // OTHER CALCULATIONS AND STUFF
              }
            `}
          </code>
        </pre>
      <p>
        In this example, the toss would behave just as a throw because there is a catch block
        waiting for an exception.
      </p>
    </div>
  </div>
</template>
