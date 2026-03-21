<script lang="ts">
  import Issue from '$lib/components/Issue.svelte';
</script>

<template lang="flat-html">
  <div>
    <h3><a href="/download">DOWNLOAD BETA v0.3.7</a></h3>
    <h1>NOTABLE CHANGES</h1>
    <div id="release-notes" class="release-notes" style="margin-bottom: 20px;">
      <div anchor-button id="thread_local" class="release-note">
        <h4>Added thread_local annotation modifier</h4>
        <p>
          As explained in <a href="/posts/thread-local-storage">this post</a>, allows thread local
          storage (TLS).
        </p>
      </div>
      <div anchor-button id="compiler_visible" class="release-note">
        <h4>Added compiler_visible annotation modifier</h4>
        <p>
          New annotation/modifier used to set a field or function's visbility to public before
          code-gen.
        </p>
      </div>
      <div anchor-button id="external_name" class="release-note">
        <h4>Added external_name annotation modifier</h4>
        <p>New annotation/modifier used to set the outputted name in the external source.</p>
      </div>
      <div anchor-button id="tls-exception-data" class="release-note">
        <h4>
          Updated ExceptionData to be kept track of through <a href="/posts/thread-local-storage"
            >TLS</a
          > instead of being passed by parameter
        </h4>
        <p>
          ExceptionData is a structure that is used to keep track of what types of exceptions are
          being caught and where they are being caught at. Previously, every function required an
          ExceptionData parameter to be supplied. This offers slightly better performance over TLS,
          but is not good for compatibility of function references across external code. For
          instance, if an external C function were to interface with a Flat function reference, it
          would have to specify the second parameter as ExceptionData:
        </p>
        <p>With the following Flat function definition:</p>
        <pre>
          <code class="language-flat">
            {`
              class FancyClass {
                public static myFancyFunc(String something) {
                  Console.writeLine(something)
                }
              }
            `}
          </code>
        </pre>
        <p>And external C code:</p>
        <pre>
          <code class="language-c">
            {`
              typedef void (*funcReference)(void*, flat_exception_Flat_ExceptionData*, flat_Flat_String*);

              void my_external_func(funcReference ref) {
                // Passing 0 as first argument for the 'this' parameter of the function because
                // the myFancyFunc is static and does not use the reference parameter.

                // Passing 0 as second argument because there is no ExceptionData in this context
                // available to pass! This is what has been fixed...
                ref(0, 0, flat_Flat_String_1_Flat_construct(0, 0, "Hello, world"));

                // The first and second argument of flat_Flat_String_1_Flat_construct are 0
                // for the same reasons as stated above.
              }
            `}
          </code>
        </pre>
        <p>
          This is the general idea of the code that was required to call a function reference that
          was passed from Flat. Now, the second parameter of type flat_exception_Flat_ExceptionData*
          has been removed. The function implementation would now look like:
        </p>
        <pre>
          <code class="language-c">
            {`
              typedef void (*funcReference)(void*, flat_Flat_String*);

              void my_external_func(funcReference ref) {
                // No need for passing a second 0
                ref(0, flat_Flat_String_1_Flat_construct(0, "Hello, world"));
              }
            `}
          </code>
        </pre>
        <p>
          This approach is cleaner and <b>safer</b>. Because the exceptionData variable is
          referenced from TLS by Flat generated code, in instances such as the previous code where
          external code is calling Flat generated code, the ExceptionData is always available. In
          the previous code, if an exception was thrown somewhere insided the funcReference function
          call, there would have been a segmentation fault. This is because we were passing in 0 as
          the ExceptionData. Referencing it from TLS removes the responsibility from the user to
          pass ExceptionData in external code.
        </p>
      </div>
      <div anchor-button id="bug-fixes" class="release-note">
        <h4>General bug fixes <Issue values={[{ number: 367, repo: 'Flat' }]} /></h4>
        <p>
          Fixed some bugs with primtive overloads <Issue
            values={[{ number: 367, repo: 'Flat' }]}
          />.
        </p>
      </div>
    </div>
  </div>
</template>
