<svelte:head>
  <link
    href="/styles/flow-boxes.css"
    rel="stylesheet"
    type="text/css"
  />
  <link
    href="/styles/path-forward.css"
    rel="stylesheet"
    type="text/css"
  />
</svelte:head>

<template lang="flat-html">
  <div anchor-button id="road-map">
    <h1>FLAT'S ROAD MAP</h1>
    <p>The current road map for Flat looks something like this:</p>
    <table
      class="path-forward drilled-down flow-container"
      style="margin-left: auto; margin-right: auto;"
      cellspacing="0"
    >
      <thead>
        <tr>
          <th style="padding-bottom: 20px;"> STAGE </th>
          <th style="padding-bottom: 20px; padding-right: 2.6%;"> TASKS </th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>
            <p class="flow-stage">BUILD NEW COMPILER</p>
          </td>
          <td>
            <p class="flow-stage">RUNTIME MODULE LOADING</p>
            <p class="flow-stage">SEPARATION OF CONCERNS</p>
            <p class="flow-stage">WRITTEN IN FLAT</p>
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
            <p class="flow-stage">FIND PEOPLE TO WORK ON COMPILER</p>
            <br />
          </td>
          <td>
            <p class="flow-stage">WRITE MORE TARGETS</p>
            <p class="flow-stage">WRITE MORE PARSERS</p>
            <p class="flow-stage">BUILD OUT MORE COMPONENTS</p>
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
            <p class="flow-stage">BUILD PERFORMANCE ANALYSIS TOOLKIT</p>
            <br />
          </td>
          <td>
            <p class="flow-stage">STAND-ALONE WEBSITE</p>
            <p class="flow-stage">AUTOMATE ANALYSIS</p>
            <p class="flow-stage">VISUALIZE ANALYTICS</p>
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
            <p class="flow-stage">FINALIZE SYNTAX / RELEASE 1.0</p>
            <br />
          </td>
          <td>
            <p class="flow-stage">RESOLVE ISSUES CONCERNING SYNTAX</p>
            <p class="flow-stage">ADD MISSING SYNTAX FEATURES</p>
            <p class="flow-stage">STABILIZE COMPILER</p>
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
            <p class="flow-stage">BUILD OUT STANDARD LIBRARY</p>
            <br />
          </td>
          <td>
            <p class="flow-stage">COMPLETE UNFINISHED INTERFACES</p>
            <p class="flow-stage">ADD GENERAL HELPFUL LIBRARIES</p>
            <p class="flow-stage">BUILD COMMON LANGUAGE BINDINGS</p>
          </td>
        </tr>
      </tbody>
    </table>
    <p>
      Many of these tasks will be carried out in parallel, and sometimes even out of order. But this
      is the general flow of what the road map on the development of Flat looks like.
    </p>
  </div>

  <div anchor-button id="build-new-compiler">
    <h1>BUILD NEW COMPILER</h1>
    <p>
      The Flat compiler is going to be the variable on the speed in which the rest of Flat is
      developed. The better the design will directly correlate to the speed in which the rest of the
      tasks on the road map can be carried out. This is why so much emphasis has been placed on the
      scalability of it. Getting the compiler right is pivotal in completing the tasks on the road
      map in a timely manner.
    </p>
    <p>
      The tasks that need to be completed to check this stage off of the road map are that the
      components must be loaded at runtime, there must be a logical separation of concerns, and it
      must be written in Flat.
    </p>
    <div anchor-button id="runtime-module-loading">
      <h3>COMPONENTS LOADED AT RUNTIME</h3>
      <p>
        This, also, has been preached on many times in previous posts. The compiler needs to have a
        few foundational components (Abstract Syntax Tree model, component pipeline model), and some
        default supplementary components (default Flat code parser, default type validators, default
        optimizers, default generators). The rest of the components are included and loaded by the
        will of the user.
      </p>
    </div>
    <div anchor-button id="separation-of-concerns">
      <h3>SEPARATION OF CONCERNS</h3>
      <p>
        Anyone who works on the compiler shouldn't have to understand the whole compiler's design in
        detail to be able to contribute. In the same way that <a
          target="_blank"
          href="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller">MVC</a
        > separates concerns into the Model, View and Controller, the compiler needs to be separated
        into distinct components. The components for the compiler are the top-level stages that the compiler
        goes through: Parser, Code Inspector, Code Optimizer, Code Generator. Someone writing a parser
        shouldn't have to understand how to write code inspectors, and so forth.
      </p>
    </div>
    <div anchor-button id="written-in-flat">
      <h3>MUST BE WRITTEN IN FLAT</h3>
      <p>
        The compiler itself must be written in Flat. Flat must be the only dependency that is
        required to run the compiler. Currently, the compiler is written in Java and requires Java
        to be installed in order to run the compiler.
      </p>
    </div>
  </div>

  <div anchor-button id="find-contributers">
    <h1>FIND PEOPLE TO WORK ON COMPILER</h1>
    <p>
      Once the compiler is written, developing for the language is going to be much easier. The
      current compiler is all-in-one instead of having the nice separation of concerns.
    </p>
    <p>
      This stage will probably be performed in parallel with the "Build performance analysis
      toolkit" stage. Finding people to work on the compiler is also pivotal for speeding up the
      development of the compiler. The separation of concerns not only makes it easier to learn how
      to develop for the compiler, it also makes it quicker to develop for the compiler. The less
      communication that is required between the components is key for rapid development when
      working with other developers. The more communication required between developers and
      components, the slower the components are developed, and with more chance of bugs caused by
      bad communication. The best solution to this is to keep the communication to a minimum.
    </p>
    <p>
      The main focus areas I would want contributers to help out on include: writing more targets,
      writing more parsers, and building more components.
    </p>
    <div style="margin-left: 25px;">
      <div anchor-button id="more-targets">
        <h3>WRITE MORE TARGETS</h3>
        <p>
          Writing more targets means better compatibility with existing libraries. It also means
          better performance analysis. With writing compilation-target components as easy as it is,
          I expect that there could be several of these compilation-target components written this
          year.
        </p>
      </div>
      <div anchor-button id="more-parsers">
        <h3>WRITE MORE PARSERS</h3>
        <p>
          Writing more parsers means that Flat is easier to adopt. Writing more parsers is important
          for developers who have already written their programs and libraries in other languages,
          but would like to incorporate Flat into the mix. As with compilation targets, I think
          there could be several new parsers this year.
        </p>
      </div>
      <div anchor-button id="more-components">
        <h3>WRITE MORE COMPONENTS</h3>
        <p>
          This is somewhat more of an abstract goal. There are a lot of components that could be
          written, many of them I haven't even thought of yet. Opening up the extensible compiler to
          the open-source community could give birth to endless components for the compiler.
        </p>
      </div>
    </div>
    <p>
      By the way, I am not completely sure how I am going to find these mythical developers, so if
      you are reading this and you are not a bot, the repositories are open-source and you can fork
      them <a target="_blank" href="https://github.com/FlatLang">here</a>. Up until this point I
      have been the only developer on this project. The idea is to build out the framework to be
      extremely conducive to open-source development and then beg my friends to work on it.
    </p>
  </div>

  <div anchor-button id="performance-analysis">
    <h1>PERFORMANCE ANALYSIS TOOLKIT</h1>
    <p>
      The performance analysis in Flat, <a href="/posts/multiple-targets#performance-analysis"
        >as explained here</a
      >, has some serious potential. The performance perspective that multiple-targets allows for is
      a big white-space in the performance analysis field.
    </p>
    <p>
      The face of the tool will be through a website. Users will go to the website (currently <a
        target="_blank"
        href="http://www.flat-reactor.com">flat-reactor.com</a
      >) and have a dashboard of their repos from github (or other repo providers) available to
      perform analysis on and to visualize analytics from.
    </p>
    <p>
      One of the staple features I want to be at the core of this project is the complete repo
      performance history. Essentially, the website would pull every version of the code possible
      from each commit, try to compile and run it with performance analysis. Doing this could give 3
      dimensional insight on the project's performance. You would be able to see which functions got
      faster or got slower, what areas of the code have always been bottlenecks, etc. And with the
      ability to differentiate from <a href="/posts/multiple-targets#bottleneck-types"
        >language architecture bottlenecks and algorithmic bottlenecks</a
      >, the performance history becomes exponentially more powerful.
    </p>
    <p>
      This is the area where I think some profitability is possible. Because of this, I am sprinting
      toward this component as fast as possible.
    </p>
  </div>

  <div anchor-button id="finalize-syntax">
    <h1>FINALIZE SYNTAX / RELEASE 1.0</h1>
    <p>
      Flat 1.0 will be defined by the finalization of the syntax. However, in this case,
      finalization of syntax does not mean the syntax will never be updated again. It simply means
      that the main objective is shifted from language design to tool design. There will be a tool
      created, much like <a target="_blank" href="https://posts.golang.org/introducing-gofix"
        >gofix</a
      >, where even if the new syntax is not backwards compatible, the old code will be convertible
      to the new syntax through automation. I think the releasing of the "Flatfix", or whatever it
      will be named, will mark the release of Flat 1.0 and the finalization of the syntax.
    </p>
  </div>

  <div anchor-button id="build-standard-library">
    <h1>BUILD OUT STANDARD LIBRARY</h1>
    <p>
      The idea of the standard library is still malleable. I'm not even completely sure if the
      standard library should be built out at all. The other path to choose instead of building out
      the library would be to write libraries and have them be installed with the compiler by
      default. This can still sort of be thought of as "standard", but instead of a single
      conglomeration library, it would be fragmented into packages like "io", "math", "graphics",
      and others like that. By the time we even reach this point, we should have better perspective
      on what path to take, so I'm not thinking too much on which path to take yet.
    </p>
    <p>
      The fragmentation of the library brings up an important point on which I will make a future
      post on: <b>package management</b>. I have been lightly thinking about the package manager
      that Flat is going to use, called "<a
        target="_blank"
        href="https://github.com/FlatLang/Airship">Airship</a
      >". The package manager would be much like
      <a target="_blank" href="https://www.npmjs.com/">npm</a> and other package managers, but little
      has been fleshed out on this topic thus far. Perhaps this could be a project that is primarily
      worked on by a contributer.
    </p>
  </div>

  <div anchor-button id="timeline">
    <h1>TIMELINE</h1>
    <p>
      I feel like I'm going to shoot myself in the foot by putting the my projection on how long I
      think it will take to complete these tasks, but I think it is helpful for perspective.
    </p>
    <ul>
      <li>The new compiler should be written by <b>end of April 2017</b>.</li>
      <li>
        I believe the finalization of the syntax and release of Flat 1.0 is achievable <b
          >within the year of 2017</b
        >.
      </li>
      <li>
        The performance analysis toolkit is likely not going to be <i>completed</i> per se, but
        should be in a functional state <b>within the year of 2017</b>.
      </li>
    </ul>
    <p>
      I would hope to have more contributers within the year of 2017 also. The addition of
      contributers could affect these time estimates.
    </p>
  </div>
</template>
