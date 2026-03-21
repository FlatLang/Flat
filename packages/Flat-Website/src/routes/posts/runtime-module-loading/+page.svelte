<template lang="flat-html">
  <div anchor-button id="why">
    <h1>WHY LOAD MODULES AT RUNTIME?</h1>
    <p>
      Loading modules at runtime is incredibly powerful when developing scalable software. But under
      what circumstances do you need to load modules at runtime?
    </p>
    <p>
      Whenever you are writing software collaboratively with other developers, it is often the case
      that you will not be writing the same exact program. The software as a whole may be developed
      to be run in a single package, but what the other collaborators are working on may differ from
      what you are working on. In cases like this, runtime loading of modules can be helpful.
    </p>
    <p>
      When developing software with collaborators like this, there is usually a core library or
      framework that the rest of the collaborators use to allow communication between interlaced
      software components. This core library is analogous to a motherboard for a computer. Just as
      hardware components are swappable and interchangeable (e.g. RAM and the CPU), the software
      components act similarly.
    </p>
    <p>
      This allows for incredible scalability because, even after the foundational software has been
      compiled, you can continue to add more functionality to the software package. Going back to
      the motherboard analogy: you could add more RAM; you could swap out the RAM to a completely
      different brand; you could add a dedicated GPU; and so on. The ability to swap out or update
      supplemental components without having to affect the foundational software components permits
      a kind of ultra-scalability that allows developers to build complex software without
      over-complicating the foundation.
    </p>
  </div>

  <div anchor-button id="how">
    <h1>HOW TO LOAD MODULES AT RUNTIME</h1>
    <p>
      Let's say that we have an external library in that is contained within a packaged named
      "com/myproject" and contains a single class that is named "MySpecialClass". And also imagine
      that the source code of this class looks like this:
    </p>
    <pre>
      <code class="language-flat">
        {`
          package "com/myproject"

          class MySpecialClass {
            public static var Int fieldName = 10

            public static funcName() {
              Console.writeLine("This came from com/myproject/MySpecialClass.funcName")
            }
          }
        `}
      </code>
    </pre>
    <p>
      To load modules at runtime with the Flat standard library you use the flat/meta/Library class.
      You load a library like this:
    </p>
    <pre>
      <code class="language-flat">
        let myLibrary = Library("path/to/project/folder").load()
      </code>
    </pre>
    <p>
      It is extremely simple to load a library. As side note about how the modules are loaded, if a
      shared library in the specific target languages is not existent at the time that the Library
      is loaded from the code, the Library class will try to compile the project folder and output a
      shared library to use.
    </p>
    <p>
      Now that we have the library loaded into our code, you can access fields and functions from
      the code like this:
    </p>
    <pre>
      <code class="language-flat">
        {`
          let myLibrary = Library("path/to/project/folder").load()

          let func = myLibrary.getFunction("funcName", "com/myproject/MySpecialClass")

          func()

          let Int field = myLibrary.getField("fieldName", "com/myproject/MySpecialClass")

          Console.writeLine("Received field with value: #field")
        `}
      </code>
    </pre>
    <p>
      Looking back on the MySpecialClass.funcName implementation, we can expect that "This came from
      com/myproject/MySpecialClass.funcName" to be outputted to the console after invoking <span
        class="pre">func()</span
      >. Furthermore, we can expect that the console outputted the string "Received field with
      value: 10" when outputting the field's value.
    </p>
    <p>Now lets add some more complex types to our class:</p>
    <pre>
      <code class="language-flat">
        {`
          package "com/myproject"

          class MySpecialClass {
            public static var Int fieldName = 10

            public static funcName() {
              Console.writeLine("This came from com/myproject/MySpecialClass.funcName")
            }

            public static complexFunc(String x, Int y) -> String {
              var String outputValue = ""

              for (i in 0..y) {
                outputValue += x
              }

              return outputValue
            }
          }
        `}
      </code>
    </pre>
    <p>
      If you need to access a more complicated function type, you can declare the type parameters
      and return type of the function when accessing the function from the getFunction function:
    </p>
    <pre>
      <code class="language-flat">
        {`
          let myLibrary = Library("path/to/project/folder").load()

          let func(String, Int) -> String = myLibrary.getFunction("complexFunc", "com/myproject/MySpecialClass")

          Console.writeLine("Result from func: " + func("hi", 3))
        `}
      </code>
    </pre>
    <p>The output of this code would be "Result from func: hihihi".</p>
    <p>
      So this is all great and all, but what if you want to call a non-static function or access a
      non-static field? To do this we would have to have an instance of the MySpecialClass class.
      But first, let's add some non-static components to the class:
    </p>
    <pre>
      <code class="language-flat">
        {`
          package "com/myproject"

          class MySpecialClass {
            public static var Int fieldName = 10

            public Int memberField

            public static funcName() {
              Console.writeLine("This came from com/myproject/MySpecialClass.funcName")
            }

            public static complexFunc(String x, Int y) -> String {
              var String outputValue = ""

              for (i in 0..y) {
                outputValue += x
              }

              return outputValue
            }

            public incrementMemberField() {
              memberField++
            }

            public toString() => "This is a MySpecialClass!!"
          }
        `}
      </code>
    </pre>
    <p>
      Now to access these non-static members, we have to create an instance of the MySpecialClass by
      calling its constructor:
    </p>
    <pre>
      <code class="language-flat">
        {`
          let myLibrary = Library("path/to/project/folder").load()

          let instance = myLibrary.getInstance("com/myproject/MySpecialClass")

          Console.writeLine("Received instance: #instance")
        `}
      </code>
    </pre>
    <p>
      This would output "Received instance: This is a MySpecialClass!!". Now you can access
      non-static fields and functions using the instance like this:
    </p>
    <pre>
      <code class="language-flat">
        {`
          let myLibrary = Library("path/to/project/folder").load()

          let instance = myLibrary.getInstance("com/myproject/MySpecialClass")

          let Int memberField = myLibrary.getField("memberField", instance)

          let incrementFunc = myLibrary.getFunction("incrementMemberField", instance)

          Console.writeLine("Unmodified memberField: #memberField")

          incrementFunc()

          Console.writeLine("Incremented memberField: #memberField")

          incrementFunc()
          incrementFunc()

          Console.writeLine("Incremented memberField: #memberField")
        `}
      </code>
    </pre>
    <p>The resulting output of running this code would yield:</p>
    <pre>
      <code class="language-bash">
        {`
          Unmodified memberField: 0
          Incremented memberField: 1
          Incremented memberField: 3
        `}
      </code>
    </pre>
    <p>
      This gives you full control over all the aspects of the MySpecialClass class that you would
      have had if you were to have access to the class at the time of compilation.
    </p>
  </div>

  <div anchor-button id="conclusion">
    <h1>Conclusion</h1>
    <p>
      I will release a more in-depth post on this topic tomorrow <a
        href="/posts/scalable-compiler-components">here</a
      >. It will pertain to how dynamic loading of modules will be used in the Flat compiler to
      achieve the ultra-scalability that is necessary to expedite the development of the Flat
      language.
    </p>
  </div>
</template>
