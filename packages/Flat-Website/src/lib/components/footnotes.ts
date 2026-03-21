export function clearFootnotes() {
  Object.keys(footnotes).forEach((key) => delete footnotes[key]);
}

export const footnotes = {};

export function getFootnote(id: string) {
  let footnote = footnotes[id];

  if (!footnote) {
    footnote = {
      number: Object.keys(footnotes).length + 1,
    };

    footnotes[id] = footnote;
  }

  return footnote;
}
