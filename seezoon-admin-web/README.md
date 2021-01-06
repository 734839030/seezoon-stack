# antd-vue3

## vue cli 辅助工具
请先参照(https://cli.vuejs.org/zh/guide/installation.html)[https://cli.vuejs.org/zh/guide/installation.html]
```
vue ui 
```

## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn serve
```

### Compiles and minifies for production
```
yarn build
```

### Lints and fixes files
```
yarn lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).


## Project setup
```
npm install
```
推荐安装 `npm install -g @vue/cli` See [Vue Cli](https://cli.vuejs.org/zh/guide/)
命令行`vue ui` 可以打开管理界面，安装依赖，启动程序等
### Compiles and hot-reloads for development
```
npm run serve
```
### Compiles and minifies for uat
```
npm run build:uat
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

## 静态资源引用说明

在vue html中使用@/assets/xxx 
在css 使用~@/assets/login-bg.jpg
import css  @import '~@/styles/mixin.scss';
