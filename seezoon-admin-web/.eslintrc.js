module.exports = {
    root: true,
    env: {
        node: true
    },
    'extends': [
        'plugin:vue/strongly-recommended',
        '@vue/standard'
    ],
    rules: {},
    parserOptions: {
        parser: 'babel-eslint'
    },
    overrides: [
        {
            files: [
                '**/__tests__/*.{j,t}s?(x)',
                '**/tests/unit/**/*.spec.{j,t}s?(x)'
            ],
            env: {
                jest: true
            }
        }
    ]
}
