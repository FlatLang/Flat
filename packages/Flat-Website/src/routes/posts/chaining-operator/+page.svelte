<template lang="flat-html">
  <div anchor-button id="introduction">
    <h1>INTRODUCING THE CHAINING OPERATOR ":"</h1>
    <p>
      The "Chaining Operator" <code>{`: // <=== this colon guy`}</code> is a new idea in the realm
      of programming languages. It allows you to call a function on object <code>x</code> and
      override the return type of that function so that it returns the object reference for
      <code>x</code>.
    </p>
    <h6>But why?</h6>
    <p>
      Why is the chaining operator helpful? In some situations the return value of a function call
      is not needed, and in those cases it could be useful to call another function on that same
      object without re-referencing the value that invoked the function call. This can especially be
      useful for void functions. Where in traditional programming languages you would need to
      respecify the reference value again if you want to call another function call, with the
      chaining operator that is no longer necessary.
    </p>
  </div>

  <div anchor-button id="examples">
    <h1>EXAMPLES</h1>
    <div anchor-button id="logging">
      <h3>LOGGING</h3>
      <p>Here is a one-liner that outputs three info messages:</p>
      <pre class="left">
        <code class="language-flat">
          {`
            log:info("one"):info("two"):info("three")

            // or

            log
              :info("one")
              :info("two")
              :info("three")
          `}
        </code>
      </pre>
      <p>
        Without the chaining operator, the same would have to be achieved with a solution resembling
        this:
      </p>
      <pre class="left">
        <code class="language-flat">
          {`
            log.info("one")
            log.info("two")
            log.info("three")
          `}
        </code>
      </pre>
    </div>
    <div anchor-button id="networking">
      <h3>NETWORKING</h3>
      <p>
        Here is an example that demonstrates starting and closing a ServerSocket on a single line:
      </p>
      <pre class="left">
        <code class="language-flat">
          {`
            ServerSocket():start(8080):close()
          `}
        </code>
      </pre>
      <p>
        Without the chaining operator, the same would have to be achieved with a solution resembling
        this:
      </p>
      <pre class="left">
        <code class="language-flat">
          {`
            let server = ServerSocket()
            server.start(8080)
            server.close()
          `}
        </code>
      </pre>
    </div>
    <div anchor-button id="string-building">
      <h3>STRING BUILDING</h3>
      <p>A common use of the chaining operator is for string building:</p>
      <pre class="left">
        <code class="language-flat">
          {`
            let builder = StringBuilder()
              :append("this ")
              :append("is ")
              :append("an ")
              :append("example")

            log.info(builder) // outputs: "this is an example"
          `}
        </code>
      </pre>
      <p>or more concisely:</p>
      <pre class="left">
        <code class="language-flat">
          {`
            log.info(
              StringBuilder()
                :append("this ")
                :append("is ")
                :append("an ")
                :append("example")
            ) // outputs: "this is an example"
          `}
        </code>
      </pre>
      <p>which are both functionally equivalent to:</p>
      <pre class="left">
        <code class="language-flat">
          {`
            let builder = StringBuilder()

            builder.append("this ")
            builder.append("is ")
            builder.append("an ")
            builder.append("example")

            log.info(builder) // outputs: "this is an example"
          `}
        </code>
      </pre>
    </div>
    <div anchor-button id="list-processing">
      <h3>LIST PROCESSING</h3>
      <p>Call forEach multiple times on a List</p>
      <pre class="left">
        <code class="language-flat">
          {`
            let myInts = [1, 2, 3, 4]
            var sum = 0

            myInts
              .map({ _ * 2 })
              :forEach({ sum += _ })
              :forEach({ sum += 1 })
              :forEach({ sum += 3 })

            expect(sum).toBe(36)
          `}
        </code>
      </pre>
    </div>
  </div>
</template>
