const templateStartPattern = /<\s*element\s+id="([^"]+)"\s*>/g;
const templateEndPattern = /<\/element>/g;
const replacementPattern = /<\s*replace\s+id="([^"]+)"[^>]*?><\/replace>/g;

async function extractElements(content) {
  const startMatches = Array.from(content.matchAll(templateStartPattern));
  const endMatches = Array.from(content.matchAll(templateEndPattern));
  const elements = {};

  if (startMatches?.length > 0 && startMatches.length === endMatches?.length) {
    for (let i = startMatches.length - 1; i >= 0; i--) {
      const start = startMatches[i];
      const end = endMatches[i];
      const value = content.substring(start.index + start[0].length, end.index).trim();

      content = content.substring(0, start.index) + content.substring(end.index + end[0].length);

      elements[start[1]] = value;
    }
  }

  return { content, elements };
}

async function replace(content, elements) {
  let replaced = false;

  content = content.replace(replacementPattern, (str, id) => {
    const replacement = elements[id];

    if (typeof replacement === 'string') {
      replaced = true;
      return replacement;
    } else {
      return str;
    }
  });

  return { content, replaced };
}

export default async function ({ content }) {
  let replaced = false;
  let counter = 0;

  const extraction = await extractElements(content);
  const elements = extraction.elements;
  content = extraction.content;

  do {
    let value = await replace(content, elements);

    content = value.content;
    replaced = value.replaced;

    if (counter > 10) {
      console.error('Too much recursion happened for replacements.');
      break;
    }
  } while (replaced);

  return { code: content };
}
