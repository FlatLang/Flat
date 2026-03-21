import adapter from 'svelte-kit-sst';
import { sveltePreprocess } from 'svelte-preprocess';
import anchorButton from './anchor-button.js';
import inlineSvg from './inline-svg.js';
import highlight from './highlight.js';
import replaceElement from './replace-element.js';

/** @type {import('@sveltejs/kit').Config} */
const config = {
  // Consult https://github.com/sveltejs/svelte-preprocess
  // for more information about preprocessors
  preprocess: sveltePreprocess({
    'flat-html': async function (args) {
      args.content;

      args.content = (await anchorButton(args)).code;
      args.content = (await inlineSvg(args)).code;
      args.content = (await highlight(args)).code;
      args.content = (await replaceElement(args)).code;

      return { code: args.content };
    },
    replace: [[/(from\s*["'])src\//g, '$1/src/']],
  }),

  kit: {
    adapter: adapter({
      // default options are shown. On some platforms
      // these options are set automatically — see below
      pages: 'build',
      assets: 'build',
      fallback: null,
      precompress: true,
    }),
    prerender: {
      // This can be false if you're using LOCAL_GIT_DIRECTORY fallback (i.e. SPA mode) default: true
      handleMissingId: 'ignore',
    },
    paths: { relative: false, base: '' },
  },
};

export default config;
