<script lang="ts">
  import { getFootnote } from '$lib/components/footnotes';

  export let id;

  let footnote = getFootnote(id);
</script>

<a href="#{id}" class="footnote">{footnote.number}</a>
