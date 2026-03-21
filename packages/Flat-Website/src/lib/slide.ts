function toggleClass(element: HTMLElement, className: string, enabled: boolean) {
  if (enabled) {
    element.classList.add(className);
  } else {
    element.classList.remove(className);
  }
}

export function slide(element: HTMLElement) {
  if (slideElements.indexOf(element) === -1) {
    slideElements.push(element);
  }

  onScroll(element);
}

function onScroll(element: HTMLElement) {
  const scroll = document.documentElement.scrollTop || document.body.scrollTop;
  const scrollBottom = scroll + window.innerHeight;

  const y = element.offsetTop;
  const height = element.offsetHeight;
  const distance = Math.abs(scroll - y);

  const active = distance < 100;
  const visible = y + window.innerHeight / 3 < scrollBottom && y + height > scroll;
  const rendering = y - 700 < scrollBottom && y + height + 700 > scroll;

  toggleClass(element, 'active', active);
  toggleClass(element, 'visible', visible);
  toggleClass(element, 'rendering', rendering);
}

let slideElements: HTMLElement[] = [];

export function resetSlides() {
  slideElements = [];
}

if (typeof window !== 'undefined') {
  window.addEventListener('scroll', () => {
    slideElements.forEach(onScroll);
  });
}
