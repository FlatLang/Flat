<script lang="ts">
  export let values;
</script>

<span class="issues">
  [Issue
  {#each values as issue, i}
    <span
      >{i > 0 ? ', ' : ''}<a
        target="_blank"
        href="https://github.com/{issue.user || 'FlatLang'}/{issue.repo}/issues/{issue.number}"
        >#{issue.number}</a
      ></span
    >{/each}]
</span>
