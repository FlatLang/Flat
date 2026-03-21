import { writable } from 'svelte/store';

export interface DocPage {
  header: string;
  subheader?: string;
  url: string;
  references: any[];
  children: DocPage[];
  tooltip?: string;
  css?: string;
  parent?: DocPage;
  path: string;
  href?: string;
  visible?: boolean;
}

const docPages: DocPage[] = [
  {
    header: 'Class types',
    url: 'class-types',
    visible: false,
    references: [],
    children: [
      {
        header: 'Classes',
        url: 'classes',
        references: ['class-types/traits', 'class-types/interfaces'],
        children: [],
        path: '',
      },
      {
        header: 'Interfaces',
        url: 'interfaces',
        references: ['class-types/traits', 'class-types/classes'],
        children: [],
        path: '',
      },
      {
        header: 'Traits',
        url: 'traits',
        references: ['class-types/classes', 'class-types/interfaces'],
        children: [],
        path: '',
      },
    ],
    path: '',
  },
  {
    header: 'Data structures',
    url: 'data-structures',
    visible: false,
    references: ['data-structures/lists'],
    children: [
      {
        header: 'Lists',
        url: 'lists',
        tooltip: 'Fundamental collection datatype',
        references: ['data-structures/arrays'],
        children: [],
        path: '',
      },
      {
        header: 'Arrays',
        url: 'arrays',
        references: ['data-structures/lists'],
        children: [],
        path: '',
      },
    ],
    path: '',
  },
  {
    header: 'Getting started',
    url: 'getting-started',
    references: [],
    children: [
      {
        header: 'Configure environment',
        url: 'configure-environment',
        visible: false,
        css: '/docs/getting-started/configure-environment.css',
        references: [],
        children: [],
        path: '',
      },
      {
        header: 'Hello world',
        url: 'hello-world',
        references: [
          {
            header: 'Downloading flat',
            href: '/download#downloads',
          },
        ],
        children: [],
        path: '',
      },
    ],
    path: '',
  },
];

function setParent(page: DocPage) {
  page.children?.forEach((child) => {
    child.parent = page;

    setParent(child);
  });
}

docPages.forEach(setParent);

function getUrl(page: DocPage) {
  let value = page.url;
  let current = page.parent;

  while (current) {
    value = `${current.url}/${value}`;

    current = current.parent;
  }

  return value;
}

function setPaths(page: DocPage, prefix: string) {
  if (prefix) {
    page.path = `${prefix}/${page.url}`;
  } else {
    page.path = page.url;
  }

  page.children?.forEach((p) => setPaths(p, page.path!));
}

docPages.forEach((page) => setPaths(page, ''));

function setHrefs(page: DocPage, prefix: string) {
  page.href = `${prefix}/${page.url}`;

  page.children?.forEach((p) => setHrefs(p, page.href!));
}

docPages.forEach((page) => setHrefs(page, '/docs'));

function getDoc(url: string, page?: DocPage): DocPage | undefined {
  if (!page) {
    for (let child of docPages) {
      const doc = getDoc(url, child);

      if (doc) {
        return doc;
      }
    }

    return;
  }
  if (getUrl(page) === url) {
    return page;
  }

  if (!page.children) {
    return;
  }

  for (let child of page.children) {
    const doc = getDoc(url, child);

    if (doc) {
      return doc;
    }
  }
}

function updateReferences(page: DocPage) {
  page.references = page.references
    ?.map((ref) => {
      if (typeof ref === 'string') {
        const doc = getDoc(ref);

        if (!doc) {
          throw new Error(`Could not find doc '${ref}'`);
        }

        return {
          header: doc.header,
          url: doc.path,
        };
      }

      return ref;
    })
    ?.map((ref) => {
      if (ref.url) {
        const doc = getDoc(ref.url);

        if (!doc) {
          throw new Error(`Could not find doc '${ref.url}'`);
        }

        return {
          ...ref,
          href: doc.href,
        };
      } else {
        return ref;
      }
    });

  page.children?.forEach(updateReferences);
}

docPages.forEach(updateReferences);

function getDocFromPath(path: string) {
  function searchDocs(page: DocPage): DocPage | undefined {
    if (page.path === path) {
      return page;
    }

    if (!page.children) {
      return;
    }

    for (let child of page.children) {
      const doc = searchDocs(child);

      if (doc) {
        return doc;
      }
    }
  }

  for (let child of docPages) {
    const doc = searchDocs(child);

    if (doc) {
      return doc;
    }
  }
}

const currentPage = writable<DocPage | null>(null);

export { docPages, currentPage, getDocFromPath };
