<script lang="ts">
  import { page } from '$app/stores';
  import { writable } from 'svelte/store';
  import { clickOutside } from '$lib/clickOutside';

  export let active = writable(false);
  let url: string;

  let urlElement;
  let copied = false;

  active.subscribe((value) => {
    if (value && urlElement) {
      urlElement.select();
    }
  });

  page.subscribe((page) => {
    url = page.url.href;
  });

  let timeoutRef;

  const copyToClipboard = () => {
    navigator.clipboard.writeText(urlElement.value);
    copied = true;

    if (timeoutRef) {
      clearTimeout(timeoutRef);
    }

    timeoutRef = setTimeout(() => {
      copied = false;

      clearTimeout(timeoutRef);
    }, 1500);
  };

  const fbShare = (url, title, descr, winWidth, winHeight) => {
    const winTop = screen.height / 2 - winHeight / 2;
    const winLeft = screen.width / 2 - winWidth / 2;

    title = title || '';
    descr = descr || '';

    window.open(
      'http://www.facebook.com/sharer.php?s=100&p[title]=' +
        title +
        '&p[summary]=' +
        descr +
        '&p[url]=' +
        url,
      'sharer',
      'top=' +
        winTop +
        ',left=' +
        winLeft +
        ',toolbar=0,status=0,width=' +
        winWidth +
        ',height=' +
        winHeight,
    );
  };

  const twitterShare = (url, title, descr, winWidth, winHeight) => {
    const winTop = screen.height / 2 - winHeight / 2;
    const winLeft = screen.width / 2 - winWidth / 2;

    title = title || '';
    descr = descr || '';

    window.open(
      'https://twitter.com/home?status=Check%20out%20this%20Flat%20post%20' + url,
      'sharer',
      'top=' +
        winTop +
        ',left=' +
        winLeft +
        ',toolbar=0,status=0,width=' +
        winWidth +
        ',height=' +
        winHeight,
    );
  };

  const linkedinShare = (url, title, descr, winWidth, winHeight) => {
    const winTop = screen.height / 2 - winHeight / 2;
    const winLeft = screen.width / 2 - winWidth / 2;

    title = title || '';
    descr = descr || '';

    window.open(
      `https://www.linkedin.com/shareArticle?mini=true&url=${url}&title=Check%20out%20this%20Flat%20posts%20post&summary=&source=`,
      'sharer',
      'top=' +
        winTop +
        ',left=' +
        winLeft +
        ',toolbar=0,status=0,width=' +
        winWidth +
        ',height=' +
        winHeight,
    );
  };
</script>

<template lang="flat-html">
  {#if $active}<div class="share-modal-background" />{/if}
  <div
    class="share-modal neon-shadow"
    class:active={$active}
    use:clickOutside
    on:clickoutside={() => active.set(false)}
  >
    <table>
      <tr>
        <td colspan="4">
          <h1 class="neon-shadow">
            SHARE THIS PAGE
            <a href="/" class="copy-link" on:click|preventDefault={() => copyToClipboard()}>
              {#if copied}
                COPIED!
              {:else}
                COPY
              {/if}
            </a>
          </h1>
        </td>
      </tr>
      <tr>
        <td colspan="4">
          <input
            class="url"
            type="text"
            value={url}
            readonly="readonly"
            bind:this={urlElement}
            aria-label="Share page link"
          />
        </td>
      </tr>
      {#if false}
        <tr>
          <td>
            <a href="/" on:click={() => fbShare(url, '', '', 520, 350)}>
              <img src="/images/facebook.svg" />
            </a>
          </td>
          <td>
            <a href="/" on:click={() => twitterShare(url, '', '', 520, 350)}>
              <img src="/images/twitter.svg" />
            </a>
          </td>
          <td>
            <a href="/" on:click={() => linkedinShare(url, '', '', 520, 350)}>
              <img src="/images/linkedin.svg" />
            </a>
          </td>
          <td>
            <a
              href="mailto:?&subject=Flat programming language&body=Check%20out%20this%20page%20on%20the%20Flat%20programming%20language%20site%3A%20{url}"
            >
              <img src="/images/email.svg" />
            </a>
          </td>
        </tr>
      {/if}
    </table>
  </div>
</template>
