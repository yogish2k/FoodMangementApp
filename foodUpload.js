const express = require('express');
const mysql = require('mysql');
const router = express.Router();

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'foodapp'
})


router.post('/', function (req, res, _next) {

    var foodStatus = 1;
    const uploadInfo = {
        userID: req.body.userid,
        contact: req.body.contact,
        status: foodStatus

    };
    const dbquery = query(uploadInfo);

    function categoryUpload(result) {
        uploadId = result.insertId;

        function dbquery(categ) {

            let sql = "INSERT INTO foodcategory SET ?"
            db.query(sql, categ);

        }

        for (var i = 0; i < req.body.category.length; i++) {
            var categ = {
                uploadID: uploadId,
                foodType: req.body.category[i].foodItem,
                quantity: req.body.category[i].quantity,
                price: req.body.category[i].price
            }

            dbquery(categ);
        }

    }

    function qcallback(err, result) {
        if (err) {
            res.json({ message: "something went wrong!" });
        }
        categoryUpload(result);
        res.status(200).json({
            "message": "food uploaded",
            "uploadID": result.insertId
        })

    }


    function query(uploadInfo) {
        let sql = 'INSERT INTO foodUpload SET ?';
        db.query(sql, uploadInfo, qcallback);
    };

    //res.json({message:"food uploaded"});

});

module.exports = router;