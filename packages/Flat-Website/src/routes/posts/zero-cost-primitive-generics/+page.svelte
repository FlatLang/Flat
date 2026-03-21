<script lang="ts">
  import Footnote from '$lib/components/Footnote.svelte';
  import FootnoteRef from '$lib/components/FootnoteRef.svelte';
</script>

<template lang="flat-html">
  <div anchor-button id="what-are-generic-types">
    <h1>WHAT ARE GENERICS DATA TYPES?</h1>
    <p>
      Before getting into what <i>zero-cost primitive</i> generic types are, lets cover what regular
      generic data types are. Generic data types are parameters you can specify for a class that are
      used to generalize some data types used within the class. To better understand this, consider an
      example:
    </p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          class Barrel<Type> {
            visible Type[] contents

            public add(Type element) {
              contents.add(element)
            }

            public remove(Type element) {
              contents.remove(element)
            }
          }
        `}
      </code>
    </pre>
    <p>
      In the above code example, <span class="pre">&lt;<span class="type">Type</span>&gt;</span> is
      the generic parameter declaration. Adding this to the class declaration allows you to use
      <span class="pre type">Type</span> in place of any regular type. And with this class you can create
      Barrels of various types:
    </p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          // A barrel containing elements of type Bottle
          let barrelOfBottles = Barrel<Bottle>()

          // A barrel containing elements of type Monkey
          let monkeysInABarrel = Barrel<Monkey>()

          // A barrel containing elements of type Shell
          let barrelOfShells = Barrel<Shell>()

          // A barrel containing elements of type Int
          let barrelOfNumbers = Barrel<Int>()
        `}
      </code>
    </pre>
    <p>
      Using generic types in your class ensures that there is type safety whenever you create an
      instance of the class. For instance, if you try to add a number to the <span class="pre"
        >barrelOfBottles</span
      >
      you will get a compile error telling you that the barrelOfBottles only accepts type
      <span class="pre">Bottle</span>.
    </p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          // COMPILE ERROR: Expected type Bottle, but was given Int
          barrelOfBottles.add(4)
        `}
      </code>
    </pre>
    <p>Generic parameters can also be declared for functions:</p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          public myGenericFunc<X, Y>(Int paramA, X paramB, Y paramC) {

          }

          myGenericFunc<Shell, Monkey>(5, Shell(), Monkey())
        `}
      </code>
    </pre>
  </div>

  <div anchor-button id="zero-cost-primitive-generics">
    <h1>ZERO-COST PRIMITIVE GENERICS</h1>
    <div anchor-button id="the-problem">
      <h3>THE PROBLEM</h3>
      <p>
        In the case of <i>zero-cost primitive</i> generic types, I will focus on this example:
      </p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            // A barrel containing elements of type Int
            let barrelOfNumbers = Barrel<Int>()

            barrelOfNumbers.add(5) // valid
          `}
        </code>
      </pre>
      <p>
        In most programming languages, when you use a primitive type in place of a generic argument,
        it will autobox the primitive type into a wrapper class of some sort. Usually representing
        something like:
      </p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            // A barrel containing elements of type Int
            let barrelOfNumbers = Barrel<IntWrapperClass>()

            barrelOfNumbers.add(IntWrapperClass(5))
          `}
        </code>
      </pre>
      <p>
        This is required because you cannot abstract between <i>reference</i> types and <i>value</i>
        types. If you are not familiar with the difference between reference and value types,
        <a target="_blank" href="https://msdn.microsoft.com/en-us/library/t63sy5hs.aspx"
          >this page</a
        > does a pretty good job explaining the difference. But in short, Object types are reference
        types, and primitive types are value types.
      </p>
      <p>
        This poses a performance issue for code that heavily relies on primitive types in place of
        generic arguments. When you create a new instance of a primtive wrapper class, it's not
        free. The common way to work around this is to create a separate class that has the same
        fields and functions, but instead of using that generic parameter, hardcode that primitive
        type instead. Something comparable to:
      </p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            class IntBarrel {
              visible Int[] contents

              public add(Int element) {
                contents.add(element)
              }

              public remove(Int element) {
                contents.remove(element)
              }
            }
          `}
        </code>
      </pre>
      <p>
        Then you would create the barrelOfNumbers like: <span class="pre"
          >let barrelOfNumbers = IntBarrel()</span
        >
      </p>
      <p>
        This has some obvious drawbacks. What if you want to add some functionality to the original
        Barrel class? Then you would have to add that same function/field to the <i>IntBarrel</i> class
        as well! What if you have a general function that takes in a Barrel, but you want to pass in
        your IntBarrel like:
      </p>
      <pre>
        <code class="language-flat" style="margin: 40px 0;">
          {`
            public myFunBarrelFunction(Barrel barrel) {
              ... do something really cool with a barrel ...
            }

            ...

            let barrelOfNumbers = IntBarrel()

            // COMPILE ERROR: Expected type Barrel, but was given IntBarrel
            myFunBarrelFunction(barrelOfNumbers)
          `}
        </code>
      </pre>
      <p>
        Short answer: you can't (slightly less short answer: you have to create a duplicate function
        that takes IntBarrel instead of Barrel).
      </p>
      <p>
        In Flat, <b>reusability</b> is a core design principle. It was quite evident that we needed an
        elegant solution to this.
      </p>
    </div>
    <div anchor-button id="the-solution">
      <h3>THE SOLUTION</h3>
      <p>
        In C++ this is a common code structure that you use when you are doing generic programming: <a
          target="_blank"
          href="https://www.tutorialspoint.com/cplusplus/cpp_templates.htm">templates</a
        >. When you use a template in C++, a unique set of code is compiled for each of the
        different types that is used with the template. For instance:
      </p>
      <pre>
        <code class="language-cpp" style="margin: 40px 0;">
          {`
            template<typename T> void f(T s)
            {
              std::cout << s << '\\n';
            }

            ...

            f(5.0);
            f("hey");
          `}
        </code>
      </pre>
      <p>
        Both of those function calls create separate f() function declarations that correspond with
        the datatype that is given to it in place of <span class="pre"
          ><span class="modifier">typename</span> T</span
        >.
      </p>
      <pre>
        <code class="language-cpp" style="margin: 40px 0;">
          {`
            // f(5.0) creates:
            void f(double s)
            {
              std::cout << s << '\\n';
            }

            // f("hey") creates:
            void f(const char* s)
            {
              std::cout << s << '\\n';
            }
          `}
        </code>
      </pre>
      <p>
        The solution to the primitive generic problem in Flat resembles some of the aspects of C++
        templates. In most cases, when you are not using primitive datatypes as generic arguments,
        you are safe to resort to the standard method of how generic datatypes work by using <a
          target="_blank"
          href="http://code.stephenmorley.org/articles/java-generics-type-erasure">type erasure</a
        >. But for when primitive datatypes are used in place of generic arguments, you have to do a
        process much like what templates do with each datatype.
      </p>
      <p>What the Flat compiler does when it comes across generic type arguments is:</p>
      <table class="flow-container wrap-friendly" style="margin: 40px auto;">
        <tr>
          <td>
            <p><span class="pre">Barrel&lt;Int&gt;</span></p>
          </td>
        </tr>
        <tr>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage">
              Does <span class="pre">Barrel&lt;Int&gt;</span> contain primitive generic argument types?
            </p>
          </td>
          <td>
            <img
              class="down-arrow"
              src="/images/down-arrow.svg"
              style="transform: rotate(-90deg);"
            /><br />
          </td>
          <td>
            <p class="flow-stage">No - Use traditional type erasure</p>
          </td>
        </tr>
        <tr>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 80px;">Yes</p>
            <br />
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage">
              Has a converted type of <span class="pre">Barrel&lt;Int&gt;</span> already been created?
            </p>
            <br />
          </td>
          <td>
            <img
              class="down-arrow"
              src="/images/down-arrow.svg"
              style="transform: rotate(-90deg);"
            /><br />
          </td>
          <td>
            <p class="flow-stage">Yes - Use the converted type</p>
          </td>
        </tr>
        <tr>
          <td>
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 80px;">No</p>
            <br />
            <img class="down-arrow" src="/images/down-arrow.svg" />
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage" style="min-width: 80px;">
              Create a duplicate class/function that uses primitive type in place of generic
              argument
            </p>
          </td>
        </tr>
      </table>
      <p>
        So following the flow chart with the input of <span class="pre">Barrel&lt;Int&gt;</span>,
        you come to the "Create a duplicate class/function ..." step. Essentially, this is what we
        did with the <span class="pre">IntBarrel</span> class, but instead of doing it yourself, the
        compiler does it for you (and without the risk of typos!).
      </p>
      <p>
        A positive side-effect of the compiler doing the conversion instead of a human doing it is
        that the compiler can be sure that IntBarrel and Barrel are compatible. It is because of
        this that you can use <span class="pre">Barrel&lt;Int&gt;</span> for the
        <span class="pre type">myFunBarrelFunction</span> function!
      </p>
      <p>
        With primitive generics you can have the <i>high-performance</i> of an
        <span class="pre">IntBarrel</span>
        with the <i>flexibility</i> of <span class="pre">Barrel&lt;Type&gt;</span>. This is where
        the <i>zero-cost</i> part of the name comes in. There is no compromise<FootnoteRef
          id="executable-size"
        />.
      </p>
    </div>
  </div>

  <div anchor-button id="conclusion">
    <h1>Conclusion</h1>
    <p>
      When using Flat, the last thing we want to happen is people be afraid to write reusable code
      because it might affect performance. Adding support for converting primitive generic argument
      types was a necessary step to let generic parameters truly reach their full potential. It is
      also worth noting that in the new compiler, the functionality to convert primitive generic
      argument types will be optional (on by default). If you are compiling to a language that uses
      templates in place of generic parameters (e.g. C++ and Rust), there is no need to do the
      conversions.
    </p>
  </div>

  <div anchor-button id="footnotes">
    <h4>Footnotes:</h4>
    <Footnote id="executable-size">Except for a slightly larger executable filesize.</Footnote>
  </div>
</template>
