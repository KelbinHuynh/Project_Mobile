'use strict';
const router = require("express").Router();
const categoryCtrl = require('../controllers/category.controller');
const imageUploader = require("../helpers/image_uploaders")

router.get('/api/getAllCategory', categoryCtrl.getAllCategory);

module.exports = router;