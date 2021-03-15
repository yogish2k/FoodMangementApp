const express = require('express');
const mysql = require('mysql');
const bcrypt = require('bcrypt');
const router = express.Router();

const saltRounds = 10;

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'drowssap123',
    database : 'foodapp'
})


router.post('/', async function (req, res, _next) {

        const password = req.body.password;
        const encryptedpassword = await bcrypt.hash(password, saltRounds);


        const user = {
            nameFirst: req.body.firstname,
	        nameLast: req.body.lastname,	
            email: req.body.email,
	        contact : req.body.contact,
	        contactAdditional : req.body.additionalcontact, 
	        password: encryptedpassword
	    
        };

        db.query("SELECT * FROM users WHERE email = ?", [req.body.email], function (err, result, _fields) {
            db.on(err, function (err) {
                res.json({ message:"error"});
            });

            if (result && result.length) {
                res.json({ message:"email already exists"});
            }

            else {
                let sql = 'INSERT INTO users SET ?';
                let query = db.query(sql, user, err => {
                    if (err) {
                        res.json({message:"something went wrong!"});
                    }
                    res.json({ message:"employee added"});
                });
            }
        });




    });

module.exports = router;
