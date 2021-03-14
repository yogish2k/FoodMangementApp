const express = require('express');
const mysql = require('mysql');
const router = express.Router();

const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database : 'nodetest'
})


router.post('/', (req, res, _next) =>{
    const post = {
        username : req.body.username,
        password : req.body.password,
        email : req.body.email
    }

        let sql = 'INSERT INTO user SET ?'
        let query = db.query(sql, post, err => {
        if(err){
            throw err
        }
        res.send('Employee added')
        })


});
module.exports = router;