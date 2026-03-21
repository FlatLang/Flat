<script lang="ts">
  import Issue from '$lib/components/Issue.svelte';
</script>

<template lang="flat-html">
  <div>
    <h3><a href="/download">DOWNLOAD BETA v0.3.9</a></h3>
    <h1>NOTABLE CHANGES</h1>
    <h4 style="margin-bottom: 0px;">tl;dr:</h4>
    <ul>
      <li><a href="#null-function-references">Function references compatible with null</a></li>
    </ul>
    <div anchor-button id="release-notes" class="release-notes" style="margin-bottom: 20px;">
      <div anchor-button id="null-function-references" class="release-note">
        <h4>
          Function references now compatible with null <Issue
            values={[{ number: 377, repo: 'Flat' }]}
          />
        </h4>
        <p>
          More info on first class functions discussed in <a href="/posts/first-class-functions"
            >this post</a
          >.
        </p>
      </div>
      <div anchor-button id="bug-fixes" class="release-note">
        <h4>General bug fixes</h4>
        <p>Fixed function types without lambda assignment being treated as lambda declarations.</p>
      </div>
    </div>
  </div>
</template>
