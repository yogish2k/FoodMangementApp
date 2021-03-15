const express = require('express');
const app = express();
const loginRoutes = require('./api/routes/login');
const registerRoutes = require('./api/routes/register');
const bodyParser = require('body-parser');
const morgan = require('morgan');
const mysql = require('mysql');

app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', '*');
    if(req.method === 'OPTIONS')
    {
        res.header('Access-Control-Allow-Methods', 'PUT, POST, PATCH, DELETE, GET');
        return res.status(200).json({});
    }
    next();
})

app.use(morgan('dev'));
app.use(bodyParser.urlencoded({extended: true }));
app.use(bodyParser.json());



// create connection 
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'drowssap123',
    database : 'foodapp'
})

db.connect(err => {
    if(err)
    {
        console.log(err);
    }
    else
        console.log('mySQL Connected...');
})


//routes
app.use('/login', loginRoutes);
app.use('/register', registerRoutes);


app.use((req, res, next) => {

    res.json({message:"working!"});
})

app.use((req, res, next) => {

    const error = new Error('Not found');
    error.status = 404;
    next(error);
})

app.use((error, req, res, next) => {
    res.status(error.status || 500).json({
        error: {
            message : error.message
        }
    });
});

module.exports = app;
