<script lang="ts">
  import Header from '$lib/components/Header.svelte';
  import Footer from '$lib/components/Footer.svelte';
  import Share from '$lib/components/Share.svelte';

  import { currentPage, postsPages } from './posts';
  import { page } from '$app/stores';
  import { writable } from 'svelte/store';

  let share = writable(false);

  page.subscribe(({ url }) => {
    currentPage.set(null);

    const prefix = '/posts/';
    const urlIndex = url.pathname.indexOf(prefix);

    if (urlIndex === 0) {
      const postsPage = url.pathname.substring(urlIndex + prefix.length);

      if (postsPage) {
        currentPage.set(postsPages.find((p) => p.url === postsPage));
      }
    }
  });
</script>

<template lang="flat-html">
  <div class="white-background posts">
    <div class="page-container">
      <Header />
      <div id="content-container">
        <div class="content">
          {#if $currentPage != null}
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
                        {#if $currentPage.author || $currentPage.date}
                          <p class="info">
                            {#if $currentPage.author}
                              <span class="author">{$currentPage.author}</span>
                            {/if}
                            {#if $currentPage.author && $currentPage.date}
                              <span> - </span>
                            {/if}
                            {#if $currentPage.date}
                              <span class="date">{$currentPage.date.format('MMM D, YYYY')}</span>
                            {/if}
                          </p>
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
              <div class="view{!$currentPage ? ' home' : ''}">
                <slot />
              </div>
            </div>
            {#if $currentPage && $currentPage.references && $currentPage.references.length > 0}
              <div anchor-button id="references" class="references dark-border">
                <h2>References</h2>
                <ul class="references-list">
                  {#each $currentPage.references as ref}
                    <li>
                      <a href={ref.url} title={ref.tooltip}>{ref.header}</a>
                    </li>
                  {/each}
                </ul>
              </div>
            {/if}
          </div>
        </div>
      </div>
      <Footer />
      <Share active={share} />
    </div>
  </div>
</template>

<svelte:head>
  <title>Posts | Flat Programming Language</title>

  <link href="/styles/docs.css" rel="stylesheet" type="text/css" />
  <link href="/styles/posts.css" rel="stylesheet" type="text/css" />
  <link href="/styles/posts-styles.css" rel="stylesheet" type="text/css" />
</svelte:head>
