export {};

declare global {
  interface HTMLProps<T> {
    flat: boolean;
    javascript: boolean;
    csharp: boolean;
    cpp: boolean;
    c: boolean;
    bash: boolean;
  }
}
