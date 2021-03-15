const express = require('express');
const router = express.Router();
const bcrypt = require('bcrypt');
const mysql = require('mysql');

const saltRounds = 10;

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'drowssap123',
    database : 'foodapp'
})

router.post('/', (req, res, next) =>{
    const login = {
        email : req.body.email,
        password : req.body.password
    }

    db.query('SELECT * FROM users WHERE email = ?',[req.body.email], async function (error, results, fields) {
        if (error) {
          res.send({
            "code":400,
            "failed":"error ocurred"
          })
        }else{
          if(results.length >0){
            const comparision = await bcrypt.compare(req.body.password, results[0].password)
            if(comparision){
                res.send({
                  "code":200,
                  "message":"login sucessfull"
                })
            }
            else{
              res.send({
                   "code":204,
                   "message":"Email and password does not match"
              })
            }
          }
          else{
            res.send({
              "code":206,
              "message":"Email does not exits"
                });
          }
        }
        });


});

module.exports = router;
