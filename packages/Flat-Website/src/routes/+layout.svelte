<script lang="ts">
  import { checkHash } from '$lib/flash';
  import { afterNavigate, beforeNavigate } from '$app/navigation';
  import { clearFootnotes } from '$lib/components/footnotes';
  import { resetSlides } from '$lib/slide';

  beforeNavigate(() => {
    resetSlides();
  });

  afterNavigate(() => {
    checkHash();
    clearFootnotes();
  });
</script>

<slot />
