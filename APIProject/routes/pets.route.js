'use strict';
const router = require("express").Router();
const petCtrl = require('../controllers/pets.controller');
const imageUploader = require("../helpers/image_uploaders")


router.get('/api/selectAvailableNewPet', petCtrl.selectAvailableNewPet);
router.post('/api/showListImagePet', imageUploader.upload.single("imageLink"), petCtrl.showListImagePet);
router.post('/api/showListStylePet', petCtrl.showListStylePet);
router.get('/api/selectAvailableCategoryPet', petCtrl.selectAvailableCategoryPet);
router.post('/api/selectInformationPet', petCtrl.selectInformationPet);
router.post('/api/filterPetToStyle', petCtrl.filterPetToStyle);


module.exports = router;