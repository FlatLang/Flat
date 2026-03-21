<script lang="ts">
  import { page } from '$app/stores';
  import { currentPage } from 'src/routes/docs/docs';
  import type { DocPage } from 'src/routes/docs/docs';

  import TreeBrowser from './TreeBrowser.svelte';

  export let isRoot = true;
  export let open = isRoot;
  export let data: DocPage[];
  export let urlPrefix = '';

  let selectedItem: DocPage;
  let opened = { [urlPrefix]: open };

  page.subscribe(({ url }) => {
    const postUrl = url.pathname.substring('/docs/'.length);
    const item = data.find((c) => c.path === postUrl);

    selectedItem = item!;
  });

  currentPage.subscribe(() => {
    const value = data.find((c) => c.path && $currentPage?.path?.startsWith(c.path));

    if (value) {
      open = true;

      if (value.path) {
        opened[value.path] = true;
      }
    }
  });

  const toggle = (item: DocPage, target: EventTarget | null) => {
    const element = target as HTMLElement;

    if (!element?.classList.contains('link')) {
      return;
    }

    if (item.path) {
      const prev = opened[item.path];

      if (selectedItem === item) {
        opened[item.path] = !prev;
      } else {
        opened[item.path] = true;
      }
    }
  };
</script>

<div class="tree-browser{isRoot ? ' root' : ' sub-tree-browser'}" class:open>
  {#each data as item}
    {#if item.visible !== false}
      <div
        class="tree-item"
        class:children={item.children.length > 0}
        class:open={opened[item.path]}
        class:selected={selectedItem === item}
      >
        <table on:click={(event) => toggle(item, event.target)}>
          <tr>
            <td class="arrow-container">
              <div
                class="arrow{item.children?.length > 0 ? '' : ' invisible'}"
                class:invisible={!item.children || item.children.length === 0}
                class:down={opened[item.path]}
              />
            </td>
            <td class="link-container">
              <a
                class="link"
                href="{urlPrefix}/{item.url}"
                title={item.tooltip}
                class:selected={selectedItem === item}
              >
                {item.header}
              </a>
            </td>
          </tr>
        </table>
        {#if item.children?.length > 0}
          <TreeBrowser
            isRoot={false}
            open={opened[item.path]}
            data={item.children}
            urlPrefix="{urlPrefix}/{item.url}"
          />
        {/if}
      </div>
    {/if}
  {/each}
</div>
