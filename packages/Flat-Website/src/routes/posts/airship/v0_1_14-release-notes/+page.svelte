<template lang="flat-html">
  <div>
    <h3><a href="/download#v0_1_14">DOWNLOAD v0.1.14</a></h3>
    <h1>NOTABLE CHANGES</h1>
    <h4 style="margin-bottom: 0px;">tl;dr:</h4>
    <ul>
      <li><a href="#es6-target">Handle ES6 compilation target</a></li>
      <li><a href="#infrastructure-updates">Infrastructure updates</a></li>
      <li><a href="#bug-fixes">Bug fixes</a></li>
    </ul>
    <div id="release-notes" class="release-notes" style="margin-bottom: 20px;">
      <div anchor-button id="es6-target" class="release-note">
        <h4>Handle ES6 Compilation Target</h4>
        <p>
          The ES6 compilation target is now handled as a sub-target of js. That means that any
          existing js configuration files that are not overridden with ES6 ones will apply to ES6
          builds. This makes it so common configuration that is likely shared between vanilla js and
          es6 target builds can easily be shared without having to duplicate the configuration.
        </p>
      </div>
      <div anchor-button id="infrastructure-updates" class="release-note">
        <h4>Infrastructure Updates</h4>
        <p>Airship is now tested against vanilla JS and ES6 on every push to GitHub.</p>
      </div>
      <div anchor-button id="bug-fixes" class="release-note">
        <h4>General bug fixes</h4>
        <p>The --target argument is now propagated to child dependencies.</p>
      </div>
    </div>
  </div>
</template>
