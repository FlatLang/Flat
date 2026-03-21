<template lang="flat-html">
  <h1>CREATING A HELLO WORLD PROGRAM</h1>
  <p style="margin-top: 50px;">
    After having <a href="/download">downloaded</a> and installed
    <a href="/download#installation">Airship</a>, run
    <code bash>airship init HelloWorld</code> and you should see this output:
  </p>
  <pre style="margin-top: 20px;">
    <code bash>
      {`
        $ airship init HelloWorld
        This utility will walk you through creating a flat.json file. It
        only covers the most common items, and tries to guess sensible
        defaults.

        Use \`airship add <pkg>\` afterwards to install a package and save
        it as a dependency in the flat.json file.

        Press ^C at any time to quit.
        Package name (default: HelloWorld):
        Initialize package in folder: "C:/Users/home/HelloWorld"? (Y/n): y
        Package version (default: 0.1.0):
        Package description: Flat Hello World program
        Package author: Your Name
        Package license (default: ISC):
        Default compilation target (default: es6):
        Main class (e.g.: example/ClassName) [optional - press enter to skip]: HelloWorld
        Create a testing suite (HelloWorld_TestSuite)? (Y/n): y
        Create a testing pipeline? (Y/n): y
        Pipeline file (default: .github/workflows/test.yml):
        Pipeline file created at "C:/Users/home/HelloWorld/.github/workflows/test.yml"
      `}
    </code>
  </pre>
  <p style="margin-top: 50px;">
    Once the initialization is finished, you should see a file with these contents at
    <code bash>HelloWorld/src/HelloWorld.flat</code>:
  </p>
  <pre style="margin-top: 20px;">
    <code>
      {`
        class {
          public static main(String[] args) {

          }
        }
      `}
    </code>
  </pre>
  <p style="margin-top: 50px;">
    Now you can add a console logging statement to the main function like this:
  </p>
  <pre style="margin-top: 20px;">
    <code>
      {`
        class {
          public static main(String[] args) {
            Console.writeLine("Hello, world!")
          }
        }
      `}
    </code>
  </pre>
  <p style="margin-top: 50px;">Now you can install the HelloWorld program:</p>
  <pre style="margin-top: 20px;">
    <code bash>
      airship install
    </code>
  </pre>
  <p style="margin-top: 50px;">
    You should see a new js file generated at <code bash>HelloWorld/dist/index.js</code>
  </p>
  <p>You can now run the HelloWorld program:</p>
  <pre style="margin-top: 20px;">
    <code bash>
      node dist/index.js
    </code>
  </pre>
  <p style="margin-top: 50px;">And you should see this logged out into to the console:</p>
  <pre style="margin-top: 20px;">
    <code bash>
      Hello, world!
    </code>
  </pre>
</template>
