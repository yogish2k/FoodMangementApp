const express = require('express');
const app = express();
const welcomeRoutes = require('./api/routes/welcome');
const loginRoutes = require('./api/routes/login');
const registerRoutes = require('./api/routes/register');
const fooduploadRoutes = require('./api/routes/foodUpload');
const fooddisplayRoutes = require('./api/routes/foodDisplay');
const foodorderRoutes = require('./api/routes/foodOrder');
const myordersRoutes = require('./api/routes/myOrders');
const myuploadsRoutes = require('./api/routes/myUploads');
const bodyParser = require('body-parser');
const morgan = require('morgan');
const mysql = require('mysql');

app.use((req, res, next) => {
    res.header('Access-Control-Allow-Origin', '*');
    res.header('Access-Control-Allow-Headers', '*');
    if (req.method === 'OPTIONS') {
        res.header('Access-Control-Allow-Methods', 'PUT, POST, PATCH, DELETE, GET');
        return res.status(200).json({});
    }
    next();
})

app.use(morgan('dev'));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());



// create connection 
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'foodapp'
})

db.connect(err => {
    if (err) {
        console.log('Error connecting to mySQL');
    }
    else
        console.log('mySQL Connected...');
})


//routes
app.use('/welcome', welcomeRoutes);
app.use('/login', loginRoutes);
app.use('/register', registerRoutes);
app.use('/foodUpload', fooduploadRoutes);
app.use('/foodDisplay', fooddisplayRoutes);
app.use('/foodOrder', foodorderRoutes);
app.use('/myOrders', myordersRoutes);
app.use('/myUploads', myuploadsRoutes);


app.use((req, res, next) => {

    const error = new Error('Not found');
    error.status = 404;
    next(error);
})

app.use((error, req, res, next) => {
    res.status(error.status || 500).json({
        error: {
            message: error.message
        }
    });
});

module.exports = app;