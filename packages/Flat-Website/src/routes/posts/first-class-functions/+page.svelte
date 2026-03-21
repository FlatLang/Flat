<template lang="flat-html">
  <div anchor-button id="how-they-work">
    <h1>HOW FIRST-CLASS FUNCTIONS WORK</h1>
    <p>
      First-class functions in their essence are just variables that point to executable code
      (functions). However, there are some key aspects that define what a first-class function
      means:
    </p>
    <ul>
      <li>Can pass functions as arguments to other functions</li>
      <li>Can return a function as a return value from a function</li>
      <li>Can assign functions to variables</li>
      <li>Can store functions in collections</li>
    </ul>
    <p>
      These points boil down to the fact that <i>functions should act like variables</i>.
    </p>
    <div anchor-button id="function-as-parameters">
      <h3>PASSING FUNCTIONS AS ARGUMENTS TO OTHER FUNCTIONS</h3>
      <p>
        This functionality is already available in Flat, and has been since the first Beta release.
        Defining a function that takes a function as an argument looks like:
      </p>
      <pre>
        <code class="language-flat">
          {`
            func1(myFirstClassFunc(String, Int)) {
              for (i in 0..10) {
                myFirstClassFunc("iteration #i", i)
              }
            }
          `}
        </code>
      </pre>
      <p>
        This function requires a function that takes at most String and Int as a parameter. I say <i
          >at most</i
        > because the functions that are passed to func1 are not required to take all of the parameters
        that the myFirstClassFunc parameter has. For example, you could match the myFirstClassFunc parameter
        definition exactly like:
      </p>
      <pre>
        <code class="language-flat">
          {`
            exactFunc(String s, Int x) {
              Console.writeLine("String count * x == #{s.count * x}")
            }

            func1(exactFunc) // valid
          `}
        </code>
      </pre>
      <p>Or you could give it a function that only contains 1 or even 0 parameters:</p>
      <pre>
        <code class="language-flat">
          {`
            zeroFunc() {
              Console.writeLine("Im pretty useless, but thats ok")
            }

            oneFunc(String s) {
              Console.writeLine("I only took the String parameter: #s")
            }

            func1(zeroFunc) // valid
            func1(oneFunc) // valid
          `}
        </code>
      </pre>
      <p>
        Passing functions as arguments to functions is one of the core aspects of the mapping and
        filtering functionality of the List framework.
      </p>
    </div>
    <div anchor-button id="functions-return-functions">
      <h3>RETURNING FUNCTIONS AS RETURN VALUES</h3>
      <p>Returning functions from functions looks like this:</p>
      <pre>
        <code class="language-flat">
          {`
            otherFunc(String x, Int y) {
              Console.writeLine("Received #x and #y")
            }

            func1() -> myFirstClassFunc(String, Int) {
              return otherFunc
            }
          `}
        </code>
      </pre>
      <p>
        The func1 returns a function that takes a String and Int as parameters. In this case, it
        just returns otherFunc, but you can also do some more interesting things like this:
      </p>
      <pre>
        <code class="language-flat">
          {`
            otherFunc(String x, Int y) {
              Console.writeLine("Received #x and #y")
            }

            func1(String input) -> myFirstClassFunc(String, Int) {
              return (x, y) => {
                for (i in 0..5) {
                  Console.writeLine("Parameter: #input")

                  otherFunc(x, y * i)
                }
              }
            }
          `}
        </code>
      </pre>
      <p>
        This func1 returns a lambda (aka anonymous function) that takes a String and Int. The types
        of the parameters for the lambda are deduced from the return type of the func1 function.
        Calling the function that is returned from the function would write func1's input parameter
        to the console 5 times, as well as calling 'otherFunc' 5 times.
      </p>
    </div>
    <div anchor-button id="functions-as-variables">
      <h3>ASSIGNING FUNCTIONS TO VARIABLES</h3>
      <p>Assigning functions to variables looks like this:</p>
      <pre>
        <code class="language-flat">
          {`
            func1(String input) -> myFirstClassFunc(String, Int) {
              return (x, y) => {
                for (i in 0..5) {
                  Console.writeLine("Parameter: #input")

                  otherFunc(x, y * i)
                }
              }
            }

            var x = func1
          `}
        </code>
      </pre>
      <p>
        This saves the func1 reference into the variable variable x. You can then invoke the x
        function reference like this:
      </p>
      <pre>
        <code class="language-flat">
          {`
            x("pass string") // valid
            x("pass string", 1) // valid
            x("pass string", 1, "asdfasdf") // valid
            x("pass string", null, 90.0, 100) // valid
          `}
        </code>
      </pre>
      <p>
        You can pass as many arguments to the function as you want, as long as you fulfill the
        original functions parameters. The original parameters, in this instance, are a single
        String. However, the extra parameters that are passed to the function will be never used.
      </p>
    </div>
    <div anchor-button id="functions-in-collections">
      <h3>STORING FUNCTIONS IN COLLECTIONS</h3>
      <p>
        Storing functions in collections extends upon the ability to assign to them variables. For
        instance, when able to store functions in collections, this is possible:
      </p>
      <pre>
        <code class="language-flat">
          {`
            func1(String input) -> myFirstClassFunc(String, Int) {
              return (String x, Int y) => {
                for (i in 0..5) {
                  Console.writeLine("Parameter: #input")

                  otherFunc(x, y * i)
                }
              }
            }

            let array = new function(String)[]

            array.add(func1)
            array.add(x => {
              Console.writeLine("In lambda that was passed #x")
            })
            array.add({
              Console.writeLine("In lambda that takes no parameters")
            })
          `}
        </code>
      </pre>
      <p>
        The <span class="pre">array</span> array stores functions that, at most, take a String as a parameter.
        You could later call the functions that are contained in this array like this:
      </p>
      <pre>
        <code class="language-flat">
          {`
            array.forEach(func => {
              func("say something")
            })
          `}
        </code>
      </pre>
      <p>This would output:</p>
      <pre>
        <code class="language-bash">
          {`
            In lambda that was passed say something
            In lambda that takes no parameters
          `}
        </code>
      </pre>
      <p>
        The first <span class="pre">func1</span> value does not output anything because the function
        that it <i>returned</i> was not invoked.
      </p>
    </div>
  </div>

  <div anchor-button id="use-cases">
    <h1>USE CASES IN FLAT</h1>
    <div anchor-button id="runtime-module-loading">
      <h3>RUNTIME MODULE LOADING</h3>
      <p>
        First-class functions have an important role in loading modules at runtime. When you load a
        module at runtime you can access fields and functions from that module dynamically.
        Accessing fields is pretty self explanatory; you are returned the value of the field.
        Functions on the other hand require a function reference to be returned in order to be
        useful.
      </p>
    </div>
    <div anchor-button id="functional-paradigm">
      <h3>FUNCTIONAL PARADIGM</h3>
      <p>
        Functional coding also has a inherent need for first-class functions. When programming in
        the functional paradigm, you expect functions to behave like variables because thats
        essentially what the paradigm is built off of.
      </p>
    </div>
    <div anchor-button id="concise">
      <h3>MORE CONCISE CODE</h3>
      <p>
        First-class functions can reduce the amount of code to perform tasks. This is an important
        point considering one of the founding principles of Flat is <i>speed of development</i>.
        When you reduce the amount of code to solve a problem, you often times are reducing the time
        it takes to complete the task.
      </p>
      <p>
        This can be demonstrated with the <a
          target="_blank"
          href="https://en.wikipedia.org/wiki/Strategy_pattern">strategy pattern</a
        >
        design pattern. This pattern is used when you want to run different algorithms based off of different
        conditions, <i>at runtime</i>. To illustrate this, lets suppose that you want to enforce a
        specific pricing algorithm depending on the type of the day. With the strategy pattern you
        could do this:
      </p>
      <pre>
        <code class="language-flat">
          {`
            interface Billable {
              calculatePrice(Double originalPrice)
            }

            class NormalHours implements Billable {
              calculatePrice(Double originalPrice) => originalPrice
            }

            class HappyHour implements Billable {
              calculatePrice(Double originalPrice) => originalPrice / 2
            }

            class SurgeHour implements Billable {
              calculatePrice(Double originalPrice) => originalPrice * 2
            }

            class Customer {
              Billable billStrategy = NormalHours()

              Double sum = 0

              public charge() {
                sum += billStrategy.calculatePrice(5)
              }
            }

            public static main(String[] args) {
              let c1 = Customer()

              c1.charge() // 5

              //surge hour
              c1.billStrategy = SurgeHour()
              c1.charge() // 10

              // happy hour
              c1.billStrategy = HappyHour()
              c1.charge() // 2.5
              c1.charge() // 2.5
            }
          `}
        </code>
      </pre>
      <p>
        The customer would leave with a bill of $20. This same process could be done with
        first-class functions like this:
      </p>
      <pre>
        <code class="language-flat">
          {`
            class Customer {
              var billStrategy(Double) -> Double = x => x

              Double sum = 0

              public charge() {
                sum += billStrategy(5)
              }
            }

            public static main(String[] args) {
              let c1 = Customer()

              c1.charge() // 5

              //surge hour
              c1.billStrategy = x => x * 2
              c1.charge() // 10

              // happy hour
              c1.billStrategy = x => x / 2
              c1.charge() // 2.5
              c1.charge() // 2.5
            }
          `}
        </code>
      </pre>
      <p>
        This code is much shorter and accomplishes the same result. The first-class function method
        reduces the amount of allocation required to complete the task, as well as the amount of
        code. However, some people might argue that the strategy pattern is more readable and
        scalable. If you are well versed with first-class functions, then the first implication of
        the argument is false, however the the scalability of the strategy pattern is a valid point.
        If you need to add extra fields to a specific billing algorithm, then the strategy pattern
        might be best. But for simple routine function abstract algorithms, first-class functions is
        often a better choice.
      </p>
      <p>
        First-class functions that can be returned by a function, assigned to a variable, and stored
        in collections, will be added in the v0.3.8 release. The target release date for v0.3.8 is
        next week on 3/5/2017. If you are seeing this after v0.3.8 has been released, then you can
        see the release notes <a href="/posts/v0_3_8-release-notes">here</a>. Otherwise that link
        will be a broken link.
      </p>
    </div>
  </div>
</template>
