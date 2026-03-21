<template lang="flat-html">
  <div anchor-button id="core-principles" style="margin-bottom: 40px;">
    <h1>CORE DESIGN PRINCIPLES</h1>

    <p>The Flat compiler has 3 core design principles that guide the development:</p>
    <ul>
      <li><a href="#separation-of-concerns">Separation of concerns</a></li>
      <li><a href="#extensibility">Extensibility</a></li>
      <li><a href="#simplicity">Simplicity</a></li>
    </ul>
    <p>These principles guide each decision that is made on the design of the compiler.</p>
  </div>

  <div anchor-button id="separation-of-concerns">
    <h1 style="margin-bottom: 10px;">SEPARATION OF CONCERNS</h1>
    <hr />
    <p>
      The Flat compilation process is separated into 4 distinct phases: parser, code inspector, code
      optimizer, and code generator. This separation is an important factor of what makes the other
      core design principles of extensibility and simplicity possible.
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
    <div anchor-button id="parser">
      <h3>Parser</h3>
      <p>
        The parser's job is to parse the source code, from an <i>input language</i>, to an AST
        (Abstract Syntax Tree). "Input language" is italicised to bring attention to the fact that
        the compiler is not restricted to Flat code only. Any language can be compiled with the Flat
        compiler if a parser is written for that language. Because all of the parsing code is
        separated out into the parse phase, the parser is the <i>only</i> component that needs to be
        written to add support for an additional input language. For example, if you want to add support
        for compiling Swift code with the Flat compiler, the only component you need to write is the
        parser.
      </p>
    </div>
    <div anchor-button id="code-inspector">
      <h3>Code inspector</h3>
      <p>
        The code inspector is where type validation for the code is done. This is where most
        compile-time errors and warnings are generated. This phase makes sure the code is valid
        before handing it off to the code optimizer.
      </p>
    </div>
    <div anchor-button id="code-optimizer">
      <h3>Code optimizer</h3>
      <p>
        This is the phase where the AST (Abstract Syntax Tree) is transformed. There are many
        reasons you would want to transform the AST including to: improve performance, obfuscate
        code (to help keep proprietary code private), minify code, inject custom functionality (e.g.
        benchmarking timers), and much more.
      </p>
    </div>
    <div anchor-button id="code-generator">
      <h3>Code generator</h3>
      <p>
        The code generator is the inverse of the parser. This phase takes the AST (Abstract Syntax
        Tree) and writes it to a specified target language. In the same way that when you write a
        new parser you gain access to all of the libraries written in the language that is being
        parsed, when writing a new code generator for a specified language, that language gains
        access to all of <i>Flat's</i> libraries. This is great because it means that all code that you
        write in Flat is not only usable in a Flat project, it also can be used in the specified target
        language. For example, if you write a Swift code generator, then you can use any code written
        in Flat natively in a Swift project.
      </p>
    </div>
  </div>

  <div anchor-button id="extensibility">
    <h1 style="margin-bottom: 10px;">EXTENSIBILITY</h1>
    <hr />
    <p>
      The extensibility of the compiler is incredibly important for the development to reach the
      scale that is necessary for Flat to become widely adopted. As a result of the separation of
      concerns, writing extensions to each of the compilation phases is made exponentially easier.
      Instead of having to know a whole complicated compiler system, you only have to know how the
      specific component you are extending works. For instance, if you need to write a new parser,
      you only have to know how to parse code; you dont need to know how to check types, optimize
      the AST (Abstract Syntax Tree), or generate the code.
    </p>
    <p>Here are some reasons you might want to extend each of the components of the compiler:</p>
    <div anchor-button id="parser-uses">
      <h3>Parsers</h3>
      <p>
        Writing a new parser allows any other language to have <i>all</i> of the functionality that Flat
        has. Once a parser is written for a language, you get all of the functionality of any existing
        code inspectors, optimizers, and code generators. You could even use the parser strictly as a
        translater to Flat by outputting the code with the Flat code generator.
      </p>
      <p>
        The separation of concerns of the parser is incredibly powerful because it allows for rapid
        development of new input languages. Each new language that can be compiled by the Flat
        compiler adds to the available libraries for all Flat programs.
      </p>
    </div>
    <div anchor-button id="code-inspector-uses">
      <h3>Code inspectors</h3>
      <p>
        Reasons you would write extensions for this phase is to add more comprehensive, or better,
        warning and error messages. Examples of extensions that could be added include:
      </p>
      <div style="margin-left: 25px;">
        <h4>Spell checkers</h4>
        <p>Spell check comments, identifiers, etc.</p>
        <h4>Formatting convention validation</h4>
        <p>
          You could throw compilation errors whenever a program does not fulfill a specified
          formatting convention. This is helpful when writing software in teams.
        </p>
      </div>
    </div>
    <div anchor-button id="code-optimizer-uses">
      <h3>Code optimizers</h3>
      <p>
        There are many reasons to write code optimizers. The ability to choose which optimizers you
        do or do not want to run during compilation gives fine tuned control to the user on how they
        want their program to be built. Some common reasons to write optimizer components include:
      </p>
      <div style="margin-left: 25px;">
        <h4>Improving performance</h4>
        <p>Inlining functions, unrolling loops, removing dead code, etc.</p>
        <h4>Profiling code</h4>
        <p>You could add timers sections of the code to observe the performance of those areas.</p>
      </div>
    </div>
    <div anchor-button id="code-generator-uses">
      <h3>Code generators</h3>
      <p>
        Implementing a code generator means adding support for Flat on a new platform. As mentioned
        before, you can write code generators to other languages to give that language full support
        of any libraries written in Flat. Adding new language targets also adds the ability for Flat
        programs to run on any of the platforms and architectures that the target language can run
        on. For example, if you generate the code to the Javascript language, you can run all of
        your Flat code in the browser. You can also interact with the generated Flat code from any
        existing Javascript libraries.
      </p>
    </div>
  </div>

  <div anchor-button id="simplicity">
    <h1 style="margin-bottom: 10px;">SIMPLICITY</h1>
    <hr />
    <p>
      As with the language itself, simplicity is a very core design principle. The separation of
      concerns really aids in keeping the compiler simple; there are no unnecessary complications
      caused by communication between the phases. Unnecessary communication is often a sign of bad
      software design and can lead to hard to detect bugs in code.
    </p>
    <p>
      The best prevention for these bugs that I have found is to keep the compiler as simple as
      possible. The simplicity is not to be confused with being rigid or inflexible. The idea is to
      have several components that operate within their own lane, and have these components compose
      what is the Flat compiler.
    </p>
  </div>

  <div anchor-button id="moving-forward" style="margin-top: 40px;">
    <h2>MOVING FORWARD</h2>
    <p>
      This is only the first evolution of the compiler and I expect a lot to change in the future
      when there are more components built on it.
    </p>
  </div>
</template>
