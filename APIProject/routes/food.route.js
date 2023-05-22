'use strict';
const router = require("express").Router();
const foodCtrl = require('../controllers/food.controller');
const imageUploader = require("../helpers/image_uploaders")


router.get('/api/selectAvailableFood', foodCtrl.selectAvailableFood);