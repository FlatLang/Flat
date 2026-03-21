<template lang="flat-html">
  <div>
    <h3><a href="/download#v0_1_15">DOWNLOAD v0.1.15</a></h3>
    <h1>NOTABLE CHANGES</h1>
    <h4 style="margin-bottom: 0px;">tl;dr:</h4>
    <ul>
      <li><a href="#setup-command">Add basic <code bash>setup</code> command</a></li>
      <li><a href="#target-runtime">Add <code bash>--target-runtime</code> CLI arg</a></li>
      <li><a href="#bug-fixes">Bug fixes</a></li>
    </ul>
    <div id="release-notes" class="release-notes" style="margin-bottom: 20px;">
      <div anchor-button id="setup-command" class="release-note">
        <h4>Add basic <code bash>setup</code> command</h4>
        <p>
          To make Flat installation quick and painless, there is now a <code bash>setup</code> command.
        </p>
        <p>
          When Airship is run with this command (e.g. <code bash>./airship setup</code>) it will
          install Airship and all the necessary Flat dependencies from their sources.
        </p>
      </div>
      <div anchor-button id="target-runtime" class="release-note">
        <h4>Add <code bash>--target-runtime</code> CLI arg</h4>
        <p>
          You can now use the <code bash>--target-runtime</code> CLI argument to target specific
          runtimes for runtime-specific code. For example, <code>--target-runtime browser</code> can
          be used to only compile browser-specific code. An example use case of this is if you want
          to handle logging differently depending on the runtime. In node, you may want to use
          <code javascript>process.stdout</code> instead of <code javascript>console.log</code> for messages:
        </p>
        <pre class="left">
          <code>
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
      <div anchor-button id="bug-fixes" class="release-note">
        <h4>General bug fixes</h4>
        <p>Fix <code bash>./airship clean</code> command.</p>
        <p>Rebuild dependency after updating dependency.</p>
      </div>
    </div>
  </div>
</template>
