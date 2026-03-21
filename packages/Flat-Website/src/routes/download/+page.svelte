<script lang="ts">
  import Header from '$lib/components/Header.svelte';
  import Footer from '$lib/components/Footer.svelte';
  import dayjs from 'dayjs';

  import type { GitHubRelease, OsRelease, OsAsset } from './types';

  import { jscd, defer, fetchJson } from '$lib/util';
  import { postsPages } from 'src/routes/posts/posts';
  import { checkHash } from '$lib/flash';
  import { browser } from '$app/environment';
  import { writable } from 'svelte/store';
  import { onMount } from 'svelte';

  function createAsset(
    release: GitHubRelease,
    name: string,
    assetName: string,
    assetMatcher: (assetName: string) => boolean,
  ): OsAsset {
    const { assets } = release;

    return {
      name,
      assetName,
      assetMatcher,
      asset: assets.find((a) => a.name === assetName),
      showMoreFormats: true,
      otherFormats: assets.filter((a) => a.name !== assetName && assetMatcher(a.name)),
    };
  }

  function createOsRelease(release: GitHubRelease): OsRelease {
    const { name, html_url } = release;

    const postsPostUrl = 'airship/' + name.replace(/[\.]/g, '_') + '-release-notes';
    const page = postsPages.find((page) => page.url === postsPostUrl);

    function prefixedWith(value: string): (name: string) => boolean {
      return (name) => name.startsWith(value);
    }

    const value = {
      assets: [
        createAsset(release, 'windows', 'airship-win.exe', prefixedWith('airship-win')),
        createAsset(release, 'mac', 'airship-macos', prefixedWith('airship-macos')),
        createAsset(release, 'linux', 'airship-linux', prefixedWith('airship-linux')),
        createAsset(release, 'node', 'airship.js', prefixedWith('airship.')),
      ],
      version: name,
      url: html_url,
      releaseNotesUrl: page ? `/posts/${page.url}` : undefined,
      createdAt: dayjs(release.created_at),
    };

    value.assets.sort((a, b) => {
      if (lowerOs === a.name) {
        return -1;
      } else if (lowerOs === b.name) {
        return 1;
      } else {
        return 0;
      }
    });

    return value;
  }

  function getReleaseTagFromHash(): string | null {
    if (!browser) {
      return null;
    }

    const hashValue = window.location.hash?.substring(1);

    if (hashValue && /v\d+_\d+_\d+/g.test(hashValue)) {
      return hashValue.replace(/_/g, '.');
    } else {
      return null;
    }
  }

  const releases = writable(defer<OsRelease[]>());
  const releaseTag = getReleaseTagFromHash();
  const apiRoot = `https://api.github.com/repos/FlatLang/Airship`;
  const releaseUrl = `${apiRoot}/releases/${releaseTag ? 'tags/' + releaseTag : 'latest'}`;

  async function fetchInitialRelease() {
    $releases.resolve([createOsRelease(await fetchJson<GitHubRelease>(releaseUrl))]);

    checkHash();
  }

  onMount(async () => {
    await fetchInitialRelease();
  });

  async function toggleShowAll() {
    showAll = !showAll;

    if (showAll) {
      const otherReleases = await fetchJson<GitHubRelease[]>(`${apiRoot}/releases`);
      const osReleases = otherReleases
        .map(createOsRelease)
        .sort((a, b) => b.createdAt.diff(a.createdAt));

      releases.set(defer<OsRelease[]>().resolve(osReleases));

      checkHash();
    }
  }

  function getSize(size: number) {
    if (size < 1024) {
      return `${size}B`;
    } else if (size / 1024 < 1024) {
      return `${(size / 1024).toFixed(1)}KiB`;
    } else if (size / 1024 / 1024 < 1024) {
      return `${(size / 1024 / 1024).toFixed(1)}MiB`;
    } else {
      return `${(size / 1024 / 1024 / 1024).toFixed(1)}GiB`;
    }
  }

  let whyJava = false;
  let whyMaven = false;
  let whyGit = false;
  let showAll = false;

  let os = jscd.os || '';
  let osVersion = jscd.osVersion;
  let lowerOs = os?.toLowerCase();
  let osHeader = os + (osVersion?.trim() ? ' ' + osVersion : '');

  if (lowerOs?.indexOf('mac') == 0) {
    lowerOs = 'mac';
  }

  function formatClassName(value: string): string {
    return value
      .replace(/ /g, '-')
      .replace(/[^\w-]/g, '_')
      .toLocaleLowerCase();
  }
</script>

<svelte:head>
  <title>Download | Flat Programming Language</title>

  <link href="/styles/download.css" rel="stylesheet" type="text/css" />
</svelte:head>

<template lang="flat-html">
  <div class="white-background download">
    <div class="page-container">
      <Header />
      <div id="content-container">
        <section anchor-button id="downloads">
          <h1 class="primary">DOWNLOAD</h1>
          <hr />
          <p>
            The recommended way to install Flat is with Flat's package manager <a
              target="_blank"
              href="https://github.com/FlatLang/Airship">Airship</a
            >. You can download a native binary for Airship for your OS below, or you can download
            the node
            {#await $releases.promise}
              <a on:click|preventDefault={() => {}} href="#node">airship.js</a>
            {:then releases}
              <a href="#{formatClassName(releases[0].version)}-node">airship.js</a>
            {/await}
            script file and run it directly with node 14 or later.
          </p>
          <p>
            Once you have downloaded Airship, you can continue on with the <a href="#installation"
              >installation steps below</a
            >.
          </p>
          {#await $releases.promise}
            <h4>Loading...</h4>
          {:then releases}
            {#each releases as release, i}
              {#if i > 0}<hr />{/if}
              <div anchor-button id={formatClassName(release.version)}>
                <h4>
                  Airship {release.version}
                  <span class="date gray">{release.createdAt.format('MMMM D, YYYY')}</span>
                </h4>
                [<a target="_blank" href={release.url}>GitHub</a>]
                {#if release.releaseNotesUrl}
                  [<a href={release.releaseNotesUrl}>Release Notes</a>]
                {/if}
                <ul class="downloads-list">
                  {#each release.assets as osAsset}
                    {@const asset = osAsset.asset}
                    {#if asset}
                      <li>
                        <div id={formatClassName(`${release.version}-${osAsset.name}`)}>
                          Download <a href={asset.browser_download_url}>{asset.name}</a>
                          <span class="asset-size">({getSize(asset.size)})</span>
                          {#if osAsset.otherFormats.length > 0 && !osAsset.showMoreFormats}
                            [<a
                              href="/"
                              on:click|preventDefault={() => (osAsset.showMoreFormats = true)}
                              >more formats...</a
                            >]
                          {/if}
                          {#if lowerOs === osAsset.name}
                            <span class="gray os-comment"
                              >// We think you are running {osHeader}</span
                            >
                          {/if}
                          {#if osAsset.showMoreFormats}
                            <div class="more-formats">
                              <ul>
                                {#each osAsset.otherFormats as asset}
                                  <li>
                                    <a href={asset.browser_download_url}>{asset.name}</a>
                                    <span class="asset-size">({getSize(asset.size)})</span>
                                  </li>
                                {/each}
                              </ul>
                            </div>
                          {/if}
                        </div>
                      </li>
                    {/if}
                  {/each}
                </ul>
              </div>
            {/each}
            {#if !showAll}
              <a href="/" on:click|preventDefault={() => toggleShowAll()}>Show all</a>
            {/if}
          {:catch error}
            {error}
          {/await}
        </section>
        <section anchor-button id="installation">
          <h1 class="primary">INSTALLATION</h1>
          <hr />
          <h4 anchor-button id="installation-prerequisites">Pre-requisites</h4>
          <ul>
            <li>
              Java Runtime 1.8 or later
              <span class="why gray">
                {#if !whyJava}<span class="tooltip" on:click={() => (whyJava = true)}
                    >why Java?</span
                  >{/if}
                {#if whyJava}<span>The first iteration of the compiler is written in Java</span
                  >{/if}
              </span>
            </li>
            <li>
              Maven
              <span class="why gray">
                {#if !whyMaven}<span class="tooltip" on:click={() => (whyMaven = true)}
                    >why Maven?</span
                  >{/if}
                {#if whyMaven}<span
                    >The first iteration of the compiler requires Maven to build the compiler</span
                  >{/if}
              </span>
            </li>
            <li>
              Git
              <span class="why gray">
                {#if !whyGit}<span class="tooltip" on:click={() => (whyGit = true)}>why Git?</span
                  >{/if}
                {#if whyGit}<span>Git is used to download project dependencies</span>{/if}
              </span>
            </li>
          </ul>
          <h4 anchor-button id="installation-prepare-executable">
            Prepare executable for execution
          </h4>
          <ul>
            <li>Windows: The executable requires no preparation.</li>
            <li>
              Mac: Give the executable permission to run: <code bash>chmod +x airship-macos</code>
            </li>
            <li>
              Linux: Give the executable permission to run: <code bash>chmod +x airship-linux</code>
            </li>
          </ul>
          <h4 anchor-button id="installation-run-setup-command">Run the setup command</h4>
          <ul>
            <li>Windows: Run <code bash>./airship-win.exe setup</code></li>
            <li>Mac: Run <code bash>./airship-macos setup</code></li>
            <li>Linux: Run <code bash>./airship-linux setup</code></li>
          </ul>
          <p>
            After the installer has finished, you are ready to <a
              href="/docs/getting-started/hello-world">write your first program</a
            >.
          </p>
        </section>
      </div>
      <Footer />
    </div>
  </div>
</template>
