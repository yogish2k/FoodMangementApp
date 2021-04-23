const express = require('express');
const router = express.Router();
const mysql = require('mysql');

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'foodapp'
})

router.post('/', (req, res, next) => {

    var orderStatus = 1;
    const orderInfo = {
        uploadID: req.body.uploadid,
        userID: req.body.userid,
        status: orderStatus
    };

    db.query("SELECT * FROM orders WHERE uploadID = ?", [req.body.uploadid], function (err, result) {
        if (err) {
            res.json({"status": 400, "message": "error" })
            return;
        }

        if (result && result.length) {
            res.json({
                "message": "out of stock"
            });
        }

        else {
            let sql = 'INSERT INTO orders SET ?';

            let query = db.query(sql, orderInfo, (err, result) => {
                if (err) {
                    res.json({ "status": 400,"message": "error occured"});
                    return
                }

                res.json({
                    message: "order successfull",
                    status: "200",
                    orderID: result.insertId
                });
            })

            db.query('UPDATE foodupload SET status = 0 WHERE uploadID = ?', [req.body.uploadid]);
        }
    });

});

module.exports = router;