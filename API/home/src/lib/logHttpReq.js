"use strict";

module.exports = (req, res, next) => {
    res.on('finish', () => {
        let dateObj = new Date()
        let date = ("0" + dateObj.getDate()).slice(-2)
        let month = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"][dateObj.getMonth()]
        let year = dateObj.getFullYear()
        let hours = dateObj.getHours()
        let minutes = dateObj.getMinutes()
        let seconds = dateObj.getSeconds()
        let timestamp = `[${date}/${month}/${year} ${hours}:${minutes}:${seconds}]`
        console.log(`${timestamp} "${req.method} ${req.url} HTTP/${req.httpVersion}" ${res.statusCode}`)
    })
    next();
}
