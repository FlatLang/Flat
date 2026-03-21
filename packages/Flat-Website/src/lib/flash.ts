let lastFlashed: HTMLElement;

export function checkHash(allowSame?: boolean) {
  if (typeof window === 'undefined') {
    return;
  }

  const hash = window.location.hash?.substring(1);

  if (hash) {
    const element = document.getElementById(hash);

    if (!element) {
      return;
    }
    if (!allowSame && element === lastFlashed) {
      return;
    }

    lastFlashed?.classList.remove('flash');

    element.classList.remove('flash');

    setTimeout(() => {
      element.classList.add('flash');
      element.scrollIntoView();
    }, 0);

    lastFlashed = element;
  }
}

if (typeof document !== 'undefined') {
  window.addEventListener('hashchange', checkHash, false);

  document.addEventListener('click', ({ target }) => {
    let current = target as HTMLElement;
    let href: string;

    while (!href && current && current !== document) {
      href = current?.getAttribute('href');

      current = current.parentElement;
    }

    if (typeof href === 'string' && href[0] === '#') {
      const hash = window.location.hash;

      if (hash === href) {
        checkHash(true);
      }
    }
  });
}
