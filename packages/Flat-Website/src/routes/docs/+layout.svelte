<script lang="ts">
  import { page } from '$app/stores';
  import { docPages, currentPage, getDocFromPath } from './docs';
  import { writable } from 'svelte/store';

  import TreeBrowser from '$lib/components/TreeBrowser.svelte';
  import Share from '$lib/components/Share.svelte';

  let share = writable(false);

  page.subscribe(({ url }) => {
    currentPage.set(null);

    const prefix = '/docs/';
    const urlIndex = url.pathname.indexOf(prefix);

    if (urlIndex === 0) {
      const docPage = url.pathname.substring(urlIndex + prefix.length);

      if (docPage) {
        currentPage.set(getDocFromPath(docPage)!);
      }
    }
  });
</script>

<svelte:head>
  <title>Docs | Flat Programming Language</title>

  <link
    href="/styles/docs.css"
    rel="stylesheet"
    type="text/css"
  />
  <link
    href="/styles/posts-styles.css"
    rel="stylesheet"
    type="text/css"
  />
</svelte:head>

<template lang="flat-html">
  <main class="docs">
    <div class="container">
      <div class="navigation dark-background">
        <div class="logo-container">
          <a href="/">
            <img style="max-width: 20vw;" class="logo glow-logo" src="/images/hex-f-plain.svg" />
          </a>

          <div class="flat-text-container">
            <a href="/">
              <h1 class="flat-text" style="color: rgba(0,0,0,0);">FLAT</h1>
              <h1 class="flat-text">FLAT</h1>
            </a>
          </div>

          <a class="neon-shadow" href="/">
            <h1 class="return-home">RETURN HOME</h1>
          </a>

          <div style="margin-top: 25px;">
            <TreeBrowser data={docPages} urlPrefix="/docs" />
          </div>
        </div>
      </div>
      <div class="content white-background">
        {#if $currentPage !== null}
          <div class="content-header-container dark-border">
            <table>
              <tbody>
                <tr>
                  <td>
                    <div class="header-container">
                      {#if $currentPage.header}
                        <h1 class="content-header">{$currentPage.header.toUpperCase()}</h1>
                      {/if}
                      {#if $currentPage.subheader}
                        <h2 class="content-subheader">{$currentPage.subheader.toUpperCase()}</h2>
                      {/if}
                    </div>
                  </td>
                  <td class="export-options">
                    <h4 class="share-link nowrap" on:click={() => share.set(true)}>
                      SHARE PAGE <img class="share" src="/images/share.svg" />
                    </h4>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        {/if}
        <div class="content-view-container">
          <div class="content-view">
            <div class="view">
              <slot />
            </div>
          </div>
          {#if $currentPage}
            {#if $currentPage.references.length > 0}
              <div anchor-button id="references" class="references dark-border">
                <h2>References</h2>
                <ul class="references-list">
                  {#each $currentPage.references as ref}
                    <li>
                      <a href={ref.href}>{ref.header}</a>
                    </li>
                  {/each}
                </ul>
              </div>
            {/if}
          {/if}
        </div>
        <Share active={share} />
      </div>
    </div>
  </main>
</template>

<style>
  .flat-text-container {
    margin-top: 20px;
  }
</style>
