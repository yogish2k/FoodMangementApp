const express = require('express');
const router = express.Router();
const mysql = require('mysql');

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'foodapp'
})

router.get('/', (req, res, next) => {

    let sql = "SELECT foodupload.uploadID, users.nameFirst, users.nameLast, users.contact, users.contactAdditional, GROUP_CONCAT(foodCategory.foodType) AS foods, GROUP_CONCAT(foodcategory.quantity) AS quantity FROM foodupload INNER JOIN users ON foodupload.userID = users.userID RIGHT JOIN foodCategory ON foodupload.uploadID = foodcategory.uploadID WHERE foodupload.status = 1 GROUP BY foodupload.uploadID ORDER by foodupload.uploadID DESC";

    let query = db.query(sql, (err, result) => {
        res.json(result);
    })

});

module.exports = router;