const express = require('express');
const app = express();
const loginRoutes = require('./api/routes/login');
const registerRoutes = require('./api/routes/register');
const bodyParser = require('body-parser');
const mysql = require('mysql');


app.use(bodyParser.urlencoded({extended: true }));
app.use(bodyParser.json());


// create connection 
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database : 'nodetest'
})

db.connect(err => {
    if(err)
    {
        console.log('error!');
    }
    console.log('mySQL Connected...');
})

//routes
app.use('/login', loginRoutes);
app.use('/register', registerRoutes);

app.use((req, res, next) => {
    res.status(200).json({
        message: 'success!'
    });
});

module.exports = app;