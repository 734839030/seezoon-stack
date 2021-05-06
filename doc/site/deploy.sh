#!/usr/bin/env sh

# 确保脚本抛出遇到的错误
set -e

# 生成静态文件 or npm run docs:build
yarn docs:build

# 进入生成的文件夹
cd docs/.vuepress/dist

# 如果是发布到自定义域名
echo 'doc.stack.seezoon.com' >CNAME

git init
git add -A
git commit -m 'deploy'

# 如果发布到 https://<USERNAME>.github.io
# git push -f git@github.com:<USERNAME>/<USERNAME>.github.io.git master

# 如果发布到 https://<USERNAME>.github.io/<REPO>
# git push -f git@github.com:<USERNAME>/<REPO>.git master:gh-pages

git branch -M gh-pages
git remote add origin https://github.com/734839030/seezoon-stack.git
git push -f -u origin gh-pages
cd -
