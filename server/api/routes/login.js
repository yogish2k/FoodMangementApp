const express = require('express');
const router = express.Router();
const bcrypt = require('bcrypt');
const mysql = require('mysql');

const saltRounds = 10;

//connecting to database
const db = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'foodapp'
})

//post method
router.post('/', (req, res, next) => {
  const login = {
    email: req.body.email,
    password: req.body.password
  }

  //SQL query
  db.query('SELECT * FROM users WHERE email = ?', [req.body.email], async function (error, results, fields) {
    if (error) {
      res.send({
        "status": 400,
        "message": "error ocurred"
      })
      return;
    }
    else {
      if (results.length > 0) {
        var userId = results[0].userID;
        const comparision = await bcrypt.compare(req.body.password, results[0].password)
        if (comparision) {
          res.send({
            "status": 200,
            "message": "login successful",
            "userID": userId
          })
        }
        else {
          res.send({
            "status": 204,
            "message": "Email and password does not match"
          })
        }
      }
      else {
        res.send({
          "status": 206,
          "message": "Email does not exits"
        });
      }
    }
  });


});

module.exports = router;