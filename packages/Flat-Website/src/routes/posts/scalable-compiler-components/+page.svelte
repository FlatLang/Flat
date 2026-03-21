<script lang="ts">
  import Footnote from '$lib/components/Footnote.svelte';
  import FootnoteRef from '$lib/components/FootnoteRef.svelte';
</script>

<svelte:head>
  <link
    href="/styles/scalable-compiler-components.css"
    rel="stylesheet"
    type="text/css"
  />
</svelte:head>

<template lang="flat-html">
  <div anchor-button id="compiler-components">
    <h1>RUNTIME LOADED COMPILER COMPONENTS</h1>
    <p>
      The Flat compiler is being developed with the aim of being the most scalable compiler ever.
      The approach that is being taken to do this is to make use of <a
        href="/posts/runtime-module-loading">runtime module loading</a
      >.
    </p>
    <p>
      The post that was written on runtime module loading was geared toward the essence of runtime
      module loading, not necessarily the application of it. This post is going to explain how
      runtime loading of modules is applied within the compiler.
    </p>
  </div>

  <div anchor-button id="compiler-structure">
    <h1>COMPILER STRUCTURE</h1>
    <p>
      I wrote a post on the <a href="/posts/compiler-design">compiler design</a> and how it is had three
      core principles: Separation of concerns, extensibility, and simplicity. The source of how a lot
      of each of those principles are kept rely on the scalability of the compiler. Here is a refresher
      on the structure of the compiler:
    </p>
    <div class="flow-container">
      <p style="white-space: nowrap;">SOURCE CODE</p>
      <img class="down-arrow" src="/images/down-arrow.svg" /><br />
      <p class="flow-stage">PARSER</p>
      <br />
      <img class="down-arrow" src="/images/down-arrow.svg" /><br />
      <p class="flow-stage">CODE INSPECTOR</p>
      <br />
      <img class="down-arrow" src="/images/down-arrow.svg" /><br />
      <p class="flow-stage">CODE OPTIMIZER</p>
      <br />
      <img class="down-arrow" src="/images/down-arrow.svg" /><br />
      <p class="flow-stage">CODE GENERATOR</p>
      <br />
      <img class="down-arrow" src="/images/down-arrow.svg" /><br />
      <p style="white-space: nowrap;">EXECUTABLE</p>
    </div>
    <p>
      Each of the boxes represents a distinct stage in which a separate component, or group of
      components, are run. A drilled-down view of what each stage performs looks like this:
    </p>
    <table
      class="drilled-down flow-container"
      style="margin-left: auto; margin-right: auto;"
      cellspacing="0"
    >
      <thead>
        <tr>
          <th style="padding-bottom: 20px;"> STAGE </th>
          <th style="padding-bottom: 20px; padding-right: 2.6%;"> COMPONENTS </th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>
            <p class="flow-stage">PARSER</p>
          </td>
          <td>
            <p class="flow-stage">C HEADER PARSER</p>
            <p class="flow-stage">JAVA PARSER</p>
            <p class="flow-stage">FLAT PARSER</p>
          </td>
        </tr>
        <tr>
          <td />
          <td colspan="99">
            <table class="horizontal-bordered">
              <tr>
                <td><hr /></td>
                <td><img class="down-arrow" src="/images/down-arrow.svg" /></td>
                <td><hr /></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage">CODE INSPECTOR</p>
            <br />
          </td>
          <td>
            <p class="flow-stage">TYPE CHECKER</p>
            <p class="flow-stage">SPELL CHECKER</p>
            <p class="flow-stage">CONVENTION CHECKER</p>
            <p class="flow-stage">CONTRACT CHECKER</p>
            <p class="flow-stage">ANNOTATION CHECKER</p>
          </td>
        </tr>
        <tr>
          <td />
          <td colspan="99">
            <table class="horizontal-bordered">
              <tr>
                <td><hr /></td>
                <td><img class="down-arrow" src="/images/down-arrow.svg" /></td>
                <td><hr /></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage">CODE OPTIMIZER</p>
            <br />
          </td>
          <td>
            <p class="flow-stage">UNROLL LOOPS</p>
            <p class="flow-stage">INSERT PROFILER</p>
            <p class="flow-stage">REMOVE DEAD CODE</p>
            <p class="flow-stage">INLINE FUNCTIONS</p>
          </td>
        </tr>
        <tr>
          <td />
          <td colspan="99">
            <table class="horizontal-bordered">
              <tr>
                <td><hr /></td>
                <td><img class="down-arrow" src="/images/down-arrow.svg" /></td>
                <td><hr /></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td>
            <p class="flow-stage">CODE GENERATOR</p>
            <br />
          </td>
          <td>
            <p class="flow-stage">C# GENERATOR</p>
            <p class="flow-stage">C GENERATOR</p>
            <p class="flow-stage">JAVA GENERATOR</p>
          </td>
        </tr>
      </tbody>
    </table>
    <p>
      The key to the scalability is to load these components at runtime. Each of these components
      resides in its own module.
    </p>
    <div anchor-button id="why-runtime">
      <h3>WHY AT RUNTIME?</h3>
      <p>
        Why is the key to scalability to load the components at runtime? Why not compile them with
        the rest of the code? Compiling in a single compilation unit requires all of the packages to
        be known and available at the time of the compile. This does not allow for adding, removing,
        or configuring the components limitlessly without recompiling the whole system.
      </p>
      <p>
        The fact that the components are loaded at runtime is the reason that, theoretically, there
        could be a single release of the core Flat compiler foundation classes, and they then could
        never have to be touched ever again. The probability of this happening is <i>highly</i> unrealistic
        in practice, but the ability of it is there.
      </p>
    </div>
  </div>

  <div anchor-button id="module-examples">
    <h1>HOW COMPILER MODULES ARE LOADED</h1>
    <p>
      <i
        ><b>Disclaimer</b>: The following code is just demo code to give a general idea of what the
        implementation looks like. There will be individual posts on each of the specific component
        types in the future where the implementation details and specifics are shared.</i
      >
    </p>
    <p>
      The compiler looks for classes that extend or implement specific classes or interfaces to
      determine what components to run for each stage of compilation. After loading the Library
      (explained in the <a href="/posts/runtime-module-loading">previous post</a>), you can search
      for classes that extended or implement a specific class or interface with the
      Class.getClassesOfType function<FootnoteRef id="class-filtering" />.
    </p>
    <div anchor-button id="parsers">
      <h3>PARSER COMPONENTS</h3>
      <p>
        Let's look at an example Parser component to help illustrate this. When searching for a
        parser, the compiler searches for classes that extend the class "NodeParser". The NodeParser
        class contains function definitions used to parse statements that are iterated over. The
        base NodeParser class looks like this:
      </p>
      <pre>
          <code class="language-flat">
            {`
              class NodeParser {
                public static parse(String input, Node parent = null, Location location = Location.INVALID) -> Node => null {
                  ...
                }
              }
            `}
          </code>
        </pre>
      <p>
        Parser components build their parsing framework on top of this class. For instance, an
        Assignment parser might look something like this:
      </p>
      <pre>
        <code class="language-flat">
          {`
            // Import the findOperatorOnTopLevel extension function, as well
            // as many other useful extension functions for parsing.
            import "flat/astro/util/CompilerStringFunctions"

            class AssignmentParser extends NodeParser {
              public static parse(String input, Node parent = null, Location location = Location.INVALID) -> Assignment => null {
                let assignmentIndex = input.findOperatorOnTopLevel('=')

                if (assignmentIndex > 0) {
                  let node = Assignment(parent, location)

                  if (node.parseAssignedNode(findAssigned(input, assignmentIndex)) &&
                    node.parseAssignment(findAssignment(input, assignmentIndex))) {
                    node.operators.add(Operator(parent: node, value: "="))

                    return node
                  }
                }
              }

              ...
            }
          `}
        </code>
      </pre>
      <p>
        This AssignmentWriter example first searches for the index of the equals sign in the
        assignment. If the index exists at a valid position (the index must be > 0 for it to be
        valid), then it tries to parse the assigned node (the left-hand side of the assignment) <i
          >and</i
        > the assignment node (the right-hand side of the assignment) using their own parsers. If all
        of that is successful, then it returns the parsed Assignment node.
      </p>
      <p>
        Applying this parser pattern to each different type of Node is what is required to create a
        Parser component. This kind of divide and conquer through using a <a
          target="_blank"
          href="https://en.wikipedia.org/wiki/Recursive_descent_parser">recursive-descent parser</a
        > pattern is recommended to make the most of the Object oriented nature of Flat.
      </p>
    </div>
    <div anchor-button id="inspectors">
      <h3>CODE INSPECTOR COMPONENTS</h3>
      <p>
        The code inspector components are run after the parsers have constructed the Abstract Syntax
        Tree (AST). The compiler's code inspector foundation code traverses the tree and calls upon
        the inspector components on the types of nodes that they are looking at.
      </p>
      <p>
        A simple example is a spell checker. This spell checker just looks at identifiers to
        validate that they are spelled correctly. As with the Parsers, code inspectors has a base
        class that is required to be extended from in order for the foundational compiler framework
        to find it. The class's name is "CodeInspector".
      </p>
      <pre>
        <code class="language-flat">
          {`
            class CodeInspector {
              visible Class[] targetTypes => []

              public inspect(Node node) {
                ...
              }
            }
          `}
        </code>
      </pre>
      <p>Extending the CodeInspector class, the spell checker example looks like this:</p>
      <pre>
        <code class="language-flat">
          {`
            class SpellChecker extends CodeInspector {
              public Class[] targetTypes => [Identifier]

              public inspect(Node node) {
                // We know it's an Identifier because that is the only
                // class type allowed in the targetTypes array
                let identifier = (Identifier)node

                if (SomeFancyDictionary.isSpellingError(identifier.name)) {
                  let suggestions = SomeFancyDictionary.getSuggestions(identifier.name)
                  let suggestionsText = suggestions.count > 0 ?
                    "Some suggestions include: " + suggestions.join(", ") : ""

                  toss SpellCheckWarning(
                    "Identifier '#identifier.name' is not spelled correctly. #suggestionsText",
                    identifier
                  )
                }
              }
            }
          `}
        </code>
      </pre>
      <p>
        This code checks a class called "SomeFancyDictionary" for the word, and if it is not spelled
        correctly, it offers some suggestions to fix it in the warning. The warning itself is
        generated by tossing a SpellCheckWarning which is a class that the SpellChecker component
        contains that extends CompilerWarning. The implementation looks like this:
      </p>
      <pre>
        <code class="language-flat">
          {`
            class SpellCheckWarning extends CompilerWarning {
              public construct(String message, Node cause) {
                super(message, cause)
              }
            }
          `}
        </code>
      </pre>
      <p>
        It is essentially an extension of the CompilerWarning. It is good to create distinct warning
        and error classes for different types of errors in order to add more helpful information for
        when the user sees it.
      </p>
      <p>
        The reason that the warning is <i>tossed</i> is because we do not want to halt execution of the
        code inspector for a spelling error. Toss allows you to save the warning to the output and continue
        the flow of the inspector.
      </p>
    </div>
    <div anchor-button id="optimizers">
      <h3>CODE OPTIMIZER COMPONENTS</h3>
      <p>
        The code optimizer components are run after the Abstract Syntax Tree has been validated.
        Just as the code inspector components, the compiler's code optimizer foundation code
        traverses the tree and calls upon the optimizer components on the types of nodes that they
        are looking at.
      </p>
      <p>Code optimizer components extend the TreeTransformer class that looks like this:</p>
      <pre>
        <code class="language-flat">
          {`
            class TreeTransformer {
              visible Class[] targetTypes => []

              public transform(Node node) {
                ...
              }
            }
          `}
        </code>
      </pre>
      <p>
        This class has the targetTypes array just like the CodeInspector class. The method used to
        perform the transformation is called "transform". An example optimizer for <a
          target="_blank"
          href="https://en.wikipedia.org/wiki/Loop_unrolling">unrolling simple loops</a
        > might look like this:
      </p>
      <pre>
        <code class="language-flat">
          {`
            class LoopUnrollTransformer extends TreeTransformer {
              visible Class[] targetTypes => [ForEachLoop]

              public transform(Node node) {
                let loop = (ForEachLoop)node

                if (!loop.iterator.class.isOfType(IntegerRange)) {
                  // Don't transform if it is not iterating over an integer range
                  return
                }

                let range = (IntegerRange)loop.iterator
                let start = range.start
                let end = range.end

                if (!start.class.isOfType(Literal) ||
                  !start.type.typeClass.extends("flat/primitive/number/Integer") ||
                  !end.class.isOfType(Literal) ||
                  !end.type.typeClass.extends("flat/primitive/number/Integer") ) {
                  // Don't transform if the integer range doesn't use literal
                  // integer values as it's bounds. Can't unroll loops with
                  // variable bounds.
                  return
                }

                let startLiteral = (Literal)start
                let endLiteral = (Literal)end

                let startValue = Int.parse(startLiteral.value)
                let endValue = Int.parse(endLiteral.value)

                let replacementScope = Scope(node.parent, node.location)

                // Unroll the contents of the foreach loop into a replacementScope
                // buffer before replacing the foreach loop with the scope.
                for (var i in startValue..endValue) {
                  let clonedContents = loop.scope.clone()

                  replacementScope.addChild(clonedContents)
                }

                node.replaceWith(replacementScope)
              }
            }
          `}
        </code>
      </pre>
      <p>
        This is an extremely basic implementation, but it should get the point across of how
        optimizer components are implemented.
      </p>
    </div>
    <div anchor-button id="generators">
      <h3>CODE GENERATOR COMPONENTS</h3>
      <p>
        The code generator components are designed similarly to the parsers. The writer components
        extend a "NodeWriter" class that takes a Node and writes it to an output stream. The
        definition of the NodeWriter class looks like this:
      </p>
      <pre>
          <code class="language-flat">
            {`
            class NodeWriter {
                public static write(Node node, OutputStream writer) => writer {
                    ...
                }
            }
            `}
          </code>
        </pre>
      <p>And an implementation of an AssignmentWriter might look like this:</p>
      <pre>
        <code class="language-flat">
          {`
            class AssignmentWriter extends NodeWriter {
              public static write(Assignment node, OutputStream writer) => writer {
                ValueWriter.write(node.assigned)
                writer.write(" = ")
                ValueWriter.write(node.assignment)
              }
            }
          `}
        </code>
      </pre>
      <p>
        Again, this uses the divide and conquer philosophy to accomplish the task. The assignment
        simply makes use of the ValueWriter to output the assigned and assignment parts of the
        assignment.
      </p>
    </div>
  </div>

  <div anchor-button id="disclaimer">
    <h1>ANOTHER DISCLAIMER</h1>
    <p>
      The code shown here is definitely not a final product by any means. The framework is highly
      likely going to change over time after it has been worked with for a while. That said, it is a
      pretty good glimpse into what the compiler's components/modules are going to look like.
    </p>
  </div>

  <div anchor-button id="footnotes">
    <h4>Footnotes:</h4>
    <Footnote id="class-filtering"
      >If some more fine-tuned control over the class type filtering is needed, then you can use the
      Class.ALL to filter upon. The Class.ALL static field contains all of the class declarations
      within the module.</Footnote
    >
  </div>
</template>
