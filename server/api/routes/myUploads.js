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


    let userId = req.body.userid;

    function display() {
        let sql = 'SELECT GROUP_CONCAT(foodcategory.foodType) AS foods, GROUP_CONCAT(foodcategory.quantity) AS quantity FROM foodupload RIGHT JOIN foodcategory ON foodupload.uploadID = foodcategory.uploadID WHERE foodupload.userID=? GROUP BY foodupload.uploadID ORDER by foodupload.uploadID DESC';
        var uploadId = [];
        
        db.query(sql, userId, (err, result) => {
            if(err)
            {
                res.json({
                    "status": 400, "message": 'an error occured'
                })
                return;
            }

            res.json(result);
        })
        

    }

    db.query('SELECT * FROM foodUpload WHERE userID = ?', userId, (err, result) => {
        if (err) {
            res.json({
                "status": 400,"message": "something went wrong in mysql"
            })
            return;
        }

        if (result && result.length) {
            display();
        }
        else {
            res.json({
                message: "no uploads"
            })
        }


    })

});

module.exports = router;