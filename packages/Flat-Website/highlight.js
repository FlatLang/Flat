import hljs from './static/js/highlight.min.js';
import { hljsFlat } from './static/js/flat.js';

hljs.registerLanguage('flat', hljsFlat);

function escapeHTML(str) {
  return str.replaceAll(
    /[}{]/gi,
    (tag) =>
      ({
        '{': '{`{`}',
        '}': '{`}`}',
      }[tag]),
  );
}

function regexIndexOf(text, re, i) {
  i = typeof i === 'number' ? i : 0;
  var indexInSuffix = text.slice(i).search(re);
  return indexInSuffix < 0 ? indexInSuffix : indexInSuffix + i;
}

function parseAttributes(value) {
  const values = value.trim().split(/\s+|('[^']*?'|"[^"]*?")/g);

  const attrs = {};
  let key = null;

  values
    .filter((v) => (typeof v === 'undefined' ? true : v))
    .forEach((v) => {
      if (v) {
        if (key) {
          if (/^"[^"]*?"|'[^']*?$'/g.test(v)) {
            v = v.substring(1, v.length - 1);
          }

          attrs[key] = v;
          key = null;
        } else {
          key = v.replace(/[^A-Za-z0-9\-]/g, '');
          attrs[key] = null;
        }
      } else {
        key = null;
      }
    });

  return attrs;
}

function getLanguageFromAttributes(attributes) {
  const prefix = 'language-';
  const attrs = parseAttributes(attributes);
  const languages = ['flat', 'javascript', 'csharp', 'cpp', 'c', 'bash'];

  const languageFromRawAttr = languages.find((l) => typeof attrs[l] !== 'undefined');

  if (languageFromRawAttr) {
    return languageFromRawAttr;
  }

  if (attrs.class) {
    const classNames = attrs.class.split(/\s+/g);
    const language = classNames.find((n) => n.indexOf(prefix) === 0);

    if (language) {
      return language.substring(prefix.length);
    }
  }

  return 'flat';
}

function trimPrecedingTabs(rawContent, trimmedValue) {
  const firstContentIndex = rawContent.indexOf(trimmedValue);
  const precedingContent = rawContent.substring(0, firstContentIndex);
  const lastNewLineIndex = precedingContent.lastIndexOf('\n');

  if (lastNewLineIndex === -1) {
    return trimmedValue;
  }

  const precedingTabs = precedingContent.substring(lastNewLineIndex + 1);

  return trimmedValue.replaceAll('\n' + precedingTabs, '\n');
}

export default async function ({ content }) {
  let matches = [];

  let index = regexIndexOf(content, /<\s*?code/gi);

  while (index > 0) {
    const start = content.indexOf('>', index + '<code'.length) + 1;

    if (start === 0) {
      break;
    }

    const end = regexIndexOf(content, /<\/code\s*?>/gi, start);

    if (end === -1) {
      break;
    }

    const elementAttributes = content.substring(
      content.indexOf('code', index) + 'code'.length,
      start - 1,
    );
    const language = getLanguageFromAttributes(elementAttributes);

    matches.push({ start, end, index, language });

    index = regexIndexOf(content, /<code/gi, end + '</code>'.length);
  }

  for (let i = matches.length - 1; i >= 0; i--) {
    const start = matches[i].start;
    const end = matches[i].end;
    const index = matches[i].index;
    const language = matches[i].language;

    const prevTagIndex = content.lastIndexOf('<', index - 2);
    const insidePreTag =
      prevTagIndex !== -1 && content.substring(prevTagIndex, prevTagIndex + 4) === '<pre';

    if (insidePreTag) {
      const endPreTag = regexIndexOf(content, /<\/\s*pre\s*>/gi, end + '</code>'.length);
      content = content.substring(0, endPreTag).trimEnd() + content.substring(endPreTag);
    }

    const rawContent = content.substring(start, end);
    const trimmed = rawContent.trim();
    const value =
      trimmed[0] === '{' && trimmed[1] === '`'
        ? trimmed.substring(2, trimmed.length - 2).trim()
        : trimmed;
    const trimmedValue = trimPrecedingTabs(rawContent, value);

    content =
      content.substring(0, start) +
      escapeHTML(hljs.highlight(trimmedValue, { language }).value) +
      content.substring(end);

    if (insidePreTag) {
      content = content.substring(0, index).trimEnd() + content.substring(index);
    }
  }

  return { code: content };
}
