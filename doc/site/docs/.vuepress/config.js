module.exports = {
    //  斜杠结尾,markdown 中图片，需要这样$withBase('/foo.png')，config 中的路径会自动加base
    // 自定义子域名则要base=/ ,利用github pages的网址 需要写base=/seezoon-stack/
    base: '/',
    head: [
        ['link', {rel: 'icon', href: '/images/favicon.ico'}],
        [
            "script",
            {
                "data-ad-client": "ca-pub-5032572591927756",
                async: true,
                src: "https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"
            }
        ]
    ],
    title: 'Seezoon Stack',
    description: '一款基于当前最前沿的前端（Vue3 + Vite + Antdv）和后台（Spring boot）实现的低代码开发平台。',
    plugins: ['@vuepress/back-to-top'],
    themeConfig: {
        logo: '/images/logo.png',
        // displayAllHeaders: true ,
        nav: [
            {text: '指南', link: '/guide/'},
            {text: '开发手册', link: '/manual/'},
            {text: '预览', link: 'https://stack.seezoon.com'},
            {text: 'GitHub', link: 'https://github.com/734839030/seezoon-stack'},
        ],
        sidebar: {
            '/guide/': [
                {
                    title: '基础',
                    collapsable: false,
                    sidebarDepth: 2,    // 可选的, 默认值是 1
                    children: ['/guide/', '/guide/base/quick-start', '/guide/base/code-specs'],
                },
                {
                    title: '功能',
                    collapsable: false,
                    sidebarDepth: 2,    // 可选的, 默认值是 1
                    children: ['/guide/feature/lombok', '/guide/feature/jsr-303', '/guide/feature/response',
                        '/guide/feature/error-code', '/guide/feature/exception', '/guide/feature/api-doc',
                        '/guide/feature/security', '/guide/feature/components', '/guide/feature/code-gen', '/guide/feature/data-scope'],
                },
                {
                    title: '进阶',
                    collapsable: false,
                    sidebarDepth: 2,    // 可选的, 默认值是 1
                    children: ['/guide/advance/cors', '/guide/advance/deploy', '/guide/advance/nacos', '/guide/advance/docker-image-tools'],
                },
            ],
            '/manual/': [
                {
                    title: '开发手册(建设中...)',
                    collapsable: false,
                    sidebarDepth: 1,    // 可选的, 默认值是 1
                    children: ['/manual/', "/manual/server"],
                },
            ]
        },
        smoothScroll: true,
        lastUpdated: '上次更新', // string | boolean

        // 假定是 GitHub. 同时也可以是一个完整的 GitLab URL
        //repo: '734839030/seezoon-stack',
        // 自定义仓库链接文字。默认从 `themeConfig.repo` 中自动推断为
        // "GitHub"/"GitLab"/"Bitbucket" 其中之一，或是 "Source"。
        //  repoLabel: '查看源码',

        // 以下为可选的编辑链接选项

        // 假如你的文档仓库和项目本身不在一个仓库：
        docsRepo: '734839030/seezoon-stack',
        // 假如文档不是放在仓库的根目录下：
        docsDir: 'doc/site/docs',
        // 假如文档放在一个特定的分支下：
        docsBranch: 'master',
        // 默认是 false, 设置为 true 来启用
        editLinks: true,
        // 默认为 "Edit this page"
        editLinkText: '帮助我们改善此页面！'

    }
}