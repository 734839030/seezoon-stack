var path = require('path');

module.exports = {
    lintOnSave: false,
    devServer: {
        port: 9000,
        contentBase: path.join(__dirname, 'dist'), // 为了本地上传文件到该目录可以访问
    },
    css: {
        loaderOptions: {
            less: {
                lessOptions: {
                    javascriptEnabled: true
                }
            }
        }
    }
}