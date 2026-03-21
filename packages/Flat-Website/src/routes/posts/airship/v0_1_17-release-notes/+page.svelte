<script lang="ts">
  import Issue from '$lib/components/Issue.svelte';
</script>

<template lang="flat-html">
  <div>
    <h3><a href="/download#v0_1_17">DOWNLOAD v0.1.17</a></h3>
    <h1>NOTABLE CHANGES</h1>
    <h4 style="margin-bottom: 0px;">tl;dr:</h4>
    <ul>
      <li><a href="#bug-fixes">Bug fixes</a></li>
    </ul>
    <div id="release-notes" class="release-notes" style="margin-bottom: 20px;">
      <div anchor-button id="bug-fixes" class="release-note">
        <h4>General bug fixes</h4>
        <ul>
          <li>Fix null pointer on invalid source</li>
        </ul>
      </div>
    </div>
  </div>
</template>
