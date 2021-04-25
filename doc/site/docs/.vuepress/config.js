module.exports = {
    //  斜杠结尾markdown 中图片，需要这样$withBase('/foo.png')
    // 自定义子域名则不要base=/ ,利用github pages 需要写base=/seezoon-stack/
    base: '/',
    head: [
        ['link', {rel: 'icon', href: '/images/favicon.ico'}]
    ],
    title: 'Seezoon Stack',
    description: '一款基于当前最前沿的前端（Vue3 + Vite + Antdv）和后台（Spring boot）实现的低代码开发平台。',
    themeConfig: {
        logo: '/images/logo.png',
        nav: [
            {text: '功能介绍', link: '/'},
            {text: '开发指南', link: '/guide/'},
            {text: 'GitHub', link: 'https://github.com/734839030/seezoon-stack'},
        ]
    }
}