const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

module.exports = {
    entry: {
        admin: ["./src/admin/index.jsx"],
        home: ["./src/home/index.jsx", "./src/home/styles.scss"]
    },
    output: {
        path: path.resolve(__dirname, "static/"),
        filename: "[name]/[name].js",
        publicPath: "http://localhost:8080/static/"
    },
    module: {
        rules: [
            {
                test: /\.(js|jsx)$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader"
                }
            },
            {
                test: /\.module\.scss$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    {
                        loader: "css-loader",
                        options: {
                            modules: true
                        }
                    },
                    "sass-loader"
                ]
            },
            {
                test: /\.scss$/,
                exclude: /\.module\.scss$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    "css-loader",
                    "sass-loader"
                ]
            }
        ]
    },
    plugins: [
        new MiniCssExtractPlugin({
            filename: "[name]/[name].css"
        }),
        new HtmlWebpackPlugin({
            template: "src/index.html",
            title: "Home",
            filename: "home/index.html",
            chunks: ["home"]
        }),
        new HtmlWebpackPlugin({
            template: "src/index.html",
            title: "Admin",
            filename: "admin/index.html",
            chunks: ["admin"]
        })
    ],
    devServer: {
        publicPath: "/static/",
        disableHostCheck: true
    }
}
