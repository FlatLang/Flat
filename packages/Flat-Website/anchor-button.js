import { parse } from 'node-html-parser';

function isHeader(element) {
  return ['h1', 'h2', 'h3', 'h4', 'h5', 'h6'].indexOf(element.tagName.toLocaleLowerCase()) >= 0;
}

export function anchorButton(element) {
  const anchor = parse(
    `<a href="#${element.id}" class="anchor-button"><img src="/images/link.svg" alt="permalink" /></a>`,
  );

  function addAnchor(element) {
    element.appendChild(anchor);
    element.classList.add('anchor-button-container');
    element.setAttribute('style', 'position: relative;');
  }

  function addContentToHeader(header) {
    header.set_content(`<span class="anchor-button-content">${header.innerHTML}</span>`);
  }

  if (isHeader(element)) {
    addContentToHeader(element);
    addAnchor(element);
  } else {
    const child = element.querySelector('h1, h2, h3, h4, h5, h6');

    if (isHeader(child)) {
      addContentToHeader(child);
      addAnchor(child);
    } else {
      addAnchor(element);
    }
  }
}

export default async function ({ content }) {
  const root = parse(content);
  const anchors = root.querySelectorAll(`[anchor-button]`);

  anchors.forEach((anchor) => {
    anchorButton(anchor);
  });

  const code = root.toString();

  return { code };
}
