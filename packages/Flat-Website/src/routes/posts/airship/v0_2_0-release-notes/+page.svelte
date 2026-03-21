<script lang="ts">
  import Issue from '$lib/components/Issue.svelte';
</script>

<template lang="flat-html">
  <div>
    <h3><a href="/download#v0_2_0">DOWNLOAD v0.2.0</a></h3>
    <h1>NOTABLE CHANGES</h1>
    <h4 style="margin-bottom: 0px;">tl;dr:</h4>
    <ul>
      <li><a href="#init-command">Add <code bash>init</code> command</a></li>
      <li><a href="#create-command">Add <code bash>create</code> command</a></li>
      <li><a href="#default-exports">Add support for default exports</a></li>
      <li><a href="#bug-fixes">Bug fixes</a></li>
    </ul>
    <div id="release-notes" class="release-notes" style="margin-bottom: 20px;">
      <div anchor-button id="init-command" class="release-note">
        <h4>
          Add <code bash>init</code> command <Issue values={[{ number: 1, repo: 'Airship' }]} />
        </h4>
        <p>
          You can now run <code bash>./airship init</code> and it will guide you through the process
          of creating a new Flat project.
        </p>
        <pre>
          <code bash>
            $ node ./airship.js init
            Package name (default: home): NewProject
            Initialize package in current folder: "C:/Users/home"? (Y/n): n
            Folder name (default: NewProject): NewProject
            Package version (default: 0.1.0): 0.1.0
            Package description: my fancy description
            Package author: Bob
            Package license (default: ISC): ISC
            Default compilation target (default: es6): es6
            Main class (e.g.: example/ClassName) [optional - press enter to skip]: bob/NewProject
            Create a testing pipeline? (Y/n): y
            Pipeline file (default: .github/workflows/test.yml):
            Pipeline file created at "C:/Users/home/NewProject/.github/workflows/test.yml"
          </code>
        </pre>
      </div>
      <div anchor-button id="create-command" class="release-note">
        <h4>
          Add <code bash>create</code> command <Issue values={[{ number: 7, repo: 'Airship' }]} />
        </h4>
        <p>
          You can now run <code bash>{`./airship create {thing}`}</code> and it will guide you through
          the process of creating a new component.
        </p>
        <pre>
          <code bash>
            $ node ./airship.js create pipeline
            Pipeline file (default: .github/workflows/test.yml):
            Pipeline file created at "C:/Users/home/NewProject/.github/workflows/test.yml"
          </code>
        </pre>
      </div>
      <div anchor-button id="default-exports" class="release-note">
        <h4>Add support for default exports <Issue values={[{ number: 6, repo: 'Airship' }]} /></h4>
        <p>Default exports can now be defined in the flat.json file as such:</p>
        <pre>
          <code json>
            {`
              {
                "name": "IO",
                "version": "0.1.0",

                ...

                "main": {
                  ...

                  "defaultExports": [
                    "flat/io/Console"
                  ]
                }
              }
            `}
          </code>
        </pre>
        <p>
          This allows any package that imports this package as a dependency to automatically have
          <code bash>flat/io/Console</code> imported in all files.
        </p>
      </div>
      <div anchor-button id="bug-fixes" class="release-note">
        <h4>General bug fixes</h4>
        <ul>
          <li>Fix duplicate sources being added in some circumstances</li>
          <li>Only include current package test source files when doing a test build</li>
        </ul>
      </div>
    </div>
  </div>
</template>
