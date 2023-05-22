const express = require('express')
const app = express()
var multer = require('multer')
const bodyParser = require('body-parser')
var forms = multer();
var cors = require('cors');
require('dotenv').config()
const port = process.env.PORT || 3000

const userRoute = require('./routes/user.route')
const petRoute = require('./routes/pets.route')
const cartRoute = require ('./routes/cart.route')
const orderRoute = require ('./routes/order.route')
const categoryRoute = require ('./routes/category.route')
const { body } = require('express-validator');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors());
app.use(bodyParser.json());
// app.use(forms.array()); 
app.use('/upload', express.static('upload/images'));


app.use(userRoute)
app.use(petRoute)
app.use(cartRoute)
app.use(orderRoute)
app.use(categoryRoute)

app.use(function(req, res) {
    res.status(404).send({url: req.originalUrl + ' not found'})
})



app.listen(port)

console.log('RESTful API server started on: ' + port)