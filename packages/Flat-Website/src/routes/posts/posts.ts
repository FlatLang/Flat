import dayjs from 'dayjs';

import { writable } from 'svelte/store';

export interface Reference {
  header: string;
  url: string;
}

export interface PostsPage {
  header: string;
  url: string;
  date: dayjs.Dayjs;
  author: string;
  visible?: boolean;
  references?: Reference[];
}

const postsPages: PostsPage[] = [
  {
    header: 'Flat Compiler Design',
    url: 'compiler-design',
    date: dayjs('2/18/2017'),
    author: 'Braden Steffaniak',
  },
  {
    header: 'Multiple Targets',
    url: 'multiple-targets',
    date: dayjs('2/19/2017'),
    author: 'Braden Steffaniak',
  },
  {
    header: 'Writing Target-Specific Code',
    url: 'target-specific-code',
    date: dayjs('2/20/2017'),
    author: 'Braden Steffaniak',
  },
  {
    header: 'Zero-Cost Primitive Generics',
    url: 'zero-cost-primitive-generics',
    date: dayjs('2/21/2017'),
    visible: false,
    author: 'Braden Steffaniak',
  },
  {
    header: 'Thread-Local Storage',
    url: 'thread-local-storage',
    date: dayjs('2/22/2017'),
    author: 'Braden Steffaniak',
  },
  {
    header: "Flat Won't Have Yield",
    url: 'no-yield',
    date: dayjs('2/23/2017'),
    author: 'Braden Steffaniak',
  },
  {
    header: 'Flat Exception Handling',
    url: 'exception-handling',
    date: dayjs('2/24/2017'),
    author: 'Braden Steffaniak',
  },
  {
    header: 'Automated API Importing',
    url: 'automated-api-importing',
    date: dayjs('2/25/2017'),
    author: 'Braden Steffaniak',
  },
  {
    header: 'v0.3.7 Release Notes',
    url: 'v0_3_7-release-notes',
    date: dayjs('2/26/2017'),
    author: 'Braden Steffaniak',
  },
  {
    header: 'First-Class Functions',
    url: 'first-class-functions',
    date: dayjs('2/27/2017'),
    author: 'Braden Steffaniak',
  },
  {
    header: 'Runtime Module Loading',
    url: 'runtime-module-loading',
    date: dayjs('2/28/2017'),
    visible: false,
    author: 'Braden Steffaniak',
  },
  {
    header: 'Scalable Compiler Components',
    url: 'scalable-compiler-components',
    date: dayjs('3/1/2017'),
    visible: false,
    author: 'Braden Steffaniak',
  },
  {
    header: 'Flat Path Forward',
    url: 'path-forward',
    date: dayjs('3/2/2017'),
    visible: false,
    author: 'Braden Steffaniak',
  },
  {
    header: 'Flat Unit Testing',
    url: 'flat-unit-testing',
    date: dayjs('3/3/2017'),
    visible: false,
    author: 'Braden Steffaniak',
  },
  {
    header: 'v0.3.8 Release Notes',
    url: 'v0_3_8-release-notes',
    date: dayjs('3/12/2017'),
    author: 'Braden Steffaniak',
    references: [
      {
        header: 'First-class Functions',
        url: '/posts/first-class-functions',
      },
    ],
  },
  {
    header: 'v0.3.9 Release Notes',
    url: 'v0_3_9-release-notes',
    date: dayjs('3/20/2017'),
    visible: false,
    author: 'Braden Steffaniak',
  },
  {
    header: `Chaining Operator ":"`,
    url: 'chaining-operator',
    date: dayjs('8/12/2022'),
    visible: true,
    author: 'Braden Steffaniak',
  },
  {
    header: `Airship v0.1.14 Release Notes`,
    url: 'airship/v0_1_14-release-notes',
    date: dayjs('8/17/2022'),
    visible: true,
    author: 'Braden Steffaniak',
  },
  {
    header: 'v0.1.6 Release Notes',
    url: 'v0_1_6-release-notes',
    date: dayjs('8/19/2022'),
    visible: true,
    author: 'Braden Steffaniak',
  },
  {
    header: `Airship v0.1.15 Release Notes`,
    url: 'airship/v0_1_15-release-notes',
    date: dayjs('8/20/2022'),
    visible: true,
    author: 'Braden Steffaniak',
  },
  {
    header: `Airship v0.1.16 Release Notes`,
    url: 'airship/v0_1_16-release-notes',
    date: dayjs('8/20/2022'),
    visible: true,
    author: 'Braden Steffaniak',
  },
  {
    header: `Airship v0.1.17 Release Notes`,
    url: 'airship/v0_1_17-release-notes',
    date: dayjs('8/20/2022'),
    visible: true,
    author: 'Braden Steffaniak',
  },
  {
    header: `Airship v0.2.0 Release Notes`,
    url: 'airship/v0_2_0-release-notes',
    date: dayjs('8/25/2022'),
    visible: true,
    author: 'Braden Steffaniak',
  },
];

const currentPage = writable<any>(null);

export { postsPages, currentPage };
