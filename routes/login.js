const express = require('express');
const router = express.Router();
const mysql = require('mysql');

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database : 'nodetest'
})

router.post('/', (req, res, next) =>{
    const login = {
        email : req.body.email,
        password : req.body.password
    }

    db.query('SELECT * FROM user WHERE email = ?',[req.body.email], async function (error, results, fields) {
        if (error) {
          res.send({
            "code":400,
            "failed":"error ocurred"
          })
        }else{
          if(results.length >0){
            if(login.password === results[0].password){
                res.send({
                  "code":200,
                  "success":"login sucessfull"
                })
            }
            else{
              res.send({
                   "code":204,
                   "success":"Email and password does not match"
              })
            }
          }
          else{
            res.send({
              "code":206,
              "success":"Email does not exits"
                });
          }
        }
        });


});

module.exports = router;