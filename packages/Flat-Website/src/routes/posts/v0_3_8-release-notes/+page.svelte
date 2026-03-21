<script lang="ts">
  import Issue from '$lib/components/Issue.svelte';
</script>

<template lang="flat-html">
  <div>
    <h3><a href="/download">DOWNLOAD BETA v0.3.8</a></h3>
    <h1>NOTABLE CHANGES</h1>
    <h4 style="margin-bottom: 0px;">tl;dr:</h4>
    <ul>
      <li><a href="#first-class-functions">First-class functions</a></li>
      <li><a href="#this-modifier">New 'this' modifier</a></li>
      <li><a href="#auto-final">Auto-final declarations</a></li>
      <li><a href="#unit-testing">Unit testing framework</a></li>
      <li><a href="#bug-fixes">Bug fixes</a></li>
    </ul>
    <div id="release-notes" class="release-notes" style="margin-bottom: 20px;">
      <div anchor-button id="first-class-functions" class="release-note">
        <h4>
          Added full support for first-class functions <Issue
            values={[
              { number: 371, repo: 'Flat' },
              { number: 374, repo: 'Flat' },
              { number: 375, repo: 'Flat' },
              { number: 376, repo: 'Flat' },
            ]}
          />
        </h4>
        <p>
          More info on first class functions discussed in <a href="/posts/first-class-functions"
            >this post</a
          >.
        </p>
      </div>
      <div anchor-button id="this-modifier" class="release-note">
        <h4>Added 'this' modifier for constructor parameters</h4>
        <p>
          Used to assign passed parameter value to field in parent class with same name as
          parameter. Example:
        </p>
        <pre>
          <code class="language-flat">
            {`
              class MyClass {
                visible Int myField

                public construct(this Int myField) {}
              }

              let myClass = MyClass(10)

              Console.writeLine(myClass.myField) // outputs 10
            `}
          </code>
        </pre>
        <p>This code is essentially the same as this:</p>
        <pre>
          <code class="language-flat">
            {`
              class MyClass {
                visible Int myField

                public construct(Int myField) {
                  this.myField = myField
                }
              }

              let myClass = MyClass(10)

              Console.writeLine(myClass.myField) // outputs 10
            `}
          </code>
        </pre>
        <p>But reduces code duplication.</p>
      </div>
      <div anchor-button id="auto-final" class="release-note">
        <h4>
          Updated declarations to default to final <Issue
            values={[{ number: 368, repo: 'Flat' }]}
          />
        </h4>
        <p>
          The default state of variable declarations is now final. Final variables are good practice
          when writing software, and the easier it is to make final variables the better.
        </p>
        <h5 style="margin-bottom: 5px;">Previous</h5>
        <pre>
          <code class="language-flat">
            {`
              Int x = 10

              x = 50 // valid
            `}
          </code>
        </pre>
        <h5 style="margin-bottom: 5px;">Current</h5>
        <pre>
          <code class="language-flat" style="margin-bottom: 35px;">
            {`
              Int x = 10

              x = 50 // Compiler error, cannot modify final variable x
            `}
          </code>
        </pre>
        <p>To declare a mutable variable you do this:</p>
        <pre>
          <code class="language-flat">
            {`
              var Int x = 10

              x = 50 // valid
            `}
          </code>
        </pre>
        <p>
          Or, if you the type can be inferred from its assignment, you do not have to speficy the
          type:
        </p>
        <pre>
          <code class="language-flat">
            {`
              var x = 10

              x = 50 // valid
            `}
          </code>
        </pre>
      </div>
      <div anchor-button id="unit-testing" class="release-note">
        <h4>
          Added unit testing functionality with Flat <a
            target="_blank"
            href="https://github.com/FlatLang/Test">Test</a
          >
        </h4>
        <p>
          There will be a post on how to use Flat Test's unit testing framework <a
            href="/posts/flat-unit-testing">here</a
          >.
        </p>
      </div>
      <div anchor-button id="bug-fixes" class="release-note">
        <h4>General bug fixes <Issue values={[{ number: 372, repo: 'Flat' }]} /></h4>
        <p>
          Fixed private function prototypes not being generated in C <Issue
            values={[{ number: 372, repo: 'Flat' }]}
          />.
        </p>
      </div>
    </div>
  </div>
</template>
