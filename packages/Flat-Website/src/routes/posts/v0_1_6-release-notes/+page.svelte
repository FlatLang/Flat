<script lang="ts">
  import Issue from '$lib/components/Issue.svelte';
</script>

<template lang="flat-html">
  <div>
    <h3><a href="/download">DOWNLOAD BETA v0.1.6</a></h3>
    <h1>NOTABLE CHANGES</h1>
    <h4 style="margin-bottom: 0px;">tl;dr:</h4>
    <ul>
      <li><a href="#statements-as-expressions">Statements as expressions</a></li>
      <li>
        <a href="#no-new-keyword-for-constructors">Remove "new" keyword from constructor calls</a>
      </li>
      <li><a href="#target-runtimes">Add target runtimes</a></li>
      <li><a href="#primitive-null-assignments">Handle primitive null assignments</a></li>
      <li>
        <a href="#inline-local-declaration-assignments"
          >Handle inline local declaration assignments</a
        >
      </li>
      <li><a href="#bug-fixes">Bug fixes</a></li>
    </ul>
    <div anchor-button id="release-notes" class="release-notes" style="margin-bottom: 20px;">
      <div anchor-button id="statements-as-expressions" class="release-note">
        <h4>
          Statements as expressions <Issue values={[{ number: 199, repo: 'Flat' }]} />
        </h4>
        <p>
          Control structure statements can now be used as expressions. The affected statements types
          include:
        </p>
        <ul>
          <li><a href="#if-statement-expressions">If statements</a></li>
          <li><a href="#match-statement-expressions">Match statements</a></li>
        </ul>
        <div anchor-button id="if-statement-expressions" class="release-note">
          <h5>If statements</h5>
          <div anchor-button id="if-statement-expressions-returns" class="release-note">
            <h6>Returns</h6>
            <p>Previously returns would need to be written as such:</p>
            <pre class="left">
              <code class="language-flat">
                {`
                  if (file.isDirectory || file.isSymbolicLink) {
                    return location + "/flat.json"
                  } else if (file.isFile) {
                    return file.location
                  } else {
                    throw InvalidFlatJsonException("Invalid flat.json location '#file.location'")
                  }
                `}
              </code>
            </pre>
            <p>Now they can be written as such:</p>
            <pre class="left">
              <code class="language-flat">
                {`
                  return if (file.isDirectory || file.isSymbolicLink) {
                    location + "/flat.json"
                  } else if (file.isFile) {
                    file.location
                  } else {
                    throw InvalidFlatJsonException("Invalid flat.json location '#file.location'")
                  }
                `}
              </code>
            </pre>
          </div>
          <div anchor-button id="if-statement-expressions-assignments" class="release-note">
            <h6>Assignments</h6>
            <p>Previously variable assignments would need to be written as such:</p>
            <pre class="left">
              <code class="language-flat">
                {`
                  var String result

                  if (file.isDirectory || file.isSymbolicLink) {
                    result = location + "/flat.json"
                  } else if (file.isFile) {
                    result = file.location
                  } else {
                    throw InvalidFlatJsonException("Invalid flat.json location '#file.location'")
                  }
                `}
              </code>
            </pre>
            <p>Now they can be written as such:</p>
            <pre class="left">
              <code class="language-flat">
                {`
                  let result = if (file.isDirectory || file.isSymbolicLink) {
                    location + "/flat.json"
                  } else if (file.isFile) {
                    file.location
                  } else {
                    throw InvalidFlatJsonException("Invalid flat.json location '#file.location'")
                  }
                `}
              </code>
            </pre>
          </div>
        </div>
        <div anchor-button id="match-statement-expressions" class="release-note">
          <h5>Match statements</h5>
          <div anchor-button id="match-statement-expressions-returns" class="release-note">
            <h6>Returns</h6>
            <p>Previously returns would need to be written as such:</p>
            <pre class="left">
              <code class="language-flat">
                {`
                  match (package) {
                    "main" => return mainSource
                    "test" => return testSource
                    default => throw InvalidCommandException("Invalid installation package '#package'")
                  }
                `}
              </code>
            </pre>
            <p>Now they can be written as such:</p>
            <pre class="left">
              <code class="language-flat">
                {`
                  return match (package) {
                    "main" => mainSource
                    "test" => testSource
                    default => throw InvalidCommandException("Invalid installation package '#package'")
                  }
                `}
              </code>
            </pre>
          </div>
          <div anchor-button id="match-statement-expressions-assignments" class="release-note">
            <h6>Assignments</h6>
            <p>Previously variable assignments would need to be written as such:</p>
            <pre class="left">
              <code class="language-flat">
                {`
                  var Package result

                  match (package) {
                    "main" => result = mainSource
                    "test" => result = testSource
                    default => throw InvalidCommandException("Invalid installation package '#package'")
                  }
                `}
              </code>
            </pre>
            <p>Now they can be written as such:</p>
            <pre class="left">
              <code class="language-flat">
                {`
                  let result = match (package) {
                    "main" => mainSource
                    "test" => testSource
                    default => throw InvalidCommandException("Invalid installation package '#package'")
                  }
                `}
              </code>
            </pre>
          </div>
        </div>
      </div>
      <div anchor-button id="no-new-keyword-for-constructors" class="release-note">
        <h4>
          Remove "new" keyword from constructor calls <Issue
            values={[{ number: 408, repo: 'Flat' }]}
          />
        </h4>
        <p>
          The <code class="language-flat">new</code> keyword is no longer necessary (or allowed)
          when calling a constructor. It is redundant and adds extra characters, and does not add
          any extra clarity as long as basic naming conventions are followed. The change does not
          apply to array initializations (array initializations still require the
          <code class="language-flat">new</code> keyword).
        </p>
        <div anchor-button id="no-new-keyword-for-constructors-example" class="release-note">
          <h5>Example:</h5>
          <p>Previously constructors that were called like this:</p>
          <pre class="left">
            <code class="language-flat">
              {`
                CliArg("--link", String[], minCount: 0, maxCount: 1)
              `}
            </code>
          </pre>
          <p>can (and must) now be:</p>
          <pre class="left">
            <code class="language-flat">
              {`
                CliArg("--link", Array<String>(), minCount: 0, maxCount: 1)
              `}
            </code>
          </pre>
        </div>
      </div>
      <div anchor-button id="target-runtimes" class="release-note">
        <h4>
          Add target runtimes <Issue values={[{ number: 410, repo: 'Flat' }]} />
        </h4>
        <p>
          You can now use the <code class="language-flat">[TargetRuntime]</code> annotation to
          target specific runtimes for runtime specific code. For example,
          <code class="language-flat">[TargetRuntime browser]</code> can be used to only compile
          browser specific code. An example use case of this is if you want to handle logging
          differently depending on the runtime. In node you may want to use
          <code class="language-javascript">process.stdout</code>
          instead of <code class="language-javascript">console.log</code> for messages:
        </p>
        <pre class="left">
          <code class="language-flat">
            {`
              [TargetRuntime browser]
              logMessage(String message) {
                external js {
                  console.log(#{message.chars.data});
                }
              }

              [TargetRuntime not browser]
              logMessage(String message) {
                external js {
                  process.stdout.write(#{message.chars.data} + "\n");
                }
              }
            `}
          </code>
        </pre>
      </div>
      <div anchor-button id="primitive-null-assignments" class="release-note">
        <h4>Handle primitive null assignments</h4>
        <p>
          Variables of a primitive type are now handled as reference types when they are assigned as
          null.
        </p>
      </div>
      <div anchor-button id="inline-local-declaration-assignments" class="release-note">
        <h4>Handle inline local declaration assignments</h4>
        <p>
          The Flat compiler now adds metadata to local declarations and assignments so that the code
          generators can generate inline local declaration assignments.
        </p>
      </div>
      <div anchor-button id="bug-fixes" class="release-note">
        <h4>General bug fixes <Issue values={[{ number: 403, repo: 'Flat' }]} /></h4>
        <ul>
          <li>Fixed some method calls with unambiguous argument names failing as ambiguous.</li>
          <li>
            Fixed null checks being added inside wrong scope for safe navigation operators in if
            statement condition.
          </li>
          <li>Fix redundant returns from being generated to transpiled code.</li>
        </ul>
      </div>
    </div>
  </div>
</template>
