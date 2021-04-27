module.exports = {
    //  斜杠结尾markdown 中图片，需要这样$withBase('/foo.png')，config 中的路径会自动加base
    // 自定义子域名则要base=/ ,利用github pages的网址 需要写base=/seezoon-stack/
    base: '/',
    head: [
        ['link', {rel: 'icon', href: '/images/favicon.ico'}]
    ],
    title: 'Seezoon Stack',
    description: '一款基于当前最前沿的前端（Vue3 + Vite + Antdv）和后台（Spring boot）实现的低代码开发平台。',
    themeConfig: {
        logo: '/images/logo.png',
        nav: [
            {text: '指南', link: '/guide/'},
            {text: '开发手册', link: '/manual/'},
            {text: '预览', link: 'https://stack.seezoon.com'},
            {text: 'GitHub', link: 'https://github.com/734839030/seezoon-stack'},
        ],
        sidebar: {
            '/guide/': [
                {
                    title: '基础(更新中...)',
                    collapsable: false,
                    children: ['/guide/', '/guide/quick-start'],
                },
            ]
        }
    }
}