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

    function display(results) {
        let sql = 'SELECT users.nameFirst, users.nameLast, users.contact, users.contactAdditional, GROUP_CONCAT(foodcategory.foodType) AS foods, GROUP_CONCAT(foodcategory.quantity) AS quantity FROM foodupload INNER JOIN users ON foodupload.userID = users.userID RIGHT JOIN foodcategory ON foodupload.uploadID = foodcategory.uploadID WHERE foodupload.uploadID IN (?) GROUP BY foodupload.uploadID ORDER by foodupload.uploadID DESC';
        var uploadId = [];
        for(var i = 0; i<results.length; i++)
        {
            uploadId.push(results[i].uploadID);
        }
        
        db.query(sql, [uploadId], (err, result) => {
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

    db.query('SELECT * FROM orders WHERE userID = ? AND status = 1', userId, (err, result) => {
        if (err) {
            res.json({
                "status": 400,"message": "something went wrong in mysql"
            })
            return;
        }

        if (result && result.length) {
            display(result);
        }
        else {
            res.json({
                message: "no orders"
            })
        }


    })

});

module.exports = router;