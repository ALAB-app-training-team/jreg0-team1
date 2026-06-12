import { plugin } from 'typescript-eslint';

/**
 * @see https://prettier.dokyumento.jp/docs/en/configuration.html
 * @type {import("prettier").Config}
 */

const config = {
    semi: true,
    singleQuote: true,
    tabWidth: 4,
    trailingComma: 'all',
    endOfLine: 'lf',

    plugin: ['prettier-plugin-tailwindcss'],
};

export default config;
