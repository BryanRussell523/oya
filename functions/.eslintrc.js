module.exports = {
  env: {
    node: true,
    es6: true,
  },
  extends: [
    'google',
  ],
  parserOptions: {
    ecmaVersion: 2017,
  },
  rules: {
    'max-len': ['error', {'code': 120}],
    'require-jsdoc': 'off', // Disable JSDoc requirement
  },
};
