<script lang="ts">
  import { browser } from '$app/environment';
  import { goto } from '$app/navigation';
  import { page } from '$app/stores';
  import { postsPages, currentPage, type PostsPage } from './posts';

  currentPage.set(null);

  let limit: boolean;
  let limited = false;
  let pages: PostsPage[] = [];
  let searchValue: string;

  const toggleLimit = () => {
    updateQueryParam('showAll', limit.toString());

    limit = !limit;

    updateResults();
  };

  const updateSearch = () => {
    updateQueryParam('search', searchValue);

    updateResults();
  };

  const updateQueryParam = (name: string, value: string) => {
    if (!value) {
      $page.url.searchParams.delete(name);
    } else {
      $page.url.searchParams.set(name, value);
    }

    goto(`?${$page.url.searchParams.toString()}`, {
      replaceState: false,
      noScroll: true,
      keepFocus: true,
    });
  };

  const updateResults = () => {
    limited = false;
    pages = [...postsPages]
      .filter((p) => p.visible !== false)
      .filter(
        (p) =>
          !searchValue ||
          p.header.toLocaleLowerCase().indexOf(searchValue.toLocaleLowerCase()) !== -1,
      );

    pages.sort((a, b) => {
      const value = b.date.diff(a.date);

      if (value !== 0) {
        return value;
      }

      return pages.indexOf(b) - pages.indexOf(a);
    });

    if (limit && pages.length > 10) {
      pages = pages.slice(0, 10);
      limited = true;
    }
  };

  page.subscribe(() => {
    if (browser) {
      searchValue = $page.url.searchParams.get('search') || '';
      limit = !$page.url.searchParams.get('showAll');
    } else {
      limit = true;
    }

    updateResults();
  });

  updateResults();
</script>

<div class="default-view">
  <table class="recent-posts-header">
    <tr>
      <td>
        <h1>
          {limit ? 'RECENT POSTS' : 'ALL POSTS'}
          {#if limit && limited}<span class="show-all" on:click={() => toggleLimit()}>Show all</span
            >{/if}
        </h1>
      </td>
      <td class="search">
        <input
          type="text"
          bind:value={searchValue}
          on:input={() => updateSearch()}
          placeholder="SEARCH POSTS"
          aria-label="Search posts"
        />
      </td>
    </tr>
  </table>
  <hr />
  <div class="post-list">
    {#each pages as page}
      <div class="post">
        <h3><a href="/posts/{page.url}"><span>{page.header}</span></a></h3>
        <h6><span class="date">{page.date.format('MMM D, YYYY')}</span></h6>
      </div>
    {/each}
    {#if limit && limited}<h3 class="show-all" on:click={() => toggleLimit()}>Show all</h3>{/if}
  </div>
</div>

<svelte:head>
  <link
    href="/styles/posts-home.css"
    rel="stylesheet"
    type="text/css"
  />
</svelte:head>
