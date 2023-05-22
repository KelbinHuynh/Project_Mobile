'use strict';
const router = require("express").Router();
const userCtrl = require('../controllers/user.controller');
const imageUploader = require("../helpers/image_uploaders")


router.post('/api/login', userCtrl.login);
router.post('/api/register', userCtrl.register);
router.post('/api/uploadAvatar', imageUploader.upload.single("avatar"), userCtrl.uploadAvatar);
router.post('/api/editInforUser', userCtrl.editInforUser);
module.exports = router;